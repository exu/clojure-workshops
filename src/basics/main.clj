


(when true
  (println "Success!")
  "abra cadabra")

(map inc [0 1 2 3])

(map (fn [n] (-> n (* 2) (+ 2) (* 123))) [0 1 2 3 4])
