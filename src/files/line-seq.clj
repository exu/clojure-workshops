;; (-> (java.io.File. ".") .getAbsolutePath)

;; line looks like this:
;; 2020-10-09 19:25:52.306  INFO 1 --- [http-nio-8080-exec-815] n.k.service.filestorage.service.Storage  : Created document: 5f80b94046e0fb0001be6b43

(with-open [rdr (clojure.java.io/reader "src/files/all.log")]
  (->> (line-seq rdr) ;; get lazy collection of lines from reader
       (map #(subs % 0 19)) ;; take date with time -> result: (("2020-10-09 19:25:23") ("2020-10-09 19:25:52"))
       (frequencies) ;; here we have something like this -> {"2020-10-09 19:25:23" 23, "2020-10-09 19:25:24" 1}
       (sort-by val >) ;; sorting by map value by function `>`
       (take 5) ;; take 5 first elements
       (doall))) ;; do for all lazy elements (w/o this you'll get exception)
