(ns components.storage
  (:require [com.stuartsierra.component :as component]))

; O código desse projeto foi criado por Job Travaini (Lead Software Engineer at Nubank)

(defprotocol StorageClient
  (read-all   [storage]           "Return the entire contents of storage")
  (put!       [storage update-fn] "Mutate the storage with the provided function")
  (clear-all! [storage]           "Clear the storage"))

(defrecord InMemoryStorage [storage]
  component/Lifecycle
  (start [this]
    (assoc this :storage (atom {})))
  (stop [this]
    (assoc this :storage nil))

  StorageClient
  (read-all [_this]
    @storage)
  (put! [_this update-fn]
    ; (println "Using implementation")
    (swap! storage update-fn))
  (clear-all! [_this]
    (reset! storage {})))

(defn new-in-memory []
  (map->InMemoryStorage {}))