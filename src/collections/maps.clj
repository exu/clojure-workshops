(def scores {"Fred"  1400
             "Bob"   1240
             "Angela" 1024})

(def scores {"Fred" 1400, "Bob" 1240, "Angela" 1024})


(assoc scores "Sally" 0 :fally 29)


(assoc scores :fally 12839283)

(dissoc scores "Bob")

(contains? scores "Bob")


(def directions {:north 0
                 :east 1
                 :south 2
                 :west 3})

(directions :south)

;; lookout for nil maps
(do
  (def bad-lookup-map nil)
  (bad-lookup-map :foo))


;; default values
(get scores "SRAM" 0)
(directions :northwest -1)


(contains? scores "Fred")
(find scores "Fred") ;; ["Fred" 1400]
(find scores "SRAM") ;; nil

;; keys or values
(keys scores)
(vals scores)


;; creating maps from arrays
(def players #{"Alice" "Bob" "Kelly"})
(zipmap players (range 1 12 3))

;; with map and into
(into {} (map (fn [player] [player 0]) players))

;; with reduce
(reduce (fn [m player]
          (assoc m player 0))
        {} ; initial value
        players)


;; combine two maps

(def new-scores {"Angela" 300 "Jeff" 900})
(merge scores new-scores)

(def new-scores {"Fred" 550 "Angela" 900 "Sam" 1000})
(merge-with + scores new-scores)


;; sorted
(let [sm (sorted-map
         "Bravo" 204
         "Alfa" 35
         "Sigma" 99
         "Charlie" 100)]
    sm)
