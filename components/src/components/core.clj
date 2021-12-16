(ns components.core
  (:require [com.stuartsierra.component :as component]
            [components.storage         :as c.storage]
            [components.dependency      :as c.dependency]))

; O código desse projeto foi criado por Job Travaini (Lead Software Engineer at Nubank)

(defn run-storage-test []
  (let [le-storage          (c.storage/new-in-memory)
        initialized-storage (component/start le-storage)]

    (println " > put! :edu")
    (c.storage/put! initialized-storage (fn [storage] (assoc storage :edu  "Eduado")))

    (println " > put! :quel")
    (c.storage/put! initialized-storage (fn [storage] (assoc storage :quel "Raquel")))

    (println " > put! :seba")
    (c.storage/put! initialized-storage (fn [storage] (assoc storage :seba "Sebastian")))

    (println " > read-all!")
    (println (c.storage/read-all initialized-storage))

    (println " > clear-all!")
    (println (c.storage/clear-all! initialized-storage))

    (println " > read-all!")
    (println (c.storage/read-all initialized-storage))

    (println " > stop!")
    (println (component/stop initialized-storage))))

; (run-storage-test)

(def started-system (component/start-system c.dependency/system-map))
(c.dependency/add-something (:first started-system) "I have to be print during stop flow")
(def stopped-system (component/stop-system started-system))
(component/stop-system (component/start-system c.dependency/system-map))