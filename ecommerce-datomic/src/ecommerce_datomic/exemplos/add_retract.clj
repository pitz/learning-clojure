(ns ecommerce-datomic.exemplos.add-retract
  (:use clojure.pprint)
  (:require [ecommerce-datomic.db    :as db]
            [ecommerce-datomic.model :as e.model]
            [datomic.api             :as d]))

(def conn (db/connect-to-db))
(db/cria-schema conn)

(println " Adicionando valores no campo :produto/tag")
(pprint (db/get-products (d/db conn)))
(d/transact conn [[:db/add 17592186045423 :produto/tag "Dwaight"]])
(d/transact conn [[:db/add 17592186045423 :produto/tag "Batestar Galatica"]])

(println " Removendo valores do campo :produto/tag")
(pprint (db/get-products (d/db conn)))
(d/transact conn [[:db/retract 17592186045419 :produto/tag "Caneca"]])
(d/transact conn [[:db/retract 17592186045420 :produto/tag "Caneca"]])
(d/transact conn [[:db/retract 17592186045421 :produto/tag "Caneca"]])