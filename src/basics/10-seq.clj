;; Clojure defines many algorithms in terms of sequences (seqs). A seq
;; is a logical list, and unlike most Lisps where the list is
;; represented by a concrete, 2-slot structure, Clojure uses the ISeq
;; interface to allow many data structures to provide access to their
;; elements as sequences. The seq function yields an implementation of
;; ISeq appropriate to the collection. Seqs differ from iterators in
;; that they are persistent and immutable, not stateful cursors into a
;; collection. As such, they are useful for much more than foreach -
;; functions can consume and produce seqs, they are thread safe, they
;; can share structure etc.

;; Most of the sequence library functions are lazy, i.e. functions that
;; return seqs do so incrementally, as they are consumed, and thus
;; consume any seq arguments incrementally as well. Functions returning
;; lazy seqs can be implemented using the lazy-seq macro. See also
;; lazy.


;; The Seq interface
;; (first coll)
;; Returns the first item in the collection. Calls seq on its argument. If coll is nil, returns nil.
;; (rest coll)
;; Returns a sequence of the items after the first. Calls seq on its argument. If there are no more items, returns a logical sequence for which seq returns nil.
;; (cons item seq)
;; Returns a new seq where item is the first element and seq is the rest.
;; For a discussion of rest vs. next and lazy-seq see lazy.


;; Since Clojure 1.7, Clojure also provides `transducers`, an alternate
;; model for composable transformations on collections. Transducers
;; decouple the input, processing, and output parts of transformation
;; and allow reuse of transformations in more contexts, such as
;; core.async channels. Many of the sequence functions in the list
;; below will create transducers if the input collection is
;; omitted. See the Transducers page for more details.

;; https://clojure.org/reference/sequences

;; Creating a `seq`
;; Lazy seq from collection: `seq` `vals` `keys` `rseq` `subseq` `rsubseq`
(seq [1 2 3])
(vals {:a 2 :b 4})
(rseq [1 2 3])
(subseq (sorted-set 1 2 3 4) > 2) ; https://clojuredocs.org/clojure.core/subseq
(rsubseq (sorted-set 1 2 3 4) > 2)

;; Lazy seq from producer function: `lazy-seq` `repeatedly` `iterate`
;; Lazy seq from constant: `repeat` `range`
;; Lazy seq from other objects: `line-seq` `resultset-seq` `re-seq` `tree-seq` `file-seq` `xml-seq` `iterator-seq` `enumeration-seq`

