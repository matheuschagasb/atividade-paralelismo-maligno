# Enunciado

Ordenar um vetor grande de valores `byte` com Merge Sort recursivo, em uma versão paralela e outra sequencial. Em Java, `byte` representa valores de -128 a 127; a quantidade de elementos é informada separadamente.

## Entrada e menu

O programa pergunta a quantidade de elementos e permite digitá-los ou gerá-los aleatoriamente. Depois da ordenação, deve permitir imprimir o vetor completo, imprimir um intervalo e consultar o tempo. A entrada deve ser validada e as exceções tratadas.

## Versão paralela

- Criar `P - 1` threads ordenadoras, sendo `P` a quantidade de processadores disponíveis; permitir também execução com um processador.
- Dividir o vetor em partes aproximadamente iguais e ordenar cada parte recursivamente.
- Aguardar as ordenadoras com `join`.
- Combinar os resultados em rodadas de threads mergeadoras, uma por par. Aguardar cada rodada antes da seguinte e tratar partes restantes quando houver quantidade ímpar.
- Registrar logs da execução.

## Versão sequencial e comparação

Disponibilizar a ordenação sequencial sem threads e registrar seus logs. A versão paralela deve permitir comparar os tempos das duas versões usando a mesma entrada.

## Entrega

Entregar os fontes, capturas dos programas em execução, relato dos testes com até 10 linhas e diário cronológico com timestamps, atividades, impressões e conclusão. A orientação recebida menciona entrega no Canvas e demonstração em aula a partir de 06/outubro; confirmar o ano e o cronograma com o docente.
