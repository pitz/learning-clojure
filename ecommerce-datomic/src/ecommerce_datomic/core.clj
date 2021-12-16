(ns ecommerce-datomic.core
  (:use clojure.pprint)
  (:require [ecommerce-datomic.db :as db]
            [ecommerce-datomic.model :as e.model]
            [datomic.api :as d]))

(def conn (db/connect-to-db))
(db/cria-schema conn)