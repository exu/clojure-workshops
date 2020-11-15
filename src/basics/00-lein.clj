;; lein new app clojure-noob


;; This project skeleton isn’t inherently special or Clojure-y. It’s
;; just a convention used by Leiningen. You’ll be using Leiningen to
;; build and run Clojure apps, and Leiningen expects your app to have
;; this structure. The first file of note is project.clj at ➊, which
;; is a configuration file for Leiningen. It helps Leiningen answer
;; such questions as “What dependencies does this project have?” and
;; “When this Clojure program runs, what function should run first?”
;; In general, you’ll save your source code in src/<project_name>. In
;; this case, the file src/clojure_noob/core.clj at ➌ is where you’ll
;; be writing your Clojure code for a while. The test directory at ➍
;; obviously contains tests, and resources at ➋ is where you store
;; assets like images.

;; | .gitignore
;; | doc
;; | | intro.md
;; ➊ | project.clj
;; | README.md
;; ➋ | resources
;; | src
;; | | clojure_noob
;; ➌ | | | core.clj
;; ➍ | test
;; | | clojure_noob
;; | | | core_test.clj


(ns clojure-noob.core ; ➊
  (:gen-class))

(defn -main ; ➋
  "I don't do a whole lot...yet."
  [& args]
  (println "Hello, World!")) ;➌


;; The lines at ➊ declare a namespace, which you don’t need to worry
;; about right now. The -main function at ➋ is the entry point to your
;; program, a topic that is covered in Appendix A. For now, replace
;; the text "Hello, World!" at ➌ with "I'm a little teapot!". The full
;; line should read (println "I'm a little teapot!")).
