(ns cardlimit.utils
  (:use clojure.pprint)
  (:require [schema.core :as s]))

; format-methods
(s/defn formart-brl :- s/Str [value :- s/Num] (str "R$ " (format "%.2f" value)))

; herlper-methods
(defn ge-0? [x] (>= x 0))
(defn valid-band? [band](some #(= band %) [:starter :gold :platinum :upmarket]))

; schema-methods
(def PosInt            (s/pred pos-int? 'inteiro-positivo))
(def Band              (s/constrained s/Keyword valid-band?))
(def NonNegativeNumber (s/constrained s/Num ge-0?))