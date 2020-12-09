(ns tdd.add-test
  (:require [clojure.test :refer :all]
            [tdd.core :refer :all]))

(deftest add-test
  (testing "dupa"
    (is (= 3 (add 1 2)))))
