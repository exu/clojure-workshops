(macroexpand `#(println %1 %2 %3))

(macroexpand `#(println %4))


;; dereference
(def x (atom 1))
(deref x)
@x


;; ^ is the metadata marker. Metadata is a map of values (with
;; shorthand option) that can be attached to various forms in
;; Clojure. This provides extra information for these forms and can be
;; used for documentation, compilation warnings, typehints, and other
;; features.

(def ^:debug five 5)
(meta #'five)


;; Another use of ^ is for type hints. These are used to tell the
;; compiler what type the value will be and allow it to perform type
;; specific optimizations thus potentially making resultant code
;; faster:
(def ^Float five 5)
(meta #'five)


(def ^Integer ^:debug ^:private ^:dupa five 5)
(meta #'five)


;; Quoting is used to indicate that the next form should be read but
;; not evaluated. The reader expands ' into a call to the quote
;; special form.
'(1 3 4)
(quote (1 2 3))


;; Keywords :
(keyword "test")
:dupa

;; Keywords can also be invoked as functions to look themselves up as
;; a key in a map:
(def my-map {:one 1 :two 2})

(:one my-map)
(:three my-map)
(:three my-map 3)
(get my-map :three 3)


;; :: - Auto-resolved keyword
;; :: is used to auto-resolve a keyword in the current namespace. If
;; no qualifier is specified, it will auto-resolve to the current
;; namespace. If a qualifier is specified, it may use aliases in the
;; current namespace:
 ::my-keyword



;; #: and #:: - Namespace Map Syntax Namespace map syntax was added in
;; Clojure 1.9 and is used to specify a default namespace context when
;; keys or symbols in a map where they share a common namespace.  The
;; #:ns syntax specifies a fully-qualified namespace map prefix n
;; alias in the namespace map prefix with, where ns is the name of a
;; namespace and the prefix precedes the opening brace { of the map.
;; For example, the following map literal with namespace syntax:
#:person{:first "Han"
         :last "Solo"
         :ship #:ship{:name "Millennium Falcon"
                      :model "YT-1300f light freighter"}}



(defmacro debug [body]
  `(let [val# ~body]
     (println "DEBUG: " val#)
     val#))

(debug (+ 2 2))

(def five 5)
`[inc ~(+ 1 five)]


(def three-and-four (list 3 4))

;; ~ - Unquote
`(1 ~three-and-four)

;; ~@ - Unquote splicing
`(1 ~@three-and-four)



(defmacro m [] `(let [x 1] x))
(m)
(defmacro m [] `(let [x# 1] x#))
(macroexpand '(m))


;; #? - Reader conditional Reader conditionals are designed to allow
;; different dialects of Clojure to share common code. The reader
;; conditional behaves similarly to a traditional cond. The syntax for
;; usage is #? and looks like this:

#?(:clj  (Clojure expression)
   :cljs (ClojureScript expression)
   :cljr (Clojure CLR expression)
   :default (fallthrough expression))


;; _ bypass param/value
(def a (atom {}))

(add-watch a :watcher
  (fn [key atom old-state new-state]
    (prn "-- Atom Changed --")
    (prn "key" key)
    (prn "atom" atom)
    (prn "old-state" old-state)
    (prn "new-state" new-state)))

(reset! a {:dup "bar"})
(reset! a {:ass "bar"})


;; The name of my account can change, and I want to update another atom accordingly.
;; I just take the fourth argument that contains the new state and I ignore the other arguments.

(let [account (atom {:name "pending"
                     :funds 100.50
                     :profit-loss 23.45})
      label-account-name (atom "no-name-yet")]
   (add-watch account :listener-one #(reset! label-account-name (:name %4)))
   (println "Before swap:" @label-account-name)
   (swap! account assoc :name "CFD")
   (println "After swap:" @label-account-name))


;; , - Whitespace character In Clojure, , is treated as whitespace,
;; exactly the same as spaces, tabs, or newlines. Commas are thus
;; never required in literal collections, but are often used to
;; enhance readability:
(def m {:a 1, :b 2, :c 3} )



(require '[clojure.spec.alpha :as s])
