(ns cardlimit.db
  (:use clojure.pprint)
  (:require [datomic.api :as d]
            [schema.core :as s]
            [cardlimit.schemata :as c.schemata]))

(def db-uri "datomic:dev://localhost:4334/hello")

(defn connect-to-db []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn apagar-bd []
  (d/delete-database db-uri))

(defn cria-user-schema  [conn] (d/transact conn c.schemata/user-schema))
(defn cria-score-schema [conn] (d/transact conn c.schemata/score-schema))

(defn users-query [conn]
  (let [database (d/db conn)]
    (d/q '[:find   ?id ?name ?cpf
           :keys  user/id user/name user/cpf
           :where [?user :user/id   ?id]
                  [?user :user/name ?name]
                  [?user :user/cpf  ?cpf]] database)))