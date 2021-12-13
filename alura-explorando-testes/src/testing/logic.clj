(ns testing.logic)

(defn cabe-na-fila? [hospital departamento]
  (some-> hospital
          departamento
          count
          (< 5)))

(defn chega-em-com-throw [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "Departamento cheio!"
                    {:pessoa pessoa
                     :departamento departamento
                     :tipo :fila-cheia}))))

(defn- tenta-colocar-na-fila [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)))

(defn chega-em! [hospital departamento pessoa]
  (if (nil? (get hospital departamento))
    {:hospital hospital :resultado :erro :descricao-resultado :departamento-nao-encontrado}
    (if-let [novo-hospital (tenta-colocar-na-fila hospital departamento pessoa)]
      {:hospital novo-hospital :resultado :sucesso}
      {:hospital hospital :resultado :erro :descricao-resultado :fila-cheia})))

