(ns playsync.core
  (:require [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]))


;; Escape Callback Hell with Process Pipelines
(defn upper-caser
  [in]
  (let [out (chan)]
    (go (while true (>! out (clojure.string/upper-case (<! in)))))
    out))

(defn reverser
  [in]
  (let [out (chan)]
    (go (while true (>! out (clojure.string/reverse (<! in)))))
    out))

(defn doubler [in]
  (let [out (chan)]
    (go (while true (>! out (let [s (<! in)] (str s "<-dupa->" s)))))
    out))

(defn printer
  [in]
  (go (while true (println (<! in)))))

(let [in-chan (chan)
      upper-caser-out (upper-caser in-chan)
      reverser-out (reverser upper-caser-out)
      doubler-out (doubler reverser-out)]
  (printer doubler-out)
  (>!! in-chan "redrum")
  (>!! in-chan "repaid"))
