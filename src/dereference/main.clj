(def multiplayer-game-state
  {:joe {:class "Ranger"
         :weapon "Longbow"
         :score 100}
   :jane {:class "Knight"
          :weapon "Greatsword"
          :score 140}
   :ryan {:class "Wizard"
          :weapon "Mystic Staff"
          :score 150}})

(let [{{:keys [class weapon]} :joe} multiplayer-game-state]
  (println "Joe is a" class "wielding a" weapon))


(let [response {:header {:content-type "application/json"} :body {:id 1 :name "dupa"}}
      {{name :name} :body} response]
  name)


;; keyword arguments
(defn configure [val options]
  (let [{:keys [debug verbose] :or {debug false, verbose false}} options]
    (println "val =" val " debug =" debug " verbose =" verbose)))

(configure 12 {:debug true})


;; or better
(defn configure [val & {:keys [debug verbose]
                        :or {debug false, verbose false}}]
  (println "val =" val " debug =" debug " verbose =" verbose))

(configure 10)
;;val = 10  debug = false  verbose = false

(configure 5 :debug true)
;;val = 5  debug = true  verbose = false

;; Note that any order is ok for the kwargs
(configure 12 :verbose true :debug true)
;;val = 12  debug = true  verbose = truennnnn
