;; https://clojure.org/reference/transducers
;; https://stackoverflow.com/questions/26317325/can-someone-explain-clojure-transducers-to-me-in-simple-terms

;;  reducing function is the kind of function you’d pass to reduce -
;;  it is a function that takes an accumulated result and a new input
;;  and returns a new accumulated result:

;; reducing function signature
;; whatever, input -> whatever

;; A transducer (sometimes referred to as xform or xf) is a
;; transformation from one reducing function to another:

;; transducer signature:
;; (whatever, input -> whatever) -> (whatever, input -> whatever)


;; Most sequence functions included in Clojure have an arity that
;; produces a transducer. This arity omits the input collection; the
;; inputs will be supplied by the process applying the
;; transducer. Note: this reduced arity is not currying or partial
;; application.

(filter odd?) ;; returns a transducer that filters odd
(map inc)     ;; returns a mapping transducer for incrementing
(take 5)      ;; returns a transducer that will take the first 5 values



(def xf
  (comp
   (filter odd?)
   (map inc)
   (take 5)))


(->> [1 3 4 45 65 6 7 7 878 3298]
     (filter odd?)
     (map inc)
     (sort)
     (take 5))
