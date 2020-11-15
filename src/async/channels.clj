(ns playsync.core
  (:require [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go go-loop chan buffer close! thread
                     alts! alts!! timeout]]))



(def echo-chan (chan))
(go (println (<! echo-chan)))
(>!! echo-chan "hello!")


(def loop-chan (chan))
(go-loop []
  (println (<! loop-chan))
  (recur))

(dotimes [n 4] (>!! loop-chan (str "ketchup" n)))



(def echo-buffer (chan 2))
(>!! echo-buffer "ketchup")
;; => true
(>!! echo-buffer "ketchup")
;; => true
((>!! echo-buffer "ketchup"))
; This blocks because the channel buffer is full (and no one listens)



;; push to channels and read from them
;; when you know elements count
(def hi-chan (chan))
(doseq [n (range 1000)]
  (go (>! hi-chan (str "hi " n))))


(doseq [n (range 1000)]
  (go (println (<! hi-chan))))



;; threads ------------------------------------------------------------


;; There are definitely times when you’ll want to use blocking instead
;; of parking, like when your process will take a long time before
;; putting or taking, and for those occasions you should use thread:
(thread (println (<!! echo-chan)))
(>!! echo-chan "mustard")
; => true
; => mustard



;; Hot-dog machine example

(defn hot-dog-machine
  []
  (let [in (chan)
        out (chan)]
    (go (<! in)
        (>! out "hot dog"))
    [in out]))


(let [[in out] (hot-dog-machine)]
  (>!! in "pocket dupa")
  (<!! out))


;; There’s a lot more code here, but the strategy is
;; straightforward. The new function hot-dog-machine-v2 allows you to
;; specify the hot-dog-count. Within the go block at ➊, it dispenses a
;; hot dog only if the number 3 (meaning three dollars) is placed on
;; the in channel; otherwise, it dispenses wilted lettuce, which is
;; definitely not a hot dog. Once a process has taken the output, the
;; hot dog machine process loops back with an updated hot dog count
;; and is ready to receive money again.  When the machine process runs
;; out of hot dogs, the process closes the channels at ➋. When you
;; close a channel, you can no longer perform puts on it, and once
;; you’ve taken all values off a closed channel, any subsequent takes
;; will return nil.

(defn hot-dog-machine-v2
  [hot-dog-count]

  (let [in (chan)
        out (chan)]

    (go (loop [hc hot-dog-count]
          (if (> hc 0)
            (println "waiting for input")
            (let [input (<! in)]
              (if (= 3 input) ; ➊
                (do (>! out "hot dog")
                    (recur (dec hc)))
                (do (>! out "wilted lettuce")
                    (recur hc))))
            (do (close! in) ; ➋
                (close! out)))))

    [in out]))

(let [[in out] (hot-dog-machine-v2 2)]
  (>!! in "pocket lint")
  (println (<!! out))

  (>!! in 3)
  (println (<!! out))

  (>!! in 3)
  (println (<!! out))

  (>!! in 3)

  (println <!! out))



;; One way you can create a pipeline of processes: just make the in
;; channel of one process the out channel of another. The following
;; example does just that, passing a string through a series of
;; processes that perform transformations until the string finally
;; gets printed by the last process:
(let [c1 (chan)
      c2 (chan)
      c3 (chan)]
  (go (>! c2 (clojure.string/upper-case (<! c1))))
  (go (>! c3 (clojure.string/reverse (<! c2))))
  (go (println (<! c3)))
  (>!! c1 "redrum"))





;; alts!!  lets you use the result of the first successful channel
;; operation among a collection of operations.

;; In that example, we uploaded a set of headshots to a
;; headshot-sharing site and notified the headshot owner when the
;; first photo was uploaded. Here’s how you’d do the same with alts!!:

(defn upload
  [headshot c]
  (go (Thread/sleep (rand 10000))
      (>! c headshot)))

(let [c1 (chan)
      c2 (chan)
      c3 (chan)]
  (upload "serious.jpg" c1)
  (upload "fun.jpg" c2)
  (upload "sassy.jpg" c3)
  (let [[headshot channel] (alts!! [c1 c2 c3])]
    (println "Sending headshot notification for" headshot)))
; => Sending headshot notification for sassy.jpg



(let [c1 (chan)]
  (upload "serious.jpg" c1)
  (let [[headshot channel] (alts!! [c1 (timeout 1000)])]
    (if headshot
      (println "Sending headshot notification for" headshot)
      (println "Timed out!"))))
; => Timed out!




;; Here you’re creating two channels and then creating a process
;; that’s waiting to perform a take on c2. The vector that you supply
;; to alts!! tells it, “Try to do a take on c1 and try to put "put!"
;; on c2. If the take on c1 finishes first, return its value and
;; channel. If the put on c2 finishes first, return true if the put
;; was successful and false otherwise.” Finally, the result of
;; value (which is true, because the c2 channel was open) prints and
;; shows that the channel returned was indeed c2.
(let [c1 (chan)
      c2 (chan)]
  (go (println (<! c2)))
  (let [[value channel] (alts!! [c1 [c2 "put!"]])]
    (println "Channel value " value (str channel))
    (= channel c2)))


;; Like <!! and >!!, alts!! has a parking alternative, alts!, which
;; you can use inside go blocks. alts! is a nice way to exercise some
;; choice over which of a group of channels you put or take from. It
;; still performs puts and takes, so the same reasons to use the
;; parking or blocking variation apply.
