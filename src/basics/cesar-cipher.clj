(defn caesar-cipher [words offset]
  "Assumes offset >=0, words entirely lowercase English characters or spaces"
  (let [alphabet-chars (map char "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890")
        alphabet-shifted (->> (cycle alphabet-chars)
                              (take 100)
                              (drop offset))
        shifted-map (-> (zipmap alphabet-chars alphabet-shifted)
                        (assoc \space \space))]
    (apply str (map shifted-map (map char words)))))


(caesar-cipher "Dupa jasia", 1)
