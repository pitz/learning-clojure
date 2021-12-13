(ns testing.logic-test
  (:require [clojure.test :refer :all])
  (:require [testing.logic :refer :all]))

(deftest cabe-na-fila?-test
  (testing "cabe-na-fila? deve retornar true para fila vazia"
    (is (cabe-na-fila? {:espera []} :espera)))

  (testing "cabe-na-fila? deve retornar false para fila nil"
    (is (not (cabe-na-fila? {} :espera))))

  (testing "cabe-na-fila? deve retornar true para fila com menos de 4 elementos"
    (is (cabe-na-fila? {:espera [1]} :espera))
    (is (cabe-na-fila? {:espera [1 2]} :espera))
    (is (cabe-na-fila? {:espera [1 2 3]} :espera))
    (is (cabe-na-fila? {:espera [1 2 3 4]} :espera)))

  (testing "cabe-na-fila? deve retornar false para fila com 5 elementos"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  (testing "cabe-na-fila? deve retornar false para fila com mais de 5 elementos"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]} :espera)))))

(deftest chega-em-com-throw-test
  (testing "chega-em-test? adiciona primeiro elemento na fila"
    (is (= {:espera [1]}
           (chega-em-com-throw {:espera []} :espera 1))))

  (testing "chega-em-test? adiciona elemento na fila"
    (is (= {:espera [2 245 43 4 5]}
           (chega-em-com-throw {:espera [2 245 43 4]} :espera 5))))

  (testing "chega-em-test? não adiciona elemento em fila cheia"
    (is (try
          (chega-em-com-throw {:espera [1 2 3 4 5]} :espera 6)
          false
          (catch Exception e
            (= :fila-cheia (get (ex-data e) :tipo)))))))

(deftest tenta-colocar-na-fila-test
  (testing "chega-em! adiciona primeiro elemento na fila"
    (is (= {:hospital {:espera [1]} :resultado :sucesso}
           (chega-em! {:espera []} :espera 1))))

  (testing "chega-em! adiciona elemento na fila"
    (is (= {:hospital {:espera [234 2 44 124 1]} :resultado :sucesso}
           (chega-em! {:espera [234 2 44 124]} :espera 1))))

  (testing "chega-em-test? não adiciona elemento em fila cheia"
    (is (= {:hospital {:espera [234 2 44 124 2]} :resultado :erro :descricao-resultado :fila-cheia}
           (chega-em! {:espera [234 2 44 124 2]} :espera 1))))

  (testing "chega-em-test? não adiciona elemento em departamento inexistente"
    (is (= {:hospital {:espera [234 2 44 124 2]} :resultado :erro :descricao-resultado :departamento-nao-encontrado}
           (chega-em! {:espera [234 2 44 124 2]} :xespera 1)))))