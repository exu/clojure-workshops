(def my-promise (promise))
(deliver my-promise (+ 1 2))
(deliver my-promise (let [dupa (Thread/sleep 1000)] "dupa"))
@my-promise



;; Example 2 The API call waits one second before returning a result
;; to simulate the time it would take to perform an actual call.

;; To show how long it will take to check the sites synchronously,
;; we’ll use some to apply the satisfactory? function to each element
;; of the collection and return the first truthy result, or nil if
;; there are none. When you check each site synchronously, it could
;; take more than one second per site to obtain a result, as the
;; following code shows:
(def yak-butter-international
  {:store "Yak Butter International"
    :price 90
    :smoothness 90})
(def butter-than-nothing
  {:store "Butter Than Nothing"
   :price 150
   :smoothness 83})
;; This is the butter that meets our requirements
(def baby-got-yak
  {:store "Baby Got Yak"
   :price 94
   :smoothness 99})

(def supa-dupa-butter
  {:store "Supa Dupax"
   :price 94
   :smoothness 99})

(defn mock-api-call
  [result]
  (Thread/sleep 1000)
  result)

(defn satisfactory?
  "If the butter meets our criteria, return the butter, else return false"
  [butter]
  (and (<= (:price butter) 100)
       (>= (:smoothness butter) 97)
       butter))


(time (some (comp satisfactory? mock-api-call)
            [yak-butter-international butter-than-nothing baby-got-yak]))



;; Here I’ve used comp to compose functions, and I’ve used time to
;; print the time taken to evaluate a form. You can use a promise and
;; futures to perform each check on a separate thread. If your
;; computer has multiple cores, this could reduce the time it takes to
;; about one second:


(time
 (let [butter-promise (promise)]
   (doseq [butter [yak-butter-international butter-than-nothing baby-got-yak supa-dupa-butter]]
     (println butter)
     (future (if-let [satisfactory-butter (satisfactory? (mock-api-call butter))]
               (do
                 (println "Delivering" butter)
                 (deliver butter-promise satisfactory-butter)))))
   (println "And the winner is:" @butter-promise)))

;; promise can get only one result here we got 2 objects which
;; fullfills the satisfactory? criterium

;; {:store Yak Butter International, :price 90, :smoothness 90}
;; {:store Butter Than Nothing, :price 150, :smoothness 83}
;; {:store Baby Got Yak, :price 94, :smoothness 99}
;; {:store Supa Dupax, :price 94, :smoothness 99}

;; 2 satisfactory? are true
;; Delivering {:store Baby Got Yak, :price 94, :smoothness 99}
;; Delivering {:store Supa Dupax, :price 94, :smoothness 99}

;; but only fastest one is passed to promise
;; And the winner is: {:store Baby Got Yak, :price 94, :smoothness 99}
;; "Elapsed time: 1003.139859 msecs"


;; -------------------------------------------------------
;; timeouts

(let [p (promise)]
  (deref p 1000 "timed out"))
;; This creates a promise, p, and tries to dereference it. The number
;; 1000 tells deref to wait 1000 milliseconds, and if no value is
;; available by then, to use the timeout value, "timed out".



;; You can also use promises to register callbacks, achieving the same
;; functionality that you might be used to in JavaScript. JavaScript
;; callbacks are a way of defining code that should execute
;; asynchronously once some other code finishes. Here’s how to do it
;; in Clojure:

(let [ferengi-wisdom-promise (promise)]

  (future
    (println "Start")
    (println "Here's some Ferengi wisdom:" @ferengi-wisdom-promise))

  (Thread/sleep 1000)
  (deliver ferengi-wisdom-promise "Whisper your way to success."))
