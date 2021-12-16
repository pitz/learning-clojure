(ns cardlimit.score.sr-scorelogic
  (:use clojure.pprint)
  (:require [schema.core               :as s]
            [cardlimit.model           :as c.model]
            [cardlimit.parsers         :as c.parser]
            [cardlimit.utils           :as c.utils]
            [cardlimit.protocols.score :as c.score]))

; bem ruim ficar replicando o userscore.
(s/defn get-score-band [score :- s/Int]
  (cond
    (< score 10) (assoc c.model/starter-user-values  :userscore/score score)
    (< score 50) (assoc c.model/gold-user-values     :userscore/score score)
    (< score 90) (assoc c.model/platinum-user-values :userscore/score score)
    :else        (assoc c.model/upmarket-user-values :userscore/score score)))

(defn calculate-score [user]
  (let [index-score (rand-int 100)
        score       (get-score-band index-score)]
    (assoc score :userscore/user-id (get user :id))))

(defrecord SerasaScoreCalculator [score-list] c.score/ScoreCalculator
  (calculate! [this user]
    (let [score (calculate-score user)]
      (swap! score-list conj score)
      (get @score-list user)))
  (print-score [this]
    (println "---------------")
    (println "- S E R A S A -")
    (println "---------------")
    (doseq [score @(get this :score-list)]
      (let [user        (get score :userscore/user)
            user-name   (get user  :user/name)
            user-cpf    (get user  :user/cpf)
            score-index (get score :user/score)
            card-band   (c.parser/parse-band-name (get score :band))
            card-limit  (get score :userscore/initial-limit)
            user-message  (str "[->] Cliente " user-name " (" user-cpf ").")
            score-message (str "     Score: " score-index ".")
            card-message  (str "     Cartão " card-band " com limite R$ " card-limit)]
        (println user-message)
        (println score-message)
        (println card-message)))
    (println "------------------------------------")))