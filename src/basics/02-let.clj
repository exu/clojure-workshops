
;; Basic `let` Clojure let is used to define new variables in a local
;; scope. These local variables give names to values. In Clojure, they
;; cannot be re-assigned, so we call them immutable.

(let [dupa 1
      lampa [1 2 3 4 5]
      sraka {:a 1 :b 2}] ;; local vars defintion

  (println)
  (println lampa)
  (println dupa)
  (println sraka) ;; code

  [dupa sraka lampa] ) ;; return something







;; SHADOWING
;; In a Clojure `let`, You can name your variables however you want—even
;; reusing the name of an existing variable. Of course, when you do
;; that, your code can only refer to the most local definition. There
;; is no way to access the outer one. Doing this is called
;; shadowing. It's like the outer variable is in the shadow of the
;; local variable.

(def db "postgres://localhost:3456")

(defn fetch-users []
  (let [db "postgres://my-server.com:2232"] ;; this variable shadows the global
    (jdbc/query db "SELECT * FROM users"))) ;; refers to local db



;; You can shadow variables in same let
(require '[clojure.string :as str]) ;; eval this in REPL
(let [s "Dupa Lampa"
      s (str/upper-case s)
      s (str/trim s)
      s (str/replace s #" +" "-")]
  (println s))


(let [x 1
      y (* 2 x)  ;; refer to x above
      z (+ 4 y)] ;; refer to y above
  (println z))


;; println debug trick :)
;; (let [user (get-user user-id)
;;       _ (println user)           ; does compile
;;       account (get-account user) ; this line fails
;;       transaction (make-deposit account 100)]
;;   (save-transaction transaction))