;; Seq in, Seq out
;; Shorter seq from a longer seq: `distinct` `filter` `remove` `for` `keep` `keep-indexed`
(distinct [1 2 3 3 3 3 3])
(filter #(> % 3) [1 2 3 4 -3 5 -5])
(remove #(> % 3) [1 2 3 4 -3 5 -5])
(keep #(> % 3) [1 2 3 4 -3 5 -5])
;;             index value
(keep-indexed #(> %1 %2) [1 2 3 4 -3 5 -5])

(for  [x (range 10)] x) ; list comprehention
(take 100 (for [x (range 100000000) y (range 1000000) :while (< y x)] [x y]))
(for [x [0 1 2 3 4 5] :let [y (* x 3)] :when (even? y)] y)
(for [x1 [1 2 3], x2 [1 2 3]] (* x1 x2))
(for [x ['a 'b 'c], y [1 2 3]] [x y])

(time (dorun (for [x (range 1000) y (range 10000) :when (> x y)] [x y])))
(time (dorun (for [x (range 1000) y (range 10000) :while (> x y)] [x y])))

;; Demonstrating functional difference between :when and :while
(for [x (range 3) y (range 3)] [x y])
(for [x (range 3) y (range 3) :when (not= x y)] [x y])
(for [x (range 3) y (range 3) :while (not= x y)] [x y])
;; :while stops at the first collection element that evaluates to
;; false, like take-while

(for [x (range) :while (< x 5)
      y (range) :while (<= y x)
      z (range) :while (< z 2)]
  [x y z])

;; Longer seq from a shorter seq: `cons` `concat` `lazy-cat` `mapcat` `cycle` `interleave` `interpose`
(cons 12 [1 2 3])
(concat [23 43] [1 2 3])
(lazy-cat [23 43] [1 2 3] [1])
(let [f (fn [n] [(- n 1) n (+ n 1)])]
  (mapcat f [100 200 300]))
(take 20 (cycle [1 2 3])) ; a lazy (infinite!) sequence

(interleave [1 2 3] [:a :b :c] ["a" "b" "c"])
(interpose "\n" ["abra" "kadabra" "hehe"])


;; Seq with head-items missing: `rest` `next` `fnext` `nnext` `drop` `drop-while` `nthnext` `for`
(rest [1 2 3 4])
(next [1 2 3 4])
(fnext [1 2 3 4]) ; Same as (first (next x))
(nnext [1 2 3 4]) ; Same as (next (next x))
(drop 2 [1 2 3 4])
(drop-while odd? [1 1 1 1 1 1 1 1 1 1 1 2 3 4]) ;; ???

;; Seq with tail-items missing: `take` `take-nth` `take-while` `butlast` `drop-last` `for`
(take 2 [1 2 3 4])
(take-nth 2 [1 2 3 4])
(take-while #(> 3 %) [1 2 3 4])
(take-while (partial > 10) (iterate inc 0))
(butlast [1 2 3 4])
(drop-last [1 2 3 4])

;; Rearrangment of a seq: `flatten` `reverse` `sort` `sort-by` `shuffle`
(flatten [1 2 3 4 [1 2 3 4 [2 3 4]] [1 2 3 4]])
(reverse [1 2 3 4])
(sort [2 3 4 5 1])
(sort-by count ["aaa" "bb" "c"])
(sort-by first [[1 3] [2 8] [3 2]])
(sort-by last [[1 3] [2 8] [3 2]])
(sort-by :rank [{:rank 2} {:rank 3} {:rank 1}])
(sort-by (juxt :foo :bar)
         [{:foo 2 :bar 11}
          {:bar 99 :foo 1}
          {:bar 55 :foo 2}
          {:foo 1 :bar 77}]) ; ;sort by :foo, and where :foo is equal, sort by :bar
(sort-by val > {:foo 7, :bar 3, :baz 5})
(sort-by (comp count key) {"aaa" 7, "n" 3, "bbbbbb" 5})
(shuffle [1 2 3 4 5 6 7 8 9 0])

;; Create nested seqs: `split-at` `split-with` `partition` `partition-all` `partition-by`
(split-at 2 [1 2 3 4 5])
(split-with #(> 4 %) [1 2 3 4 5])
(partition 2 [1 2 3 4 5])
(partition-all 2 [1 2 3 4 5])
(partition-by :rank [{:rank 2} {:rank 3} {:rank 1 :name "aaa"} {:rank 1 :name "dupa"}])
(partition-by last [[1 3] [2 8] [3 2] [5 8] [3 3]])


;; Process each item of a seq to create a new seq: `map` `pmap` `mapcat` `for` `replace` `reductions` `map-indexed` `seque`
(map (partial + 1) [1 2 3 4 5])
(pmap (partial + 1) [1 2 3 4 5])
(def uris ["https://api.ratesapi.io/api/latest?symbols=PLN" "https://api.ratesapi.io/api/2010-03-12?symbols=PLN" "https://api.ratesapi.io/api/2010-01-12?symbols=PLN"])
(map #(slurp %) uris)
(pmap #(slurp %) uris)

(replace {2 10, 5 50} [1 2 3 4 5 5 5 5 3 3 3 22 2 2 2 ])
(reductions + [1 1 4 1])
(reduce + [1 1 4 1])
;; This is just like reduce except that the calculation is collected during the reduce.
(= (reduce + [1 2 3])
   (last (reductions + [1 2 3])))
(reductions conj [] '(1 2 3))
(reductions (fn [sum num] (+ sum num)) 100 [1 2 3 4 5])
(map-indexed
 (fn [key val] {:key key :val val :length (count val)})
 ["aa" "b" "ccc" "dedede"])

;; Using a seq
;; Extract a specific-numbered item from a seq: `first` `ffirst` `nfirst` `second` `nth` `when-first` `last` `rand-nth`
(rand-nth [1 2 3 4 5 6])
;; Construct a collection from a seq: `zipmap` `into` `reduce` `set` `vec` `into-array` `to-array-2d` `frequencies` `group-by`
(zipmap [:a :b :c :d :e :f] [5 4 3 2 1])
(into [1 2 3 4] [1 2 3 4])
(reduce + [1 2 3 4])
(set [1 2 3 4 1 2 3 4])
(vec '(1 2 3 4))
(into-array [1 2 3 4])
(to-array-2d [[1 2] [3 4]])
(frequencies [:a :b :c :a :a :b :c :c])
(group-by even? [1 2 3 4 5 7])

;; Pass items of a seq as arguments to a function: `apply`
(apply println [:a] [:b] [:c :d :e])
;; Compute a boolean from a seq: `not-empty` `some` `reduce` `seq?`
;; `every?` `not-every?` `not-any?` `empty?`
(not-empty [])
(some even? [1 5 3 3])
(seq? (range 10))
(seq? [1 2 23])
(every? even? [2 4 6])
(not-every? even? [2 1 4 6])
(not-any? even? [1 3 5 7])
(empty? [])

;; Search a seq using a predicate: `some` `filter`
;; Force evaluation of lazy seqs: `doseq` `dorun` `doall`
;; Check if lazy seqs have been forcibly evaluated: `realized?`


;; `sequence` vs `seq` The difference is that `sequence` always
;; returns a `seq` even if the collection is empty (in that case an
;; empty list), while seq returns nil for empty collections. Also,
;; sequence can be used with `transducers.`
