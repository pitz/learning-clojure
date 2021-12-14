(ns clojure-property-based-test.model
  (:require [schema.core :as s]))

(def empty-queue clojure.lang.PersistentQueue/EMPTY)

(defn novo-hospital []
  {:espera empty-queue
   :lab-01 empty-queue
   :lab-02 empty-queue
   :lab-03 empty-queue})

(defn novo-departamento []
  empty-queue)

(s/def PacienteID   s/Str)
(s/def Departamento (s/queue PacienteID))
(s/def Hospital     {s/Keyword Departamento})