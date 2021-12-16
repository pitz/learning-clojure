(ns cardlimit.core
  (:use clojure.pprint)
  (:require [schema.core                   :as s]
            [cardlimit.model               :as c.model]
            [cardlimit.user.userlogic      :as c.userlogic]
            [cardlimit.score.sr-scorelogic :as c.sr-scorelogic]
            [cardlimit.score.bv-scorelogic :as c.bv-scorelogic]
            [cardlimit.protocols.score     :as c.score]
            [cardlimit.db                  :as db]
            [datomic.api                   :as d]))

; DISCLAIMER (se você está lendo esse código :)):
; várias decisões de design tomadas nesse projeto se justificam apenas com o intuito de manter
; diversas abordagens/jeitos de codar com Clojure. num código real, eu tomaria algumas decisões de forma diferente.

(def conn (db/connect-to-db))
(db/cria-user-schema  conn)
(db/cria-score-schema conn)
;(db/apagar-bd)

(defn add-user  [users user]      (conj users user))
(defn add-user! [users user-info] (alter users add-user (c.userlogic/create-user (get user-info :id) (get user-info :name) (get user-info :cpf))))

(defn process-score-calculator [score-calculator users]
  (dosync
    (doseq [user users]
      (c.score/calculate! score-calculator user))
    (c.score/print-score score-calculator)))

(s/defn create-and-analyse-users-with-atom [user-info-list]
  (let [users (ref [])]
    (dosync
      (doseq [user-info user-info-list]
        (add-user! users user-info))
      (let [score-calculator (c.sr-scorelogic/->SerasaScoreCalculator   (atom []))]
        (process-score-calculator score-calculator users))
      (let [score-calculator (c.bv-scorelogic/->BoaVistaScoreCalculator (atom []))]
        (process-score-calculator score-calculator users)))))

(s/defn create-users [user-info-list]
  (let [users (ref [])]
    (dosync
      (doseq [user-info user-info-list]
        (add-user! users user-info))
      (doseq [user (deref users)]
        (println @(d/transact conn [user]))))))

(s/defn analyse-users []
  (let [users (db/users-query conn)]
    (let [score-calculator (c.sr-scorelogic/->SerasaScoreCalculator   (atom []))]
      (process-score-calculator score-calculator users))
    (let [score-calculator (c.bv-scorelogic/->BoaVistaScoreCalculator (atom []))]
      (process-score-calculator score-calculator users))))

(analyse-users)