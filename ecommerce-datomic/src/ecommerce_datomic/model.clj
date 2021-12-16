(ns ecommerce-datomic.model
  (:require [datomic.api :as d])
  (:import (java.util UUID)))

(defn novo-produto [nome slug preco tag categoria]
  {:produto/id        (UUID/randomUUID)
   :produto/nome      nome
   :produto/slug      slug
   :produto/preco     preco
   :produto/tag       tag
   :produto/categoria categoria})

(defn nova-categoria [nome]
  {:categoria/id   (UUID/randomUUID)
   :categoria/nome nome})
