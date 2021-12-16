(ns cardlimit.user.userlogic
  (:use clojure.pprint)
  (:require [schema.core        :as s]
            [cardlimit.utils    :as utils]
            [cardlimit.schemata :as c.schenata]))

(s/defn create-user :- c.schenata/User
  [id   :- utils/PosInt,
   name :- s/Str,
   cpf  :- s/Str]
  {:user/id   id
   :user/name name
   :user/cpf  cpf})