;; Member access
;; (.instanceMember instance args*)
;; (.instanceMember Classname args*)
;; (.-instanceField instance)
;; (Classname/staticMethod args*)
;; Classname/staticField


(.getEnclosingClass java.util.Map$Entry)


(defn date? [d] (instance? java.util.Date d))
(date? "asdsad")

(.toUpperCase "fred")



(System/getProperty "java.vm.version")

Math/PI

(let [hm (new java.util.HashMap)]
  (.put hm  "a" "ksadjjsad")
  hm)


(doto
    (new java.util.HashMap)
  (.put "a" 1)
  (.put "b" 2)
  (.get "a"))
