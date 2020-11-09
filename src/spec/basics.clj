(require '[clojure.spec.alpha :as s])

(s/conform even? 1000)
(s/valid? even? 10)

(s/valid? nil? nil)  ;; true
(s/valid? string? "abc")  ;; true
(s/valid? #(> % 5) 10) ;; true
(s/valid? #(> % 5) 0) ;; false

(import java.util.Date)
(s/valid? inst? (Date.))  ;; true


(s/def ::even-strings (s/& (s/* string?) #(even? (count %))))
(s/valid? ::even-strings ["a"])  ;; false
(s/valid? ::even-strings ["a" "b"])  ;; true
(s/valid? ::even-strings ["a" "b" "c"])  ;; false
(s/valid? ::even-strings ["a" "b" "c" "d"])  ;; true


(s/valid? #{:club :diamond :heart :spade} :club) ;; true
(s/valid? #{:club :diamond :heart :spade} 42) ;; false

(s/valid? #{42} 42) ;; true



;; Specs are registered using def. It’s up to you to register the
;; specification in a namespace that makes sense (typically a
;; namespace you control).

(s/def ::date inst?)
(s/def ::suit #{:club :diamond :heart :spade})

(s/valid? ::date (java.util.Date.))
(s/conform ::suit :sraka)


;; A registered spec identifier can be used in place of a spec definition
;; in the operations we’ve seen so far - conform and valid?.


(doc ::date)
(doc ::suit)

;; Composing predicates - The simplest way to compose specs is with
;; and and or. Let’s create a spec that combines several predicates
;; into a composite spec with s/and:
(s/def ::big-even (s/and int? even? #(> % 1000)))
(s/valid? ::big-even :foo) ;; false
(s/valid? ::big-even 10) ;; false
(s/valid? ::big-even 100000) ;; true

(doc ::big-even)


(s/def ::name-or-id (s/or :name string?
                          :id   int?))
(s/valid? ::name-or-id "abc") ;; true
(s/valid? ::name-or-id 100) ;; true
(s/valid? ::name-or-id :foo) ;; false

(s/conform ::name-or-id "abc")
(s/conform ::name-or-id 100)

(s/valid? string? nil)
(s/valid? (s/nilable string?) nil)


(s/explain ::suit 42);; 42 - failed: #{:spade :heart :diamond :club} spec: :user/suit
(s/explain ::big-even 5);; 5 - failed: even? spec: :user/big-even
(s/explain ::name-or-id :foo)

(s/explain-data ::name-or-id :foo)



;;

(def email-regex #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$")
(s/def ::email-type (s/and string? #(re-matches email-regex %)))

(s/def ::acctid int?)
(s/def ::first-name string?)
(s/def ::last-name string?)
(s/def ::email ::email-type)

(s/def ::person (s/keys :req [::first-name ::last-name ::email]
                        :opt [::phone]))



(s/valid? ::person
  {::first-name "Bugs"
   ::last-name "Bunny"
   ::email "bugs@example.com"})
;;=> true

;; Fails required key check
(s/explain ::person
  {::first-name "Bugs"})
;; #:my.domain{:first-name "Bugs"} - failed: (contains? % :my.domain/last-name)
;;   spec: :my.domain/person
;; #:my.domain{:first-name "Bugs"} - failed: (contains? % :my.domain/email)
;;   spec: :my.domain/person

;; Fails attribute conformance
(s/explain ::person
  {::first-name "Bugs"
   ::last-name "Bunny"
   ::email "n/a"})
;; "n/a" - failed: (re-matches email-regex %) in: [:my.domain/email]
;;   at: [:my.domain/email] spec: :my.domain/email-type
