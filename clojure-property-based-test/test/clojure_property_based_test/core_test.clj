(ns clojure-property-based-test.core-test
  (:use clojure.pprint)
  (:require [clojure.test                      :refer :all]
            [clojure-property-based-test.logic :refer :all]
            [clojure-property-based-test.model :as c.model]
            [clojure.test.check.generators     :as gen]
            [schema.core                       :as s]))

(s/set-fn-validation! true)

(deftest cabe-na-fila?-test

  (testing "cabe-na-fila? retornando true com fila vazia"
    (is (cabe-na-fila? {:espera []}, :espera)))

  (testing "cabe-na-fila? retornando true com fila que possui elementos"
    (doseq [fila (gen/sample (gen/vector gen/int 0 4), 100)]
      (is (cabe-na-fila? {:espera fila}, :espera))))

  (testing "cabe-na-fila? retornando false com fila cheia"
    (doseq [fila (gen/sample (gen/vector gen/int 5), 100)]
      (is (not (cabe-na-fila? {:espera fila}, :espera)))))

  (testing "cabe-na-fila? retornando false com fila inválida (com mais elementos)"
    (doseq [fila (gen/sample (gen/vector gen/int 5 100), 100)]
      (is (cabe-na-fila? {:espera fila}, :espera))))

  (testing "cabe-na-fila? retornando false se departamento não existe"
    (is (not (cabe-na-fila? {:espera [1 2 3 4]}, :raio-x)))))