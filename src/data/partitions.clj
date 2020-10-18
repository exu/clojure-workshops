(partition-by even? [0 1 1 1 1 1 2 3 4 5 6])

(group-by even? [0 1 1 1 1 1 2 3 4 5 6])

(map  even? (range 1 100))

(group-by odd? [1 1 1 2 2 3 3])

(group-by #(mod % 3) (range 100))


(map #(cond
            (= 0 (mod % (* 3 5))) "FizzBuzz"
            (= 0 (mod % 5)) "Buzz"
            (= 0 (mod % 3)) "Fizz"
            :else %) (range 100))
