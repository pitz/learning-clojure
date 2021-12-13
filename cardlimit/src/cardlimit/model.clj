(ns cardlimit.model
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(defn ge-0? [x] (>= x 0))
(defn valid-band? [band](some #(= band %) [:starter :gold :platinum :upmarket]))

(def PosInt (s/pred pos-int? 'inteiro-positivo))
(def Band   (s/constrained s/Keyword valid-band?))
(def NonNegativeNumber (s/constrained s/Num ge-0?))

(def User
  {:id   PosInt
   :nome s/Str
   :cpf  s/Str})

(def UserScore
  {:user          User
   :band          Band
   :score         PosInt
   :initial-limit NonNegativeNumber})

(def user-info-list [{:id 1 :cpf "08861995906" :name "Eduardo Pitz"}
                     {:id 2 :cpf "01461995906" :name "Reuardo Pitz"}
                     {:id 3 :cpf "01246995906" :name "Rffuado Pitz"}
                     {:id 4 :cpf "07561995906" :name "Vfrardo Pitz"}
                     {:id 5 :cpf "12861995906" :name "Fruardo Pitz"}])

(s/defn parse-band-name :- s/Str
  [band :- s/Keyword]
  (case band
    :starter "Cesta Básica"
    :gold "Gold"
    :platinum "Platinum"
    :upmarket "UV"
    :else (throw "Usuário inválido.")))