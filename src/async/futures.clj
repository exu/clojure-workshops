(future (Thread/sleep 4000)
        (println "I'll print after 4 seconds")
        "dupa")
(println "I'll print immediately")



;; You can use futures to run tasks on a separate thread and then
;; forget about them, but often you’ll want to use the result of the
;; task. The future function returns a reference value that you can
;; use to request the result. The reference is like the ticket that a
;; dry cleaner gives you: at any time you can use it to request your
;; clean dress, but if your dress isn’t clean yet, you’ll have to
;; wait. Similarly, you can use the reference value to request a
;; future’s result, but if the future isn’t done computing the result,
;; you’ll have to wait.

;; Requesting a future’s result is called dereferencing the future,
;; and you do it with either the deref function or the @ reader
;; macro. A future’s result value is the value of the last expression
;; evaluated in its body. A future’s body executes only once, and its
;; value gets cached. Try the following:


(let [result (future (println "this prints once")
                     (Thread/sleep 1000)
                     (+ 1 1))]
  (println "before: ")
  (println "deref: " (deref result))
  (println "@: " @result))

;; Requesting a future’s result is called dereferencing the future,
;; and you do it with either the deref function or the @ reader
;; macro. A future’s result value is the value of the last expression
;; evaluated in its body. A future’s body executes only once, and its
;; value gets cached. Try the following:

(let [result (future (Thread/sleep 3000)
                     (+ 1 1))]
  (println "The result is: " @result)
  (println "It will be at least 3 seconds before I print"))


;; Finally, you can interrogate a future using realized? to see if
;; it’s done running:
(realized? (future (Thread/sleep 1000)))

(let [f (future)]
  @f
  (realized? f))
