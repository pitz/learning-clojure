(ns cardlimit.scorelogic
  (:use clojure.pprint)
  (:require [schema.core :as s]
            [cardlimit.model :as c.model]))

(def starter-user-values
  {:band :starter
   :initial-limit 50.00})

(def gold-user-values
  {:band :gold
   :initial-limit 650.00})

(def platinum-user-values
  {:band :platinum
   :initial-limit 1550.00})

(def upmarket-user-values
  {:band :upmarket
   :initial-limit 12050.50})

(s/defn calculate-score []
  (let [score (int (rand 100))]
    (cond
      (< score 10) (assoc starter-user-values :score score)
      (< score 50) (assoc gold-user-values :score score)
      (< score 90) (assoc platinum-user-values :score score)
      :else (assoc upmarket-user-values :score score))))