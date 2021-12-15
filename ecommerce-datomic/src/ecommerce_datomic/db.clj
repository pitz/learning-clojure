(ns ecommerce-datomic.db
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/hello")

(defn connect-to-db []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn apagar-bd []
  (d/delete-database db-uri))

(def schema [{:db/ident       :produto/nome
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :produto/slug
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :produto/preco
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one}])

(defn cria-schema [conn] (d/transact conn schema))

(defn get-products
  [filters db-readonly]

  (println filters)
  (println (get filters :ident))

  (d/q '[:find ?entidade
         :where [?entidade :produto/nome]] db-readonly))

(defn get-products-by-slug [filters db-readonly]
  (println " .... " (get filters :slug))

  (d/q '[:find   ?entidade
         :in    $ ?slug                           ; $ = bd
         :where [?entidade :produto/slug ?slug]]
       db-readonly (get filters :slug)))

(defn get-all-slugs [filters db]
  (d/q '[:find ?slug
         :where [_ :produto/slug ?slug]] db))

(defn get-all-slugs [filters db]
  (println " get-all-slugs @ filters " filters)

  (d/q '[:find ?slug
         :where [_ :produto/slug ?slug]] db))

(defn get-all-prices [db]
  (d/q '[:find   ?nome ?preco
         :keys  produto/nome produto/preco
         :where [?product :produto/preco ?preco]
                [?product :produto/nome  ?nome]] db))

(defn get-products-returning-entity [db-readonly]
  (d/q '[:find (pull ?entidade [:produto/nome :produto/preco :produto/slug])
         :where [?entidade :produto/nome]] db-readonly))

(defn get-products-returning-entity-generic [db-readonly]
  (d/q '[:find (pull ?entidade [*])
         :where [?entidade :produto/nome]] db-readonly))

