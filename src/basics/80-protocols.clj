;; https://clojure.org/reference/protocols

;; Clojure is written in terms of abstractions. There are abstractions
;; for sequences, collections, callability, etc. In addition, Clojure
;; supplies many implementations of these abstractions. The
;; abstractions are specified by host interfaces, and the
;; implementations by host classes. While this was sufficient for
;; bootstrapping the language, it left Clojure without similar
;; abstraction and low-level implementation facilities. The protocols
;; and datatypes features add powerful and flexible mechanisms for
;; abstraction and data structure definition with no compromises vs
;; the facilities of the host platform.

;; There are several motivations for protocols:
;; - Provide a high-performance, dynamic polymorphism construct as an alternative to interfaces
;; - Support the best parts of interfaces
;;   - specification only, no implementation
;;   - a single type can implement multiple protocols
;; - While avoiding some of the drawbacks
;;   - Which interfaces are implemented is a design-time choice of the type author,
;;     cannot be extended later (although interface injection might eventually address this)
;;   - implementing an interface creates an isa/instanceof type relationship and hierarchy
;; - Avoid the 'expression problem' by allowing independent extension of the set of types,
;;   protocols, and implementations of protocols on types, by different parties
;;   - do so without wrappers/adapters
;; - Support the 90% case of multimethods (single dispatch on type) while providing
;;   higher-level abstraction/organization


;; defprotocol will automatically generate a corresponding interface,
;; with the same name as the protocol, e.g. given a protocol
;; my.ns/Protocol, an interface my.ns.Protocol. The interface will
;; have methods corresponding to the protocol functions, and the
;; protocol will automatically work with instances of the interface.

(defprotocol AProtocol
  "A doc string for AProtocol abstraction"
  (bar [a b] "bar docs")
  (baz [a] [a b] [a b c] "baz docs"))

;; - No implementations are provided
;; - Docs can be specified for the protocol and the functions
;; - The above yields a set of polymorphic functions and a protocol object - all are namespace-qualified by the namespace enclosing the definition
;; - The resulting functions dispatch on the type of their first argument, and thus must have at least one argument
;; - defprotocol is dynamic, and does not require AOT compilation


(defprotocol P
  (foo [x])
  (bar-me [x] [x y]))

(deftype Foo [a b c]
  P
  (foo [x] a)
  (bar-me [x] b)
  (bar-me [x y] (+ c y)))

(bar-me (Foo. 1 2 3) 42)

;; TODO ???
(foo
 (let [x 42]
   (reify P
     (foo [this] 17)
     (bar-me [this] x)
     (bar-me [this y] x))))
