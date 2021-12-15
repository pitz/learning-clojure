(ns ecommerce-datomic.intro-datomic
  (:use clojure.pprint)
  (:require [ecommerce-datomic.db :as db]
            [ecommerce-datomic.model :as e.model]
            [datomic.api :as d]))

(def conn (db/connect-to-db))
(db/cria-schema conn)

; Criar um elemento no nosso DB
(let [product-a (e.model/novo-produto "Caneca The Office" "/caneca_the_office" 15.99)
      product-b (e.model/novo-produto "Caneca Best Boss" "/caneca_bestboss"  15.99)]
  (d/transact conn [product-a product-b]))

; Consultar elementos no nosso DB
(def db-readonly (d/db conn))
(d/q '[:find ?entidade
       :where [?entidade :produto/nome]] db-readonly)

; Criar um novo elemento, obtém o TX-ID e atualiza.
(let [product-d        (e.model/novo-produto "Caneca Crazy Dwight" "/caneca_the_office_dwight" 11.99M)
      product-d-result @(d/transact conn [product-d])
      product-d-id     (first (vals (get product-d-result :tempids)))] ; vals pois é um MAP
  (println " [x] product-d-id " product-d-id)
  (println " [x] update: ")
  (pprint @(d/transact conn [[:db/add product-d-id :produto/preco 9.99M]])))
; Interessante a fórmula como é feito um Update:
;    -> ele aplica um softdelete na linha anterior e cria uma nova linha.

; Criar um novo elemento, obtém o TX-ID e remove.
(let [product-d        (e.model/novo-produto "Caneca Crazy Dwight" "/caneca_the_office_dwight" 11.99M)
      product-d-result @(d/transact conn [product-d])
      product-d-id     (first (vals (get product-d-result :tempids)))] ; vals pois é um MAP
  (println " [x] product-d-id " product-d-id)
  (println " [x] remover coluna: ")
  ; (pprint @(d/transact conn [[:db/add product-d-id :produto/preco 9.99M]]))
  (pprint @(d/transact conn [[:db/retract product-d-id :produto/slug "/caneca_the_office_dwight"]])))
; Interessante a fórmula como é feito um Remove:
;    -> cria uma linha para informar que o TX-ID está removido






















