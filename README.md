## Learning Clojure
Este repositório conterá alguns projetos que estou criando a fim de evoluir meus conhecimentos com Clojure.
1. [Cálculo de limites para cartões](#card-limit)
2. [Cálculo de limites para cartões (com Datomic)](#cardlimit-wdb)
1. [Explorando testes unitários em Clojure (Alura)](#alura-explorando-testes)

## card-limit:
O projeto card-limit apresenta o meu primeiro contato com o uso de `schema.core` e me permite explorar um pouco mais a utilização de `refs`. 
Neste projeto simulo o input de alguns usuários e calculo (usando um `rand` safado) o Score que este cliente possui a fim de definir qual banda de cartão de crédito pode ser disponibilizado para ele.

## cardlimit-wdb:
Uma variação do projeto cardlimit: agora usamos o Datomic para salvar as informações dos Usuários e seus Scores. Aqui eu também aplico algumas refatorações (e deveria aplicar mais rs).

## alura-explorando-testes:
Aqui apresento um compilado de códigos ref. ao curso **Clojure: Explorando testes** da Alura. Neste projeto, como o próprio nome já diz, exploramos um pouco sobre testes automátizados utilizando o Clojure.
