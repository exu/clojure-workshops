;; Using Clojure’s interop syntax, interacting with Java objects and
;; classes is straightforward. Let’s start with object interop syntax.

;; You can call methods on an object using (.methodName object). For
;; example, because all Clojure strings are implemented as Java
;; strings, you can call Java methods on them:

(.toUpperCase "By Bluebeard's bananas!")
(.indexOf "Let's synergize our bleeding edges" "y")



;; Clojure’s syntax allows you to pass arguments to Java
;; methods. In this example, at ➊ you passed the argument "y" to the
;; indexOf method.


(java.lang.Math/abs -3)

java.lang.Math/PI


;; All of these examples (except java.lang.Math/PI) use macros that
;; expand to use the dot special form. In general, you won’t need to
;; use the dot special form unless you want to write your own macros
;; to interact with Java objects and classes. Nevertheless, here is
;; each example followed by its macroexpansion:
(macroexpand-1 '(.toUpperCase "By Bluebeard's bananas!"))



;; Creating and mutating objects

;; You can create a new object in two ways: (new ClassName
;; optional-args) and (ClassName. optional-args):

(new String)
; => ""

(String.)
; => ""

(String. "To Davey Jones's Locker with ye hardies")
; => "To Davey Jones's Locker with ye hardies"


(java.util.Stack.)
; => []

➊ (let [stack (java.util.Stack.)]
  (.push stack "Latest episode of Game of Thrones, ho!")
  (.push stack "Latest dupa !")
  stack)
                                        ; => ["Latest episode of Game of Thrones, ho!"]

(let [stack (java.util.Stack.)]
  (.push stack "Latest episode of Game of Thrones, ho!")
  (.push stack "Latest episode of Batman, ho!")
  (.push stack "Latest episode of Smurfs, ho!")
  (second stack))


;; Clojure provides the doto macro, which allows you to execute
;; multiple methods on the same object more succinctly:

(doto (java.util.Stack.)
  (.push "Latest episode of Game of Thrones, ho!")
  (.push "Whoops, I meant 'Land, ho!'"))

(macroexpand-1
 '(doto (java.util.Stack.)
    (.push "Latest episode of Game of Thrones, ho!")
    (.push "Whoops, I meant 'Land, ho!'")))


;; IMPORTING

;; In Clojure, importing has the same effect as it does in Java: you
;; can use classes without having to type out their entire package
;; prefix:
(import java.util.Stack)
(Stack.)


(import [java.util Date Stack]
        [java.net Proxy URI])

(Date.)

;; usually youre importing classes in ns
(ns dupa.core
  (:import [java.util Date Stack]
           [java.net Proxy URI]))


;; Java classes

(System/getenv)
(System/getProperties)


;; IO

(let [file (java.io.File. "/")]
  (println (.exists file))
  (println (.canWrite file))
  (println (.getPath file)))


(spit "/tmp/hercules-todo-list"
"- kill dat lion brov
- chop up what nasty multi-headed snake thing")

(slurp "/tmp/hercules-todo-list")


(with-open [todo-list-rdr (clojure.java.io/reader "/tmp/hercules-todo-list")]
  (println (first (line-seq todo-list-rdr))))
