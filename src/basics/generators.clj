;; Lazy sequences in Clojure have two parts, the head is the first
;; unrealized element of the sequence, and the thunk is an uninvoked,
;; argument-less function that, when called, will realize the rest of
;; the elements in the sequence starting from the head. Clojure’s core
;; functions, like map, filter, and others are already optimized to
;; work with lazy sequences, but you can also utilize the lazy-seq
;; macro to produce a lazy sequence when producing potentially large
;; collections. Lazy sequences can have some unexpected “gotcha’s”.

(take 100 (cycle [1 2 4]))

(take 10 (cycle (range 0 3)))

(defn positive-numbers
  ([] (positive-numbers 1))
  ([n] (lazy-seq (cons n (positive-numbers (inc n))))))

(take 10 (positive-numbers))



(defn fib
  ([]
   (fib 1 1))
  ([a b]
   (lazy-seq (cons a (fib b (+ a b))))))

(take 5 (fib))


;; I like to think of lazy sequences in Clojure as anonymous
;; collections. We don’t know what elements inhabit this collection,
;; nor do we know how many elements are in the collection. The only
;; things we do know about a lazy sequence is that it implements the
;; Clojure’s sequence abstraction, and that it’s got a head.

(defn lazy-range [i limit]
  (lazy-seq
    (when (< i limit)
      (println "REALIZED" i)
      (cons i (lazy-range (inc i) limit)))))

(println "---------")

(def first-ten (lazy-range 0 10))

(take 2 first-ten)
(take 8 first-ten)
