(ns clojure-property-based-test.logic
  (:use     [clojure pprint])
  (:require [clojure-property-based-test.model :as c.model]
            [schema.core                       :as s]))

(defn cabe-na-fila?
  [hospital departamento]
  (some-> hospital
          departamento
          count
          (< 5)))

(defn chega-em
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "O departamento está cheio!" {:paciente pessoa}))))

(s/defn atender-paciente :- c.model/Hospital
  [hospital :- c.model/Hospital, departamento :- s/Keyword]
  (update hospital departamento pop))

(s/defn proxima :- c.model/PacienteID
  [hospital :- c.model/Hospital, departamento :- s/Keyword]
  (-> hospital
      departamento
      peek))

(defn mesmo-tamanho? [hospital, outro-hospital, de, para]
  (= (+ (count (get outro-hospital de)) (count (get outro-hospital para)))
     (+ (count (get hospital de))       (count (get hospital para)))))

(s/defn transferir-paciente :- c.model/Hospital
  [hospital             :- c.model/Hospital,
   departamento-origem  :- s/Keyword,
   departamento-destino :- s/Keyword]
  {:pre  [(contains? hospital departamento-origem), (contains? hospital departamento-destino)]
   :post [(mesmo-tamanho? hospital % departamento-origem departamento-destino)]}
  (let [pessoa (proxima hospital departamento-origem)]
    (-> hospital
        (atender-paciente departamento-origem)
        (chega-em departamento-destino pessoa))))