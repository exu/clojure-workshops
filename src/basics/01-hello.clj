(println "Hello")



;; Func and var

(defn im-a-func [greeting]
  (println "Hello" greeting)
  (count greeting))

(def greeting "Dupa")

(im-a-func "Tralallala")
(im-a-func greeting)
