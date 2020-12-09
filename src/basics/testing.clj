(use 'clojure.test)

(testing "Arithmetic"

  (testing "with positive integers"
    (is (= 4 (+ 2 2)))
    (is (= 7 (+ 4 4))))

  (testing "with negative integers"
    (is (= -4 (+ -2 -2)))
    (is (= -1 (+ 3 -4))))

  (testing "with negative integers"
    (is (= -4 (+ 1 -2)))
    (is (= -1 (+ 3 -4))))


  )
