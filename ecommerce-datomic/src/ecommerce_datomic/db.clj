(ns ecommerce-datomic.db
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/hello")

(defn connect-to-db []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn apagar-bd []
  (d/delete-database db-uri))

(def schema [{:db/ident       :produto/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}
             {:db/ident       :produto/nome
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :produto/slug
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :produto/preco
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one}
             {:db/ident       :produto/tag
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/many}
             {:db/ident       :produto/categoria
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/one}

             {:db/ident       :categoria/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}
             {:db/ident       :categoria/nome
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}])

(defn get-id-from-transact [fn-transact]
  (:e (second (:tx-data @fn-transact))))

(defn cria-schema [conn] (d/transact conn schema))

; --------------------------- ;
; QUERIES RETORNANDO ENTIDADE ;
; --------------------------- ;

(defn get-products [db-readonly]
  (d/q '[:find (pull ?entidade [*])
         :where [?entidade :produto/nome]] db-readonly))

(defn get-products-manual [db-readonly]
  (d/q '[:find (pull ?entidade [:produto/nome :produto/preco :produto/slug :produto/tag])
         :where [?entidade :produto/nome]] db-readonly))

(defn get-categorias [db-readonly]
  (d/q '[:find (pull ?entidade [*])
         :where [?entidade :categoria/id]] db-readonly))

; ------------------ ;
; QUERIES ALEATÓRIAS ;
; ------------------ ;

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

; ------------------- ;
; QUERIES COM FILTROS ;
; ------------------- ;

; Retornar produtos filtrando por slug
(defn get-products-by-slug [filters db-readonly]
  (d/q '[:find   ?entidade
         :in    $ ?slug                           ; $ = bd
         :where [?entidade :produto/slug ?slug]]
       db-readonly (get filters :slug)))


; Retornar produtos filtrando por preço mínimo
(defn query-filtering-precos [db ge-preco]
  (d/q '[:find   ?nome ?preco ?tag
         :in    $ ?filter-ge-preco
         :keys  produto/nome produto/preco produto/tag
         :where [?product :produto/preco ?preco]
                [?product :produto/nome  ?nome]
                [?product :produto/tag   ?tag]
                [(>= ?preco ?filter-ge-preco)]] db ge-preco))

; Retornar produtos filtrando por um campo MANY
(defn query-filtering-tag [db tag]
  (d/q '[:find   (pull ?product [*])
         :in    $ ?filter-tag
         :where [?product :produto/preco ?preco]
         [?product :produto/nome  ?nome]
         [?product :produto/tag   ?tag]
         [(>= ?tag ?filter-tag)]] db tag))

; Retornar apenas um produto - filtrando por TX-ID.
(defn get-product-by-tx-id [db id]
  (d/pull db '[*] id))

; Retornar apenas um produto - filtrando por produto/id.
(defn get-product [db id]
  (d/pull db '[*] [:produto/id id]))

; -------------------------------------- ;
; QUERIES COM TABELAS RELACIONADAS (REF) ;
; -------------------------------------- ;

(defn get-products-and-categories [db-readonly]
  (d/q '[:find ?nome ?nome-categoria
         :keys produto/nome categoria/nome
         :where [?produto   :produto/nome      ?nome]
                [?produto   :produto/categoria ?categoria]
                [?categoria :categoria/nome    ?nome-categoria]
         ] db-readonly))

(defn get-products-from-category [db-readonly category-name]
  (d/q '[:find (pull ?produto [*])
         :in  $ ?nome-categoria
         :where [?produto   :produto/nome      ?nome]
                [?produto   :produto/categoria ?categoria]
                [?categoria :categoria/nome    ?nome-categoria]
         ] db-readonly category-name))

(defn get-products-from-category-backward-navigation [db-readonly category-name]
  (d/q '[:find   (pull ?categoria [:categoria/nome
                                   {:produto/_categoria
                                    [:produto/nome :produto/slug]}])
         :in    $ ?nome-categoria
         :where [?categoria :categoria/nome ?nome-categoria]
         ] db-readonly category-name))
