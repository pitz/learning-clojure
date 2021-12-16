(ns ecommerce-datomic.asof
  (:use clojure.pprint)
  (:require [ecommerce-datomic.db :as db]
            [ecommerce-datomic.model :as e.model]
            [datomic.api :as d]))

(def conn (db/connect-to-db))
(db/cria-schema conn)

;(let [product-a (e.model/novo-produto "Caneca The Office"     "/caneca_the_office"  14.99M)
;      product-b (e.model/novo-produto "Caneca Best Boss"     "/caneca_bestboss"   13.99M)
;      product-c (e.model/novo-produto "Caneca Dundler Miffin" "/caneca_dundler"    16.99M)
;      product-d (e.model/novo-produto "Caneca Dundies"       "/caneca_dundies"    16.99M)
;      product-e (e.model/novo-produto "Caneca M. Scotch"     "/caneca_scoth"      16.99M)
;      product-f (e.model/novo-produto "Caneca Bears Beets"   "/caneca_bearsbeets" 13.99M)]
;  (d/transact conn [product-a product-b product-c product-d product-e product-f]))

(def conn-before (d/db conn))

(println " 1 @ buscando produtos ")
(doseq [product (db/get-all-prices (d/db conn))]
  (println " @ " (get product :produto/nome)))

(let [product (e.model/novo-produto "Caneca Batman Begins xxxx" "/caneca_batman_bxxx" 9.99M)
      result  (d/transact conn [product])]
  (pprint result))

(println " @ buscando produtos - última imagem do BD")
(doseq [product (db/get-all-prices (d/db conn))]
  (println " @ " (get product :produto/nome)))

(println " @ buscando produtos - com imagem de BD definido antes")
(doseq [product (db/get-all-prices conn-before)]
  (println " @ " (get product :produto/nome)))

(println " @ buscando produtos - com as-of de alguns minutos atrás")
(doseq [product (db/get-all-prices (d/as-of (d/db conn) #inst "2021-12-16T16:50:44.962"))]
  (println " @ " (get product :produto/nome)))
