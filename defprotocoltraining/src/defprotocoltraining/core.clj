(ns defprotocoltraining.core
  (:use clojure.pprint)
  (:require [schema.core :as s]
            [defprotocoltraining.model :as d.model]
            [defprotocoltraining.logic :as d.logic]))

(defn create-score [id]
  (println " @-novo-score " id)
  {:id id,
   :carregado-em (d.logic/date-now)})

(defn calculate-serasa-score
  [cache id carregadora]
  (if (contains? cache id)
    cache
    (let [paciente (carregadora id)]
      (assoc cache id paciente))))

(defprotocol Score
  (calculate! [this id])
  (print-score [this]))

(defrecord SerasaScore [score-list score-factory] Score
  (calculate! [this id]
    (swap! score-list calculate-serasa-score id score-factory)
    (get @score-list id))
  (print-score [this]
    (println " @-print-score: " this)))

(def pacientes (->SerasaScore (atom {}) create-score))

(println " # pacientes {ini} " pacientes)
(calculate!  pacientes 1)
(calculate!  pacientes 2)
(calculate!  pacientes 3)
(calculate!  pacientes 4)
(print-score pacientes)