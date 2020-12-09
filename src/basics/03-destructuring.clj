(def my-vector [123 234 345 456 567])

(let [[a b c d e] my-vector]
  (println (str "a:",  a ", b:" b ", c:" c ", d:" d ", e:" e)))

(def my-map {:a 123 :b 234 :c 345 :d 456 :e 567})

(let [{a :a b :b c :c d :d e :e} my-map]
  (println (str a ", " b ", " c ", " d ", " e)))

(let [[a b & more :as all] my-vector]
  (println (str "I got " more " from " all)))

(let [[a b c d e] "hello"]
  (println (str e d c b a)))




(defn my-first
  [[first-thing & rest]] ; Notice that first-thing is within a vector
  (println rest)
  first-thing)

(my-first ["oven" "bike" "war-axe"])



(defn chooser
  [[first-choice second-choice & unimportant-choices]]
  (println (str "Your first choice is: " first-choice))
  (println (str "Your second choice is: " second-choice))
  (println (str "We're ignoring the rest of your choices. "
                "Here they are in case you need to cry over them: "
                (clojure.string/join ", " unimportant-choices))))

(chooser ["Marmalade", "Handsome Jack", "Pigpen", "Aquaman"])
