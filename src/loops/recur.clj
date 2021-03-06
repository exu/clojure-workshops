;; using recur in loop
(loop [c 4]
  (if (> c 0)
    (do
      (println c)
      (recur (dec c)))))



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
