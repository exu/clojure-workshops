(ns http.http
  (:require
   [clj-http.client :as client]
   [clojure.pprint :refer [pprint]]
   ))

(def uri "https://api.stackexchange.com/2.2/answers?order=desc&sort=activity&site=stackoverflow")
(def response (client/get uri {:as :json}))

(-> response
     :body
     :items
     first
     :owner
     :display_name
     )
