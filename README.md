## Learning Clojure
Este repositório conterá alguns projetos que estou criando a fim de evoluir meus conhecimentos com Clojure.
1. [card-limit: Cálculo de limites para cartões](#card-limit)
2. [cardlimit-wdb: Cálculo de limites para cartões (com Datomic)](#cardlimit-wdb)
3. [alura-explorando-testes: Explorando testes unitários em Clojure (Alura)](#alura-explorando-testes)
4. [clojure-property-based-test: Explorando testes de propriedade em Clojure (Alura)](#clojure-property-based-test)
5. [defprotocoltraining: Bricando um pouco com defprotocol e  defrecord](#clojure-property-based-test)
6. [components: Aplicando o uso do framework Component](#components)
7. [ecommerce-datomic: Primeiros passos com o Datomic](#ecommerce-datomic)

## card-limit:
O projeto card-limit apresenta o meu primeiro contato com o uso de `schema.core` e me permite explorar um pouco mais a utilização de `refs`. 
Neste projeto simulo o input de alguns usuários e calculo (usando um `rand` safado) o Score que este cliente possui a fim de definir qual banda de cartão de crédito pode ser disponibilizado para ele.

## cardlimit-wdb:
Uma variação do projeto cardlimit: agora usamos o Datomic para salvar as informações dos Usuários e seus Scores. Aqui eu também aplico algumas refatorações (e deveria aplicar mais rs).

Conforme evolui no projeto cardlimit-wdb vi que podemos melhorar alguns códigos deste projeto.

## alura-explorando-testes:
Aqui apresento um compilado de códigos ref. ao curso **Clojure: Explorando testes** da Alura. Neste projeto, como o próprio nome já diz, exploramos um pouco sobre testes automátizados utilizando o Clojure.

## clojure-property-based-test
Aqui apresento um compilado de testes. O diferencial entre este projeto e o projeto alura-explorando-testes é que aqui vemos a utilização de uma biblioteca para geração automática de dados.

## defprotocoltraining
Um projeto bem simples aplicando alguns conceitos de defprotocol e defrecord.

## components
Projeto aplicando alguns conceitos do framework Component (https://github.com/stuartsierra/component). O código apresentado neste projeto foi obtido através de um treinamento feito por Job Travaini (Lead Software Engineer at Nubank). Aqui eu apenas aplico algumas pequenas mudanças e reflito sobre como tudo funciona :).

## ecommerce-datomic
Este projeto é meu primeiro contato com Clojure (ele é um projeto que foi iniciado antes do cardlimit-wdb). Aqui eu aplico todos os conhecimentos obtidos durante os cursos de Datomic da Alura. 





