;; pluj :D
(spit "/tmp/test.txt" (repeat 3 "Look, I can write a file!")) ;

(map #(str "aaa" %) (slurp "/tmp/test.txt"))
;
;; "Look, I can write a file!"

;; siorb :D
(slurp "/etc/hosts" :encoding "UTF-16") ;
;; "潳琠䑡瑡扡獥ਣਣ潣慬桯"

(spit "/tmp/txt" "Something." :append true) ;
(slurp "/tmp/txt")
;; "Something.Something."





;;; FROM STACK ------------------------------------------------------------

;; Number 1: how to read an entire file into memory.
;; Not recommended when it is a really big file.
(slurp "/tmp/test.txt")

;; Number 2: how to read a file line by line.  The with-open macro
;; takes care that the reader is closed at the end of the body. The
;; reader function coerces a string (it can also do a URL, etc) into a
;; BufferedReader. line-seq delivers a lazy seq. Demanding the next
;; element of the lazy seq results into a line being read from the
;; reader.  Note that from Clojure 1.7 onwards, you can also use
;; transducers for reading text files.
(use 'clojure.java.io)
(with-open [rdr (reader "/tmp/test.txt")]
  (doseq [line (line-seq rdr)]
    (println line)))


;; Number 3: how to write to a new file.  Again, with-open takes care
;; that the BufferedWriter is closed at the end of the body. Writer
;; coerces a string into a BufferedWriter, that you use use via java
;; interop: (.write wrtr "something").
(use 'clojure.java.io)
(with-open [wrtr (writer "/tmp/test.txt")]
  (.write wrtr "Line to be written"))

;; You could also use spit, the opposite of slurp:
(spit "/tmp/test.txt" "Line to be written")


;; Number 4: append a line to an existing file.
;; Same as above, but now with append option.
(use 'clojure.java.io)
(with-open [wrtr (writer "/tmp/test.txt" :append true)]
  (.write wrtr "Line to be appended"))

;; Or again with spit, the opposite of slurp:

(spit "/tmp/test.txt" "Line to be written" :append true)
;; PS: To be more explicit about the fact that you are reading and writing to a File and not something else, you could first create a File object and then coerce it into a BufferedReader or Writer:

(reader (file "/tmp/test.txt"))
;; or
(writer (file "tmp/test.txt"))
;; The file function is also in clojure.java.io.

;; PS2: Sometimes it's handy to be able to see what the current directory (so ".") is. You can get the absolute path in two ways:

(System/getProperty "user.dir")
;; or
(-> (java.io.File. ".") .getAbsolutePath)
