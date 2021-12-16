(ns ecommerce-datomic.exemplos.queries
  (:use clojure.pprint)
  (:require [ecommerce-datomic.db    :as db]
            [ecommerce-datomic.model :as e.model]
            [datomic.api             :as d]))

(def conn (db/connect-to-db))
(db/cria-schema conn)

(println " Buscar todos os produtos ")
(pprint (db/get-products (d/db conn)))

(println " Buscar por ID ")
(pprint (db/get-product  (d/db conn) #uuid"9fd369c8-b516-4c43-acf8-a35d39d96409"))

(println " Buscar com filtro por preço (>= 16.99)")
(doseq [product (db/query-filtering-precos (d/db conn) 16.99)]
  (println " @ " (get product :produto/nome) "-" (get product :produto/tag)))

(println " Buscar com filtro por TAG")
(pprint (db/query-filtering-precos (d/db conn) "Caneca"))

(println " Buscar filtrando com as-of (filtra determinado período do BD)")
(doseq [product (db/get-products (d/as-of (d/db conn) #inst "2021-12-16T16:50:44.962"))]
  (println " @ " (get product :produto/nome)))

(println " Buscar propriedades de tabela relacionada (ref)")
(println (db/get-products-and-categories (d/db conn)))

(println " Buscar com filtro em tabela relacionada (ref)")
(println (db/get-products-from-category (d/db conn) "Marvel"))

(println " Buscar com filtro em tabela relacionada (ref) com get-products-from-category-backward-navigation")
(println (db/get-products-from-category-backward-navigation (d/db conn) "Marvel"))
