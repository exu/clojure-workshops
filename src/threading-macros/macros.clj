(defn transform [person]
   (update (assoc person :hair-color :gray) :age inc))

(transform {:name "Socrates", :age 39})


(defn transform [person]
   (-> person
      (assoc :hair-color :gray)
      (update :age inc)
      (update :age inc)))

;; ,,, are like whitespaces
;;; so -> put threded element as first param to function call
(defn transform [person]
   (-> person
      (assoc ,,, :hair-color :gray)
      (update ,,, :age inc)))

(defn calculate []
   (reduce + (map #(* % %) (filter odd? (range 10)))))

;; ->> put on second place
(defn calculate* []
   (->> (range 10)
        (filter odd? ,,,)
        (map #(* % %) ,,,)
        (reduce + ,,,)))

(def a-string "PREFIX ajkdsajdakjsd ")
(-> a-string clojure.string/lower-case (.startsWith "prefix"))


;; as->
(as-> [:foo :bar] v
  (map name v)
  (first v)
  (.substring v 1))



(as-> [[1 2 3 4 5]] i
  (first i)
  (map #(* % %) i))



(as-> [1 2 3 4] v
  (map name v)
  (second v)
  (+ v 1 ))



(when-let [a-str nil]
  (Long/parseLong a-str))


;; If a-map lacks the key :counter, the entire expression will evaluate to nil rather than raising an exception. In fact, this behavior is so useful that it is common to see some-> used when threading is not required:
(let [a-map {:a 2323 :b 344 :c 1 :counter "32434"}]
  (some-> a-map :counter Long/parseLong inc))


(defn describe-number [n]
  (cond-> []
    (odd? n) (conj "odd")
    (even? n) (conj "even")
    (zero? n) (conj "zero")
    (pos? n) (conj "positive")))


(describe-number 3)
(describe-number 4)

;; cond->> inserts the threaded value as the last argument of each form but works analogously otherwise.
