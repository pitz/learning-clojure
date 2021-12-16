(ns ecommerce-datomic.helper
  (:use clojure.pprint)
  (:require [ecommerce-datomic.db    :as db]
            [ecommerce-datomic.model :as e.model]
            [datomic.api             :as d]))

(defn save-categoria [conn nome]
  (d/transact conn [(e.model/nova-categoria nome)]))

(defn populate-db [conn]
  (db/cria-schema conn)

  (let [categoria-the-office-id (db/get-id-from-transact (save-categoria conn "The Office"))
        categoria-marvel-id    (db/get-id-from-transact (save-categoria conn "Marvel"))

        product-a  (e.model/novo-produto "Caneca The Office"     "/caneca_the_office"  12.99M ["Caneca" "The Office"]        categoria-the-office-id)
        product-b  (e.model/novo-produto "Caneca Best Boss"     "/caneca_bestboss"   13.99M ["Caneca" "Best Boss"]        categoria-the-office-id)
        product-c  (e.model/novo-produto "Caneca Dundler Miffin" "/caneca_dundler"    14.99M ["Caneca" "Dundler" "Mifflin"] categoria-the-office-id)
        product-d  (e.model/novo-produto "Caneca Dundies"       "/caneca_dundies"    16.99M ["Caneca" "Dundies"]          categoria-the-office-id)
        product-e  (e.model/novo-produto "Caneca M. Scotch"     "/caneca_scoth"      16.99M ["Caneca" "Scotch"]           categoria-the-office-id)
        product-f  (e.model/novo-produto "Caneca Bears Beets"   "/caneca_bearsbeets" 13.99M ["Caneca" "Bears Beets"]      categoria-the-office-id)]
    (d/transact conn [product-a product-b product-c product-d product-e product-f])))

(defn create-new-produto [conn nome slug valor tags categoria-id]
  (let [product (e.model/novo-produto nome slug valor tags categoria-id)]
    (d/transact conn [product])))
;(e.helpers/create-new-produto conn "Caneca do Miranha"         "/miranha"   55.03M ["Caneca"] 17592186045420)
;(e.helpers/create-new-produto conn "Caneca do Doutor Estranho" "/estranhao" 55.03M ["Caneca"] 17592186045420)

