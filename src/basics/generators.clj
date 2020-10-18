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
