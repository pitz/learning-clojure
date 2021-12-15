(ns ecommerce-datomic.queries-datomic
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

;(println " get-products         @ :produto/nome ")
;(pprint (db/get-products {:ident :produto/nome} (d/db conn)))
;
;(println " get-products-by-slug @ :produto/slug ")
;(pprint (db/get-products-by-slug {:slug "/caneca_the_office"} (d/db conn)))
;
;(println " get-all-slugs        @ :produto/slug ")
;(pprint (db/get-all-slugs {} (d/db conn)))

;(println " get-all-prices       @ :produto/slug ")
;(doseq [product (db/get-all-prices {} (d/db conn))]
;  (println "                      @ " (first product) "com valor" (last product)))

;(println "get-all-prices       @ :produto/slug ")
;(doseq [product (db/get-all-prices {} (d/db conn))]
;  (println "                     @ " (get product :produto/nome) "com valor de R$" (get product :produto/preco)))
;
;(println "get-all-products       @")
;(doseq [product (db/get-all-prices {} (d/db conn))]
;  (println "                     @ " (get product :produto/nome) "com valor de R$" (get product :produto/preco)))

;(println "get-all-get-products-returning-entity @ :produto/slug ")
;(doseq [product (db/get-products-returning-entity (d/db conn))]
;  (println " @ " product)
;  ;(println " @ " (get product :produto/nome) "com valor de R$" (get product :produto/preco))
;  )

;(println "get-all-get-products-returning-entity @ :produto/slug ")
;(doseq [product (db/get-products-returning-entity-generic (d/db conn))]
;  (println " @ " product)
;  ;(println " @ " (get product :produto/nome) "com valor de R$" (get product :produto/preco))
;  )


;(db/apagar-bd)





