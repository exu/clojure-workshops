;; NUMBERS

;; Clojure provides full support for JVM primitive values by default,
;; allowing high performance, idiomatic Clojure code for numeric
;; applications.


(def some-long 13213)
(def some-ratio 1/4)


;; BigInts and floating point types are "contagious" across
;; operations. That is, any integer operation involving a BigInt will
;; result in a BigInt, and any operation involving a double or float will
;; result in a double.

(== 1 1.0 1M)
(/ 2 3)
(/ 2.0 3)
(map #(Math/abs %) (range -3 3))
(class 36786883868216818816N)
(class 3.14159265358M)


;; STRINGS - Clojure strings are Java Strings
(map (fn [x] (.toUpperCase x)) (.split "Dasher Dancer Prancer" " "))


;; CHARACTERS - same as Java
(def some-char \A)

;; SYMBOLS Symbols are identifiers that are normally used to refer to
;; something else. They can be used in program forms to refer to
;; function parameters, let bindings, class names and global
;; vars. They have names and optional namespaces, both of which are
;; strings. Symbols can have metadata (see with-meta).  Symbols, just
;; like Keywords, implement IFn for invoke() of one argument (a map)
;; with an optional second argument (a default value). For
;; example ('mysym my-hash-map :none) means the same as (get
;; my-hash-map 'mysym :none). See get.


;; COLLECTIONS

;; All of the Clojure collections are immutable and persistent. In
;; particular, the Clojure collections support efficient creation of
;; 'modified' versions, by utilizing structural sharing, and make all
;; of their performance bound guarantees for persistent use. The
;; collections are efficient and inherently thread-safe. Collections
;; are represented by abstractions, and there may be one or more
;; concrete realizations. In particular, since 'modification'
;; operations yield new collections, the new collection might not have
;; the same concrete type as the source collection, but will have the
;; same logical (interface) type.  All the collections support count
;; for getting the size of the collection, conj for 'adding' to the
;; collection, and seq to get a sequence that can walk the entire
;; collection, though their specific behavior is slightly different
;; for different types of collections.
;; Because collections support the seq function, all of the sequence
;; functions can be used with any collection.





;; LISTS - Lists are collections. They implement the ISeq interface
;; directly. (Note that the empty list implements ISeq as well,
;; however the seq function will always return nil for an empty
;; sequence.) count is O(1). conj puts the item at the front of the
;; list.
(def some-array '(1 2 3 4 5))



;; VECTOR - A Vector is a collection of values indexed by contiguous
;; integers. Vectors support access to items by index in log32N
;; hops. count is O(1).
;; `conj` puts the item at the END of the vector.
;; Vectors also support rseq, which returns the items in
;; reverse order. Vectors implement IFn, for invoke() of one argument,
;; which they presume is an index and look up in themselves as if by
;; nth, i.e. vectors are functions of their indices. Vectors are
;; compared first by length, then each element is compared in order.
(def some-vector [1 2 3 4 5])

;; SETS
(def some-set #{:a :b :c :d})

(hash-set :a :b :c :d)
(sorted-set :a :b :c :d)
(set [1 2 3 2 1 2 3])

(def s #{:a :b :c :d})
(conj s :e)
(count s)
(seq s)
(= (conj s :e) #{:a :b :c :d :e})

(disj s :d)
(contains? s :b)


(get s :a)
;; sets are functions of their members
(s :a)
(s :dupa)



;; MAPS A Map is a collection that maps keys to values. Two different
;; map types are provided - hashed and sorted. Hash maps require keys
;; that correctly support hashCode and equals. Sorted maps require
;; keys that implement Comparable, or an instance of Comparator. Hash
;; maps provide faster access (log32N hops) vs (logN hops), but sorted
;; maps are, well, sorted. count is O(1). conj expects
;; another (possibly single entry) map as the item, and returns a new
;; map which is the old map plus the entries from the new, which may
;; overwrite entries of the old. conj also accepts a MapEntry or a
;; vector of two items (key and value). seq returns a sequence of map
;; entries, which are key/value pairs. Sorted map also supports rseq,
;; which returns the entries in reverse order. Maps implement IFn, for
;; invoke() of one argument (a key) with an optional second
;; argument (a default value), i.e. maps are functions of their
;; keys. nil keys and values are ok.
(def some-map {:dupa "lamapa" 1 23232 "key" "value"})

;; , is like whitespace in clojure
(def scores {:Daras  1400
             :Przemo   1240
             :Jacek 1024
             :Sasin 70000000})

(assoc scores "Don Stanisław" 0)   ;; add new keys
(assoc scores :Jacek -666)         ;; or replace existing
(dissoc scores :Jacek)             ;; remove
(get scores :Jacek)                ;; get
(scores :Jacek)                    ;; use function of members
(scores :Placek 0)                 ;; use optional default value
(:Jacek scores)                    ;; if you use keywords you can pass array to keyword function :P
(contains? scores :Daras)          ;; has key?
(find scores :Przemo)
(keys scores)
(vals scores)

(def new-scores {"Bubuś" 300 "Mimi" 900})
(merge scores new-scores)


(def new-scores {:Daras 300 :Przemo 900})
(merge-with + scores new-scores) ; :)
(merge-with #(/ %1 %2) scores new-scores) ; :)
(merge-with - scores {:Sasin 70000000}) ;; Sasin przejebał 1 Sasina

;; sorted
(def sm (sorted-map
         "Bravo" 204
         "Alfa" 35
         "Sigma" 99
         "Charlie" 100))
(keys sm)
(vals sm)


;; nested
(def company
  {:name "Kinguin"
   :address {:street "Grunwaldzka 64"
             :city "Poznan"
             :state "WLKP"}})

(get-in company [:address :city])
(assoc-in company [:address :street] "Remote")


;; building map spoiler! out of scope
(def players #{"Alice" "Bob" "Kelly"})
(zipmap players (range 1 4))
(zipmap [:a :b :c] [1 2 3])

(into {} (map (fn [player] [player 0]) players))
(reduce (fn [m player]
          (assoc m player 0))
        {} ; initial value
        players)

;; errors
(def bad-lookup-map nil)
(bad-lookup-map :foo)            ;; so functional but lookout for nils :P




;; ARRAY-MAP - When doing code form manipulation it is often desirable to have a map which maintains key order. An array map is such a map - it is simply implemented as an array of key val key val…​ As such, it has linear lookup performance, and is only suitable for very small maps. It implements the full map interface. New ArrayMaps can be created with the array-map function. Note that an array map will only maintain sort order when un-'modified'. Subsequent assoc-ing will eventually cause it to 'become' a hash-map.

;; STRUCT-MAP - Most uses of StructMaps would now be better served by records.

;; RECORD

(defrecord Person [first-name last-name age occupation])

;; Positional constructor - generated
(def kelly (->Person "Kelly" "Keen" 32 "Programmer"))

;; Map constructor - generated
(def kelly (map->Person
             {:first-name "Kelly"
              :last-name "Keen"
              :age 32
              :occupation "Programmer"}))
