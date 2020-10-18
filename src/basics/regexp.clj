(use '[clojure.string :only (replace split)])

(second (re-matches #".*(jasia).*" "dupa jasia na mamasia"))

(def r (re-matcher #"s+(.*)(s+)" "success"))

(re-find r)
(re-groups r)

(re-seq #"s+(.*)(s+)" "success")


(clojure.string/replace "mississippi" #"i.." "obb")

;; from imoport
(replace "mississippi" #"i.." "obb")
(split "hello world" #"lo.wo")
