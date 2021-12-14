(ns cardlimit.scorelogic
  (:use clojure.pprint)
  (:require [schema.core :as s]
            [cardlimit.model :as c.model]))

(def starter-user-values  { :band :starter  :initial-limit 50.00    :minimum-score 0  })
(def gold-user-values     { :band :gold     :initial-limit 650.00   :minimum-score 10 })
(def platinum-user-values { :band :platinum :initial-limit 1550.00  :minimum-score 50 })
(def upmarket-user-values { :band :upmarket :initial-limit 12050.50 :minimum-score 90 })

(s/defn get-score-band [score :- s/Int]
  (cond
    (< score 10) (assoc starter-user-values  :score score)
    (< score 50) (assoc gold-user-values     :score score)
    (< score 90) (assoc platinum-user-values :score score)
    :else        (assoc upmarket-user-values :score score)))

(s/defn calculate-score []
  (let [score (int (rand 100))]
    (get-score-band score)))





(defn serasa-calculate-score [user]
  (let [index-score (rand-int 100)
        score       (get-score-band index-score)]
    (assoc score :user user)))

(defprotocol ScoreCalculator
  (calculate!  [this user])
  (print-score [this]))

(defrecord SerasaScoreCalculator [score-list] ScoreCalculator
  (calculate! [this user]
    (let [score (serasa-calculate-score user)]
      (swap! score-list conj score)
      (get @score-list user)))
  (print-score [this]
    (println "---------------")
    (println "- S E R A S A -")
    (println "---------------")
    (doseq [score @(get this :score-list)]
      (let [user        (get score :user)
            user-name   (get user  :name)
            user-cpf    (get user  :cpf)
            score-index (get score :score)
            card-band   (c.model/parse-band-name (get score :band))
            card-limit  (get score :initial-limit)
            user-message  (str "[->] Cliente " user-name " (" user-cpf ").")
            score-message (str "     Score: " score-index ".")
            card-message  (str "     Cartão " card-band " com limite R$ " card-limit)]
        (println user-message)
        (println score-message)
        (println card-message)))
    (println "------------------------------------")))


(s/defn get-score-band-with-boa-vista [score :- s/Num]
  (cond
    (< score 0.1000) (assoc starter-user-values  :score score)
    (< score 0.6000) (assoc gold-user-values     :score score)
    (< score 0.7999) (assoc platinum-user-values :score score)
    :else            (assoc upmarket-user-values :score score)))

(defn calculate-score-with-boa-vista [user]
  (let [index-score (rand 1)
        score       (get-score-band-with-boa-vista index-score)]
    (assoc score :user user)))

(defrecord BoaVistaScoreCalculator [score-list] ScoreCalculator
  (calculate! [this user]
    (let [score (calculate-score-with-boa-vista user)]
      (swap! score-list conj score)
      (get @score-list user)))
  (print-score [this]
    (println "-------------------")
    (println "- B O A V I S T A -")
    (println "-------------------")
    (doseq [score @(get this :score-list)]
      (let [user        (get score :user)
            user-name   (get user  :name)
            user-cpf    (get user  :cpf)
            score-index (get score :score)
            card-band   (c.model/parse-band-name (get score :band))
            card-limit  (get score :initial-limit)
            user-message  (str "[->] Cliente " user-name " (" user-cpf ").")
            score-message (str "     Score: " score-index ".")
            card-message  (str "     Cartão " card-band " com limite " (c.model/formart-brl card-limit))]
        (println user-message)
        (println score-message)
        (println card-message)))
    (println "------------------------------------")))