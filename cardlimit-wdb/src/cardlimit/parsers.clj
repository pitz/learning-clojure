(ns cardlimit.parsers
  (:use clojure.pprint)
  (:require [schema.core :as s]))

; parse-band-name com demulti/defmethod
(defmulti  parse-band-name identity)
(defmethod parse-band-name :starter  [n] "Cesta Básica")
(defmethod parse-band-name :gold     [n] "Gold")
(defmethod parse-band-name :platinum [n] "Platinum")
(defmethod parse-band-name :upmarket [n] "UV")
(defmethod parse-band-name :default  [n] "Cesta de benefícios é inválida")

; parse-band-name-with-case com case
;(s/defn parse-band-name-with-case :- s/Str
;  [band :- s/Keyword]
;  (case band
;    :starter "Cesta Básica"
;    :gold "Gold"
;    :platinum "Platinum"
;    :upmarket "UV"
;    :else (throw "Usuário inválido.")))

; parse-band-name-with-cond com cond.
;(s/defn parse-band-name-with-cond :- s/Str
;  [band :- s/Keyword]
;  (cond
;    (= band :starter) "Cesta Básica"
;    (= band :gold) "Gold"
;    (= band :platinum "Platinum")
;    (= band :upmarket) "UV"
;    :else (throw "Usuário inválido.")))