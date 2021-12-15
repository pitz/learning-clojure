(ns cardlimit.core
  (:use clojure.pprint)
  (:require [schema.core          :as s]
            [cardlimit.model      :as c.model]
            [cardlimit.userlogic  :as c.userlogic]
            [cardlimit.scorelogic :as c.scorelogic]))

(defn add-user  [users user]      (conj users user))
(defn add-user! [users user-info] (alter users add-user (c.userlogic/create-user (get user-info :id) (get user-info :name) (get user-info :cpf))))

(defn add-analyse  [analyses analyse] (conj analyses analyse))
(defn add-analyse! [analyses user]    (alter analyses add-analyse (c.userlogic/create-user-score user)))

(defn add-analyse-x  [analyses analyse] (conj analyses analyse))
(defn add-analyse-x! [analyses user]    (alter analyses add-analyse (c.userlogic/create-user-score user)))

(s/defn report
  [scores :- [c.model/UserScore]]
  (println "Usuários cadastrados com sucesso!")
  (doseq [user-score scores]
    (let [user (get user-score :user)
          user-name (get user :name)
          user-cpf (get user :cpf)
          user-info (str user-name "(" user-cpf ")")
          band-name (c.model/parse-band-name (get user-score :band))
          report-message (str "Usuário " user-info " cadastrado com sucesso! Cartão " band-name ".")]
      (println report-message))))

(s/defn analyse-users
  [user-info-list]
  (let [users (ref [])]
    (dosync
      (doseq [user-info user-info-list]
        (add-user! users user-info))
      (let [scores (ref [])]
        (dosync
          (doseq [user (deref users)] (add-analyse! scores user))
          (report (deref scores)))))))
; (analyse-users c.model/user-info-list)

(s/defn analyse-users-x
  [user-info-list]
  (let [users (ref [])]
    (dosync
      (doseq [user-info user-info-list]
        (add-user! users user-info))
      (let [score-calculator (c.scorelogic/->SerasaScoreCalculator (atom []))]
        (dosync
          (doseq [user (deref users)]
            (c.scorelogic/calculate! score-calculator user))
          (c.scorelogic/print-score score-calculator)))
      (let [score-calculator (c.scorelogic/->BoaVistaScoreCalculator (atom []))]
        (dosync
          (doseq [user (deref users)]
            (c.scorelogic/calculate! score-calculator user))
          (c.scorelogic/print-score score-calculator))))))

(analyse-users-x c.model/user-info-list)
