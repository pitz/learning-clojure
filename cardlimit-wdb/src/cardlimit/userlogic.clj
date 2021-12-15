(ns cardlimit.userlogic
  (:use clojure.pprint)
  (:require [schema.core :as s]
            [cardlimit.model :as c.model]
            [cardlimit.scorelogic :as c.scorelogic]))

(s/defn create-user :- c.model/User
  [id   :- c.model/PosInt,
   name :- s/Str,
   cpf  :- s/Str]
  {:id   id
   :name name
   :cpf  cpf})

(s/defn create-user-score :- c.model/UserScore
  [user :- c.model/User]
  (let [values (c.scorelogic/calculate-score)]
    {:user          user
     :band          (get values :band)
     :score         (get values :score)
     :initial-limit (get values :initial-limit)}))