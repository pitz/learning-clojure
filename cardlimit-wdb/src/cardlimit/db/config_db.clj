(ns cardlimit.db.config-db
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