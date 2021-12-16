(ns ecommerce-datomic.core
  (:use clojure.pprint)
  (:require [ecommerce-datomic.db :as db]
            [ecommerce-datomic.model :as e.model]
            [datomic.api :as d]))

(def conn (db/connect-to-db))
(db/cria-schema conn)

(let [product-a (e.model/novo-produto "Caneca The Office"     "/caneca_the_office"  14.99M)
      product-b (e.model/novo-produto "Caneca Best Boss"     "/caneca_bestboss"   13.99M)
      product-c (e.model/novo-produto "Caneca Dundler Miffin" "/caneca_dundler"    16.99M)
      product-d (e.model/novo-produto "Caneca Dundies"       "/caneca_dundies"    16.99M)
      product-e (e.model/novo-produto "Caneca M. Scotch"     "/caneca_scoth"      16.99M)
      product-f (e.model/novo-produto "Caneca Bears Beets"   "/caneca_bearsbeets" 13.99M)]
  (d/transact conn [product-a product-b product-c product-d product-e product-f]))

(println "get-all-get-products-returning-entity @ :produto/slug ")
(doseq [product (db/get-products-returning-entity (d/db conn))]
  (println " @ " (get product :produto/nome)))

(println "get-all-get-products-returning-entity @ :produto/slug ")
(doseq [product (db/get-all-prices (d/db conn))]
  (println " @ " (get product :produto/nome)))


;; antes
;(pprint (count (db/get-all-products (db/as-of) #inst "2019-09-18T17:34:34.200")))`
;
;; no meio
;(pprint (count (db/get-all-products (db/as-of) #inst "2019-09-18T17:35:34.200")))
;
;; depois
;(pprint (count (db/get-all-products (db/as-of) #inst "2019-09-18T17:36:34.200")))