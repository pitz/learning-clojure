(ns ecommerce-datomic.model
  (:require [datomic.api :as d]))

(defn novo-produto [nome slug preco]
  {:produto/nome  nome
   :produto/slug  slug
   :produto/preco preco})