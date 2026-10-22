# Ordenação de vetores com Merge Sort

O programa ordena valores `byte` (de -128 a 127) com Merge Sort, em versões paralela e sequencial. É possível informar os valores manualmente ou gerá-los aleatoriamente.

## Compilar e executar

Na raiz do projeto, com JDK instalado:

```powershell
New-Item -ItemType Directory -Force build
javac -encoding UTF-8 -d build Main.java MainSequencial.java ThreadOrdenadora.java ThreadMergeadora.java
java -cp build Main
```

Para executar a versão sequencial, use `java -cp build MainSequencial`. O menu permite imprimir o vetor ordenado, exibir um intervalo e consultar o tempo. `Main` também compara as duas versões com a mesma entrada; `MainSequencial` ordena sem threads. Valores `byte` vão de -128 a 127, enquanto a quantidade de elementos é informada separadamente.

Os exemplos em `documentacoes/` são materiais independentes das aulas. Compile apenas os quatro arquivos da raiz indicados acima.

Responsabilidades: Matheus — thread mergeadora; Marcos — thread ordenadora; Thiago — `Main`.

Diário, relato dos testes, registros e capturas: [DIARIO.md](entrega/DIARIO.md), [RELATO_TESTES.md](entrega/RELATO_TESTES.md), `entrega/TESTES.txt` e `entrega/capturas/`.

A criação das threads e a espera com join() seguem os exemplos 1 e 3. A consulta aos processadores segue o exemplo 8. Cada ordenadora trabalha em seu próprio trecho do vetor.
