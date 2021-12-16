(ns cardlimit.protocols.score)

(defprotocol ScoreCalculator
  "Calculdor de Score de usuários por meio de consultas externas"

  ; Calcula Score de usuários em batch
  (calculate!  [this user])

  ; Exibe relatório de scores calculados
  (print-score [this]))