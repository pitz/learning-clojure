(ns testing.logic)

(defn cabe-na-fila? [hospital departamento]
  (-> hospital
      departamento
      count
      (< 5)))