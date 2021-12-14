(ns ecommerce-datomic.core
  (:require [ecommerce-datomic.db :as db]
            [ecommerce-datomic.model :as e.model]
            [datomic.api :as d]))

(def conn (db/connect-to-db))
(db/cria-schema conn)

(let [product-a (e.model/novo-produto "Caneca The Office" "/caneca_the_office" 15.99)
      product-b (e.model/novo-produto "Caneca Best Boss" "/caneca_bestboss"  15.99)]
  (d/transact conn [product-a product-b]))

(def db-readonly (d/db conn))
(d/q '[:find ?entidade
       :where [?entidade :produto/nome]] db-readonly)
