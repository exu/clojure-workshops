(->
 2
 (+ 3)
 (- 12)
 (* 5))




;; I did not fully get what -> (thrush or thread) did until I visualized it like this:

(-> expr f1 f2 f3)  ;same as (f3 (f2 (f1 expr)))

(-> expr            ;same as form above
    f1              ;just a different visual layout
    f2
    f3)

;this form is equivalant and shows the lists for f1, f2, f3.
(->         expr     ; expr treaded into first form
        (f1     )    ;  |   result threaded into next form
    (f2          )   ;  |   and so on...
(f3               )) ;  V   the lists (f1

(f3 (f2 (f1 expr)))  ;the result is the same as this



(-> 41 inc dec inc)   ;same as (inc (dec (inc 41)))


(->            41     ;same as above but more readable
          (inc   )
     (dec         )
(inc               ))


(inc (dec (inc 41)))  ;easier to see equivalence with above form.


(-> 4 (* 4 3) (- 6))  ;same as (- (* 4 3 4) 6)


(->   4               ;      4
   (*   3 4)          ;   (* 4 3 4)
(-           6))      ;(- (* 4 3 4) 6)


(- (* 4 3 4) 6)       ;easier to see equivalence with above form.
