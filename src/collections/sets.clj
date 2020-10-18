(def players #{"Alice", "Bob", "Kelly"})

;; adding
(conj players "Fred")
(conj players "Fred" "Fred" "Fred" "Bob")


;; Sorted set
(conj (sorted-set) "Bravo" "Charlie" "Sigma" "Alpha")


;; into is used for putting one collection into another.
(def players #{"Alice" "Bob" "Kelly"})
(def new-players ["Tim" "Sue" "Greg"])
(into players new-players)
