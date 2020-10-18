(defn number-summary
  "Computes a summary of the arithmetic properties of a number, as a data structure."
  [n]
  (let [proper-divisors (into (sorted-set)
                          (filter
                            (fn [d]
                              (zero? (rem n d)))
                            (range 1 n)))
        divisors-sum (apply + proper-divisors)]
    {:n n
     :proper-divisors proper-divisors
     :even? (even? n)
     :prime? (= proper-divisors #{1})
     :perfect-number? (= divisors-sum n)}))

(mapv number-summary [1 2 3 4 5])

(source into)
(source sorted-set)
(source filter)


(filter #(> 5 %)
        (range 1 10))





(def map {:dupa 1
          :lampa 2
          :djskadjaksd "jksadasd"
          })


(require '[clojure.pprint :as pp])

(pp/print-table (mapv number-summary []))

;; vis exceptions
(/ 1 0)
(pst *e)


;; exceptions
(defn divide-verbose
  "Divides two numbers `x` and `y`, but throws more informative Exceptions when it goes wrong.
  Returns a (double-precision) floating-point number."
  [x y]
  (try
    (double (/ x y))
    (catch Throwable cause
      (throw
       (ex-info
        (str "Failed to divide " (pr-str x) " by " (pr-str y))
        {:numerator x
         :denominator y}
        cause)))))


;;; we can add additional data to `ex-info`
(divide-verbose 1 0)
*e
;; :data key exists in exception map



(require '[clojure.java.javadoc :as jdoc])

(jdoc/javadoc #"a+")
(jdoc/javadoc java.util.regex.Pattern)


;; inspector
(require '[clojure.inspector :as insp])
(insp/inspect-table (mapv number-summary [2 5 6 28 42]))


;;

(require '[clojure.java.io :as io])

(def v (io/input-stream "https://www.clojure.org"))
(type v)
(ancestors (type v))
(jdoc/javadoc java.io.InputStream)

(require '[clojure.reflect :as reflect])
(reflect/reflect java.io.InputStream)
