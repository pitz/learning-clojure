(ns cardlimit.score.bv-scorelogic
  (:use clojure.pprint)
  (:require [schema.core               :as s]
            [cardlimit.model           :as c.model]
            [cardlimit.parsers         :as c.parser]
            [cardlimit.utils           :as c.utils]
            [cardlimit.protocols.score :as c.score]))

(s/defn get-score-band [score :- s/Num]
  (cond
    (< score 0.1000) (assoc c.model/starter-user-values  :userscore/score score)
    (< score 0.6000) (assoc c.model/gold-user-values     :userscore/score score)
    (< score 0.7999) (assoc c.model/platinum-user-values :userscore/score score)
    :else            (assoc c.model/upmarket-user-values :userscore/score score)))

(defn calculate-score [user]
  (let [index-score (rand 1)
        score       (get-score-band index-score)]
    (assoc score :userscore/user (get user :id))))

(defrecord BoaVistaScoreCalculator [score-list] c.score/ScoreCalculator
  (calculate! [this user]
    (let [score (calculate-score user)]
      (swap! score-list conj score)
      (get @score-list user)))
  (print-score [this]
    (println "-------------------")
    (println "- B O A V I S T A -")
    (println "-------------------")
    (doseq [score @(get this :score-list)]
      (let [user        (get score :userscore/user)
            user-name   (get user  :user/name)
            user-cpf    (get user  :user/cpf)
            score-index (get score :user/score)
            card-band   (c.parser/parse-band-name (get score :band))
            card-limit  (get score :userscore/initial-limit)
            user-message  (str "[->] Cliente " user-name " (" user-cpf ").")
            score-message (str "     Score: " score-index ".")
            card-message  (str "     Cartão " card-band " com limite " (c.utils/formart-brl card-limit))]
        (println user-message)
        (println score-message)
        (println card-message)))
    (println "------------------------------------")))