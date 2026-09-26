# Enunciado

Haverá no seu programa, 2 tipos de Threads:

- Threads ordenadoras
- Threads misturadoras (mergeadoras)

## Entrada de dados

Seu programa deve perguntar ao usuário:

- Quantos elementos deseja ter no vetor;
- Se quer digitá-los ou se quer que sejam gerados aleatoriamente.

E o seu programa atende o desejo do usuário.

## Divisão do vetor e Threads ordenadoras

Divida agora o vetor em **N pedaços**, sendo **N a quantidade de processadores - 1**.

Agora, **N Threads ordenadoras** deverão ser instanciadas, e seus construtores deverão ter recebido **N pedaços**.

Essas Threads, que são ordenadoras, ordenarão o pedaço que lhes coube de forma tradicional, recursivamente.

A `main` deverá então esperar as Threads terminarem, usando o método `JOIN`, e então usará um método das Threads para recuperar o vetor ordenado por cada um.

## Threads misturadoras (mergeadoras)

Agora a `main` deverá fazer várias rodadas de `start` de Threads misturadoras:

- **N/2 Threads misturadoras** na primeira rodada;
- **N/4 Threads misturadoras** na segunda rodada;
- **N/8 Threads misturadoras** na terceira rodada;
- ...
- **1 Thread misturadora** na última rodada.

Também precisará lidar com vetor com o número de elementos ímpar.

A `main` deve esperar uma rodada terminar para começar outra, usando os resultados da rodada anterior.

## Menu final

Para finalizar, a `main` deve oferecer ao usuário um menu com as opções de:

1. Printar o vetor ordenado inteiro;
2. Printar um pedaço do vetor ordenado, de um início a um fim digitado;
3. Printar o tempo tomado pela ordenação.

## Tempo das Threads ordenadoras

As Threads ordenadoras devem printar no final o tempo que levaram pra ordenar.