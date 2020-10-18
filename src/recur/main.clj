(defn recur-1 [in]
  "simple recur"
  (if (> (count in) 10)
    in
    (recur (str in in))))

(recur-1 "b")


(defn some-join [coll result]
  "some joins"
  (if (= 1 (count coll))
    (str result (first coll))
    (recur (rest coll) (str result (first coll) ", "))))


(some-join ["hello" "world" "love" "coding"] "Words: ")

(print "Dupa")


(require '[clojure.string :as s])

(s/trim " akjsdlksajkdjsa d ")
(s/includes? "asdkdjsakd ksajkdsakjd s" "ksa")
(is true :true)
