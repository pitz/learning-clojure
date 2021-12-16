(ns ecommerce-datomic.core
  (:use clojure.pprint)
  (:require [ecommerce-datomic.db     :as db]
            [ecommerce-datomic.helper :as e.helpers]
            [datomic.api              :as d]))

; Apagar DB:
;(db/apagar-bd)

(def conn (db/connect-to-db))
;(e.helpers/populate-db conn)

(println " @ TODOS PRODUTOS ")
(pprint (db/get-products (d/db conn)))

;(println " @ TODAS CATEGORIAS ")
;(pprint (db/get-categorias (d/db conn)))
;(println " @ BY-ID ")
;(pprint (db/get-product  (d/db conn) #uuid"9fd369c8-b516-4c43-acf8-a35d39d96409"))