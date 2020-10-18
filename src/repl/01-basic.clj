;; https://clojure.org/guides/repl/basic_usage

(require '[clojure.repl :refer :all])

(doc nil?)

(doc clojure.string/upper-case)

(source some?)
(source nil?)

(print "\n\n")

;; all names in ns
(dir clojure.string)


(dir clojure.repl)

(apropos "index")

(find-doc "indexed")
