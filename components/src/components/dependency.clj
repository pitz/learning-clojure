(ns components.dependency
  (:require [com.stuartsierra.component :as component]))

; O código desse projeto foi criado por Job Travaini (Lead Software Engineer at Nubank)

(defprotocol Something
  (add-something [this something] "Add something")
  (read-something [this] "Read something from something"))

(defrecord FirstComponent [something]
  component/Lifecycle
  (start [this]
    (println "1. Starting the FirstComponent...")
    (assoc this :something (atom nil)))
  (stop [this]
    (println "3. Stopping the FirstComponent...")
    (assoc this :something nil))

  Something
  (add-something [_this new-something] (swap! something (fn [_] new-something)))
  (read-something [_this] @something))

(defrecord SecondComponent [first]
  component/Lifecycle
  (start [this]
    (println "2. Starting the SecondComponent...")
    (println (str "3. Value of our dependency: " (read-something first)))
    this)
  (stop [this]
    (println "1. Stopping the SecondComponent...")
    (println (str "2. Value of our dependency: " (read-something first)))
    (assoc this :first nil)))

(defn new-first-component []
  (map->FirstComponent {}))

(defn new-second-component []
  (map->SecondComponent {}))

(def system-map
  (component/system-map
    :first  (new-first-component)
    :second (component/using (new-second-component) [:first])))
