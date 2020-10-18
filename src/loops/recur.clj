;; using recur in loop
(loop [c 4]
  (if (> c 0)
    (do
      (println c)
      (recur (dec c)))))
