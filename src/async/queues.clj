(ns playsync.core
  (:require [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]))

(defn append-to-file
  "Write a string to the end of a file"
  [filename s]
  (spit filename s :append true))

(defn format-quote
  "Delineate the beginning and end of a quote because it's convenient"
  [quote]
  (str "=== BEGIN QUOTE ===\n" quote "=== END QUOTE ===\n\n"))

(defn random-quote
  "Retrieve a random quote and format it"
  []
  (format-quote (slurp "https://www.braveclojure.com/random-quote")))



(defn snag-quotes
  [filename num-quotes]
  (let [c (chan)]
    (go (while true (append-to-file filename (<! c))))
    (dotimes [n num-quotes] (go (>! c (random-quote))))))

(snag-quotes "quotes" 10)
