(ns defprotocoltraining.logic
  (:require [defprotocoltraining.model :as d.model]))

(defn date-now []
  (d.model/to-ms (java.util.Date.)))