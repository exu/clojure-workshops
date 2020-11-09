(defmacro wait
  "Sleep `timeout` seconds before evaluating body"
  [timeout & body]
  `(do (Thread/sleep ~timeout) ~@body))



(let [saying3 (promise)]
  (future (deliver saying3 (wait 100 "Cheerio!")))

  (let [saying2 (promise)]
    (future (deliver saying2 (wait 400 "Pip pip!")))
    @(let [saying1 (promise)]
       (future (deliver saying1 (wait 200 "'Ello, gov'na!")))
       (println @saying1)
       saying1)
    (println @saying2)
    saying2)

  (println @saying3)
  saying3)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(-> (enqueue saying (wait 200 "'Ello, gov'na!") (println @saying))
    (enqueue saying (wait 400 "Pip pip!") (println @saying))
    (enqueue saying (wait 100 "Cheerio!") (println @saying)))

(defmacro enqueue
  ([q concurrent-promise-name concurrent serialized]
   `(let [~concurrent-promise-name (promise)]
      (future (deliver ~concurrent-promise-name ~concurrent))
      (deref ~q)
      ~serialized
      ~concurrent-promise-name))
  ([concurrent-promise-name concurrent serialized]
   `(enqueue (future) ~concurrent-promise-name ~concurrent ~serialized)))


;; Notice first that this macro has two arities in order to supply a
;; default value. The first arity ➊ is where the real work is done. It
;; has the parameter q, and the second arity does not. The second
;; arity ➍ calls the first with value (future) supplied for q; you’ll
;; see why in a minute. At ➋, the macro returns a form that creates a
;; promise, delivers its value in a future, dereferences whatever form
;; is supplied for q, evaluates the serialized code, and finally
;; returns the promise. q will usually be a nested let expression
;; returned by another call to enqueue, like in Listing 9-2. If no
;; value is supplied for q, the macro supplies a future so that the
;; deref at ➌ doesn’t cause an exception.


(time @(-> (enqueue saying (wait 200 "'Ello, gov'na!") (println @saying))
           (enqueue saying (wait 400 "Pip pip!") (println @saying))
           (enqueue saying (wait 100 "Cheerio!") (println @saying))))
