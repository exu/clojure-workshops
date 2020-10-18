(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})
(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))

(c-int character)
; => 10

(c-str character)
; => 4

(c-dex character)
                                        ; => 5

;; getting strength
((fn [c] (:strength (:attributes c))) character)




(defn spell-slots
  [char]
  (int (inc (/ (c-int char) 2))))

(spell-slots character)

;; with function composition
(def spell-slots-comp (comp int inc #(/ % 2) c-int))
(spell-slots character)



;; Clojure’s comp function can compose any number of functions. To get
;; a hint of how it does this, here’s an implementation that composes
;; just two functions:
(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))

((two-comp
  (fn [s] (print "Dupa" s))
  (fn [s] (print "Lampa" s))) "s")




;; memoization


(defn sleepy-identity
  "Returns the given value after 1 second"
  [x]
  (Thread/sleep 1000)
  x)
(sleepy-identity "Mr. Fantastico")
; => "Mr. Fantastico" after 1 second

(sleepy-identity "Mr. Fantastico")
; => "Mr. Fantastico" after 1 second



(def memo-sleepy-identity (memoize sleepy-identity))
(memo-sleepy-identity "Mr. Fantastico")
; => "Mr. Fantastico" after 1 second

(memo-sleepy-identity "Mr. Fantastico")
; => "Mr. Fantastico" immediately
