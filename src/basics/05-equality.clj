(= 2 (+ 1 1))

(= (str "fo" "od") "food")

(= (float 314.0) (double 314.0))

(= 3 3N)

(= 2 2.0)


(= [0 1 2] (range 3))


(= [0 1 2] '(0 1 2))

(= [0 1 2] [0 2 1]) ;; false order different

(= '(0 1 2) '(0 1 2.0)) ;; false


;; for sets and maps order doesnt matter
(let [s1 #{1999 2001 3001}
      s2 (sorted-set 1999 2001 3001)]
  (= s1 s2))


(let [m1 (sorted-map-by > 3 -7 5 10 15 20)
     m2 {3 -7, 5 10, 15 20}]
     (= m1 m2))


;; Any metadata associated with Clojure collections is ignored when
;; comparing them.
(def s1 (with-meta #{1 2 3} {:key1 "set 1"}))
(def s2 (with-meta #{1 2 3} {:key1 "set 2 here"}))
(binding [*print-meta* true] (pr-str s1))
(binding [*print-meta* true] (pr-str s2))
(= s1 s2)
(= (meta s1) (meta s2))

(defrecord MyRec1 [a b])
(def r1 (->MyRec1 1 2))
(def r3 (->MyRec1 1 2))
r1
(defrecord MyRec2 [a b])
(def r2 (->MyRec2 1 2))
r2
(def m1 {:a 1 :b 2})
(= r1 r2)
(= r1 m1)
(= r3 r1)

(into {} r1)
(= (into {} r1) m1)

;; Clojure = is true if the 'category' and numeric values are the
;; same.
(= (int 1) (long 1))
(= 1 1.0)

;; Clojure also has == that is only useful for comparing numbers. It
;; returns true whenever = does. It also returns true for numbers that
;; are numerically equal, even if they are in different
;; categories.
(= 1 1.0) ;is false, but
(== 1 1.0) ;is true.


;; #NaN
(Math/sqrt -1)

(= ##NaN ##NaN) ;; false

;; Clojure uses the underlying Java double-size floating point
;; numbers (64-bit) with representation and behavior defined by a
;; standard, IEEE 754. There is a special value NaN ("Not A Number") that
;; is not even equal to itself. Clojure represents this value as the
;; symbolic value ##NaN.

(def s1 #{1.0 2.0 ##NaN})
s1
(s1 1.0)
(s1 1.4)
(s1 ##NaN)  ; cannot find ##NaN in a set, because it is not = to itself

(disj s1 2.0)
(disj s1 ##NaN)

;; Java has a special case in its equals method for floating point
;; values that makes ##NaN equal to itself. Clojure = and == do not.
(.equals ##NaN ##NaN)



;; Clojure has = and hash for similar reasons. Since Clojure =
;; considers more pairs of things equal to each other than Java
;; equals, Clojure hash must return the same hash value for more pairs
;; of objects. For example, hash always returns the same value
;; regardless of whether a sequence of = elements is in a sequence,
;; vector, list, or queue:
(hash ["a" 5 :c])
(hash (seq ["a" 5 :c]))
(hash '("a" 5 :c))
(hash (conj clojure.lang.PersistentQueue/EMPTY "a" 5 :c))


;; However, since hash is not consistent with = when comparing Clojure
;; immutable collections with their non-Clojure counterparts, mixing
;; the two can lead to undesirable behavior, as shown in the examples
;; below.

(def java-list (java.util.ArrayList. [1 2 3]))
(def clj-vec [1 2 3])

;; They are =, even though they are different classes
(= java-list clj-vec)
(class java-list)
(class clj-vec)

;; Their hash values are different, though.

(hash java-list)
(hash clj-vec)


;; If java-list and clj-vec are put into collections that do not use
;; their hash values, like a vector or array-map, then those
;; collections will be equal, too.

(= [java-list] [clj-vec])
(class {java-list 5})
(= {java-list 5} {clj-vec 5})
(assoc {} java-list 5 clj-vec 3)

;; However, if java-list and clj-vec are put into collections that do
;; use their hash values, like a hash-set, or a key in a hash-map,
;; then those collections will not be equal because of the different
;; hash values.

(class (hash-map java-list 5))
(= (hash-map java-list 5) (hash-map clj-vec 5))
                                        ;
(= (hash-set java-list) (hash-set clj-vec))
                                        ;

(get (hash-map java-list 5) java-list)
(get (hash-map java-list 5) clj-vec)
                                        ;

(conj #{} java-list clj-vec)
                                        ;
(hash-map java-list 5 clj-vec 3)
                                        ;
