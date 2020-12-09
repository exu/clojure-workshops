
;; pretty print
(clojure.pprint/print-table [{:a 1 :b 2} {:a 2 :b 4}])
(clojure.pprint/code-dispatch "(map inc)")
(clojure.pprint/pprint {:a {:b {:c {:a {:b {:c :d}}} :d {:a {:b {:c :d}}}}}})

(str "dupa")
(clojure.pprint/pp) ;; prints last output (*1)




;; format
(format "%3d" 1) ;; "  1" ;
(format "%03d" 1) ;; "001" ;
(format "%.2f" 10.3456) ;; "10.35" ;
(format "%10s", "Clojure") ;; "   Clojure" ;
(format "%-10s", "Clojure") ;; "Clojure   " ;
(format "%-11.11s" "truncatefixedsize") ;; "truncatefix" ;
(format "%tT" (java.util.Calendar/getInstance)) ;; "22:15:11" ;


(require '[clojure.pprint :refer [cl-format]]) ;

(cl-format nil "~:d" 1000000) ;; "1,000,000" ;
(cl-format nil "~b" 10) ;; "1010" ;
(cl-format nil "Anno Domini ~@r" 25) ;; "Anno Domini XXV" ;

(cl-format nil "~r" 15883293982392839829929283892839328) ;; "one hundred fifty-eight" ;
(cl-format nil "~:r and ~:r" 1 2) ;; "first and second" ;
(cl-format nil "~r banana~:p" 1) ;; "one banana"
(cl-format nil "~r banana~:p" 2) ;; "two bananas" ;


(def fmt "~#[nope~;~a~;~a and ~a~:;~a, ~a~]~#[~; and ~a~:;, ~a, etc~].")
(apply cl-format nil fmt []) ;; nope.
(apply cl-format nil fmt [1 2]) ;; "1 and 2."
(apply cl-format nil fmt [1 2 3]) ;; "1, 2 and 3."
(apply cl-format nil fmt [1 2 3 4]) ;; "1, 2, 3, etc."

(def fmt "I see ~[no~:;~:*~r~] fish~:*~[es~;~:;es~].")

(cl-format nil fmt 0) ;; "I see no fishes."
(cl-format nil fmt 1) ;; "I see one fish."
(cl-format nil fmt 100) ;; "I see one hundred fishes."

(def paragraph
  ["This" "sentence" "is" "too" "long" "for" "a" "small" "screen"
  "and" "should" "appear" "in" "multiple" "lines" "no" "longer"
  "than" "20" "characters" "each" "."])

(println (cl-format nil "~{~<~%~1,20:;~A~> ~}" paragraph))
;; This sentence is too
;; long for a small
;; screen and should
;; appear in multiple
;; lines no longer than
;; 20 characters each.


(import 'java.util.HashMap)

(def java-map (HashMap. {:a "1" :b nil}))
(pr java-map)
(prn java-map) ;
;; {:a "1", :b nil}
(println java-map) ;
;; #object[java.util.HashMap 0x1ffddcad {:a=1, :b=null}]




(def data {:a [1 2 3]
           :b '(:a :b :c)
           :c {"a" 1 "b" 2}})

(pr-str data) ;
;; "{:a [1 2 3], :b (:a :b :c), :c {\"a\" 1, \"b\" 2}}"

(prn-str data) ;
;; "{:a [1 2 3], :b (:a :b :c), :c {\"a\" 1, \"b\" 2}}\n"

(print-str data) ;
;; "{:a [1 2 3], :b (:a :b :c), :c {a 1, b 2}}"

(println-str data) ;
;; "{:a [1 2 3], :b (:a :b :c), :c {a 1, b 2}}\n"




(require '[clojure.java.io :as io]) ;

(with-open [w (io/writer "/home/exu/tmp/dupa.txt")] ;
  (binding [*out* w] ;
    (print (range 100000)))) ;


(def data {:a ["red" "blue" "green"]
           :b '(:north :south :east :west)
           :c {"x-axis" 1 "y-axis" 2}})

data ;
;; {:a ["red" "blue" "green"], :b (:north :south :east :west), :c {"x-axis" 1, "y-axis" 2}}

(pp) ;
;; {:a ["red" "blue" "green"],
;;  :b (:north :south :east :west),
;;  :c {"x-axis" 1, "y-axis" 2}}

(pprint data) ;
;; {:a ["red" "blue" "green"],
;;  :b (:north :south :east :west),
;;  :c {"x-axis" 1, "y-axis" 2}}
