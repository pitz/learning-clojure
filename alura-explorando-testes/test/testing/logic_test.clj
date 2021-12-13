(ns testing.logic-test
  (:require [clojure.test :refer :all])
  (:require [testing.logic :refer :all]))

(deftest cabe-na-fila?-test
  (testing "cabe-na-fila? deve retornar true para fila vazia"
    (is (cabe-na-fila? {:espera []} :espera)))

  (testing "cabe-na-fila? deve retornar true para fila com menos de 4 elementos"
    (is (cabe-na-fila? {:espera [1 2 3 4]} :espera)))

  (testing "cabe-na-fila? deve retornar false para fila com 5 elementos"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  (testing "cabe-na-fila? deve retornar false para fila com mais de 5 elementos"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]} :espera)))))