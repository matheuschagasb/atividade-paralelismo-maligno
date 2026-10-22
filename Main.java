import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main
{
    public static void main (String[] args)
    {
        executar (true);
    }

    public static void executar (boolean paralela)
    {
        try (Scanner teclado = new Scanner (System.in))
        {
            executarComTeclado (teclado, paralela);
        }
        catch (OutOfMemoryError erro)
        {
            System.out.println ("Memoria insuficiente para concluir a operacao. Encerrando.");
        }
        catch (InterruptedException erro)
        {
            Thread.currentThread ().interrupt ();
            System.out.println ("Operacao interrompida. Encerrando.");
        }
        catch (RuntimeException erro)
        {
            System.out.println ("Falha durante a ordenacao: " + erro.getMessage ()
                    + ". Encerrando.");
        }
    }

    private static void executarComTeclado (Scanner teclado, boolean paralela)
            throws InterruptedException
    {
        int processadores = Runtime.getRuntime ().availableProcessors ();
        System.out.println ("Modo: " + (paralela ? "paralelo" : "sequencial"));
        System.out.println ("Processadores disponiveis: " + processadores);

        Integer quantidadeLida = lerInteiro (teclado,
                "Quantos elementos deseja ter no vetor? ", 1, Integer.MAX_VALUE);
        if (quantidadeLida == null)
        {
            System.out.println ("Entrada encerrada. Programa finalizado.");
            return;
        }

        Integer modo = lerInteiro (teclado,
                "Digite 1 para informar os elementos ou 2 para gera-los aleatoriamente: ", 1, 2);
        if (modo == null)
        {
            System.out.println ("Entrada encerrada. Programa finalizado.");
            return;
        }

        byte[] entrada = new byte[quantidadeLida];
        if (modo == 1)
        {
            for (int i = 0; i < entrada.length; i++)
            {
                Integer valor = lerInteiro (teclado, "Elemento " + i + " (-128 a 127): ",
                                            Byte.MIN_VALUE, Byte.MAX_VALUE);
                if (valor == null)
                {
                    System.out.println ("Entrada encerrada. Programa finalizado.");
                    return;
                }
                entrada[i] = (byte) valor.intValue ();
            }
        }
        else
        {
            Random aleatorio = new Random ();
            for (int i = 0; i < entrada.length; i++)
                entrada[i] = (byte) (aleatorio.nextInt (256) - 128);
        }

        System.out.println ("Tamanho do vetor: " + entrada.length);
        System.out.println ("Modo de entrada: " + (modo == 1 ? "manual" : "aleatorio"));
        System.out.println ("Iniciando ordenacao " + (paralela ? "paralela" : "sequencial") + "...");
        long inicio = System.nanoTime ();
        byte[] ordenado = paralela
                ? ordenarParalelo (entrada, true)
                : MainSequencial.ordenar (entrada, true);
        long tempoOrdenacaoNs = System.nanoTime () - inicio;
        System.out.printf ("Ordenacao concluida em %.3f ms.%n", tempoOrdenacaoNs / 1_000_000.0);

        menuFinal (teclado, entrada, ordenado, tempoOrdenacaoNs, paralela);
    }

    public static byte[] ordenarParalelo (byte[] entrada, boolean mostrarMensagens)
            throws InterruptedException
    {
        if (entrada == null)
            throw new IllegalArgumentException ("Vetor de entrada ausente");

        long inicioTotal = System.nanoTime ();
        if (entrada.length == 0)
        {
            if (mostrarMensagens)
                System.out.printf ("Ordenacao paralela de 0 elementos em %.3f ms%n",
                                   (System.nanoTime () - inicioTotal) / 1_000_000.0);
            return entrada.clone ();
        }

        int processadores = Runtime.getRuntime ().availableProcessors ();
        int quantidadeDeOrdenadoras = processadores - 1;
        if (quantidadeDeOrdenadoras < 1)
        {
            quantidadeDeOrdenadoras = 1;
            if (mostrarMensagens)
                System.out.println ("Apenas um processador foi detectado; usando uma ordenadora.");
        }

        ThreadOrdenadora[] ordenadoras = new ThreadOrdenadora[quantidadeDeOrdenadoras];
        int tamanhoBase = entrada.length / quantidadeDeOrdenadoras;
        int restantes = entrada.length % quantidadeDeOrdenadoras;
        int inicioPedaco = 0;

        for (int i = 0; i < ordenadoras.length; i++)
        {
            int tamanhoPedaco = tamanhoBase + (i < restantes ? 1 : 0);
            byte[] pedaco = new byte[tamanhoPedaco];
            System.arraycopy (entrada, inicioPedaco, pedaco, 0, tamanhoPedaco);
            inicioPedaco += tamanhoPedaco;
            ordenadoras[i] = new ThreadOrdenadora (pedaco, mostrarMensagens);
            ordenadoras[i].setName ("Ordenadora-" + (i + 1));
        }

        if (mostrarMensagens)
        {
            System.out.printf ("Iniciando %d ordenadoras; tamanhos dos pedacos:",
                               quantidadeDeOrdenadoras);
            for (int i = 0; i < ordenadoras.length; i++)
                System.out.print (" " + (tamanhoBase + (i < restantes ? 1 : 0)));
            System.out.println ();
        }

        iniciarEAguardar (ordenadoras);
        byte[][] resultados = new byte[ordenadoras.length][];
        for (int i = 0; i < ordenadoras.length; i++)
            resultados[i] = ordenadoras[i].getVetorOrdenado ();

        ordenadoras = null;
        int numeroDaRodada = 1;
        while (resultados.length > 1)
        {
            int quantidadeDeMergeadoras = resultados.length / 2;
            int sobrante = resultados.length % 2;
            ThreadMergeadora[] mergeadoras = new ThreadMergeadora[quantidadeDeMergeadoras];

            for (int i = 0; i < quantidadeDeMergeadoras; i++)
            {
                mergeadoras[i] = new ThreadMergeadora (
                        resultados[i * 2], resultados[i * 2 + 1], mostrarMensagens);
                mergeadoras[i].setName ("Mergeadora-" + numeroDaRodada + "-" + (i + 1));
            }

            if (mostrarMensagens)
                System.out.printf ("Rodada %d: %d pares; sobrante impar: %s%n",
                                   numeroDaRodada, quantidadeDeMergeadoras,
                                   sobrante == 1 ? "sim" : "nao");
            iniciarEAguardar (mergeadoras);

            int quantidadeDeResultados = quantidadeDeMergeadoras + sobrante;
            byte[][] proximosResultados = new byte[quantidadeDeResultados][];
            for (int i = 0; i < quantidadeDeMergeadoras; i++)
                proximosResultados[i] = mergeadoras[i].getVetorMisturado ();

            if (sobrante == 1)
                proximosResultados[quantidadeDeResultados - 1] = resultados[resultados.length - 1];
            resultados = proximosResultados;
            numeroDaRodada++;
        }

        long fimTotal = System.nanoTime ();
        if (mostrarMensagens)
            System.out.printf ("Ordenacao paralela de %d elementos em %.3f ms%n",
                               entrada.length, (fimTotal - inicioTotal) / 1_000_000.0);
        return resultados[0];
    }

    private static void iniciarEAguardar (Thread[] threads) throws InterruptedException
    {
        boolean interrompida = Thread.interrupted ();
        Throwable falhaInicio = null;
        int iniciadas = 0;

        for (; iniciadas < threads.length; iniciadas++)
        {
            try
            {
                threads[iniciadas].start ();
            }
            catch (RuntimeException | Error erro)
            {
                falhaInicio = erro;
                break;
            }
        }

        for (int i = 0; i < iniciadas; i++)
        {
            boolean terminou = false;
            while (!terminou)
            {
                try
                {
                    threads[i].join ();
                    terminou = true;
                }
                catch (InterruptedException erro)
                {
                    interrompida = true;
                }
            }
        }

        if (Thread.interrupted ())
            interrompida = true;
        if (interrompida)
            Thread.currentThread ().interrupt ();

        if (falhaInicio instanceof Error)
            throw (Error) falhaInicio;
        if (falhaInicio instanceof RuntimeException)
            throw (RuntimeException) falhaInicio;
        if (interrompida)
            throw new InterruptedException (
                    "A ordenacao foi interrompida depois que todas as threads terminaram");
    }

    private static void menuFinal (Scanner teclado, byte[] entrada, byte[] ordenado,
                                   long tempoOrdenacaoNs, boolean permiteComparacao)
            throws InterruptedException
    {
        while (true)
        {
            System.out.println ("\n1 - Imprimir o vetor ordenado inteiro");
            System.out.println ("2 - Imprimir um intervalo do vetor ordenado");
            System.out.println ("3 - Imprimir o tempo da ordenacao");
            if (permiteComparacao)
                System.out.println ("4 - Comparar tempos paralelo e sequencial (cria threads)");
            System.out.println ("0 - Sair");

            Integer opcao = lerInteiro (teclado, "Escolha: ", 0, permiteComparacao ? 4 : 3);
            if (opcao == null)
            {
                System.out.println ("Entrada encerrada. Programa finalizado.");
                return;
            }
            if (opcao == 0)
                return;
            if (opcao == 1)
                imprimir (ordenado, 0, ordenado.length - 1);
            else if (opcao == 2)
            {
                Integer inicio = lerInteiro (teclado, "Indice inicial: ", 0, ordenado.length - 1);
                if (inicio == null)
                {
                    System.out.println ("Entrada encerrada. Programa finalizado.");
                    return;
                }
                Integer fim = lerInteiro (teclado, "Indice final: ", inicio, ordenado.length - 1);
                if (fim == null)
                {
                    System.out.println ("Entrada encerrada. Programa finalizado.");
                    return;
                }
                imprimir (ordenado, inicio, fim);
            }
            else if (opcao == 3)
                System.out.printf ("Tempo da ordenacao principal: %.3f ms%n",
                                  tempoOrdenacaoNs / 1_000_000.0);
            else if (opcao == 4)
                compararTempos (entrada);
        }
    }

    private static void compararTempos (byte[] entrada) throws InterruptedException
    {
        System.out.println ("Comparacao: mesma entrada; cada metodo cria sua propria copia. Logs desligados.");
        long inicioParalela = System.nanoTime ();
        byte[] paralelo = ordenarParalelo (entrada, false);
        long tempoParalelo = System.nanoTime () - inicioParalela;

        long inicioSequencial = System.nanoTime ();
        byte[] sequencial = MainSequencial.ordenar (entrada, false);
        long tempoSequencial = System.nanoTime () - inicioSequencial;

        System.out.printf ("Tempo paralelo: %.3f ms%n", tempoParalelo / 1_000_000.0);
        System.out.printf ("Tempo sequencial: %.3f ms%n", tempoSequencial / 1_000_000.0);
        System.out.println ("Resultados iguais: " + (Arrays.equals (paralelo, sequencial) ? "sim" : "nao"));
        System.out.println ("Ambos em ordem crescente: "
                + (estaOrdenado (paralelo) && estaOrdenado (sequencial) ? "sim" : "nao"));
    }

    private static boolean estaOrdenado (byte[] vetor)
    {
        for (int i = 1; i < vetor.length; i++)
            if (vetor[i - 1] > vetor[i])
                return false;
        return true;
    }

    private static Integer lerInteiro (Scanner teclado, String mensagem, int minimo, int maximo)
    {
        while (true)
        {
            System.out.print (mensagem);
            if (!teclado.hasNextLine ())
                return null;

            String linha = teclado.nextLine ().trim ();
            try
            {
                int valor = Integer.parseInt (linha);
                if (valor < minimo || valor > maximo)
                {
                    System.out.println ("Digite um inteiro entre " + minimo + " e " + maximo + ".");
                    continue;
                }
                return valor;
            }
            catch (NumberFormatException erro)
            {
                System.out.println ("Digite um numero inteiro valido.");
            }
        }
    }

    private static void imprimir (byte[] vetor, int inicio, int fim)
    {
        System.out.print ("[");
        for (int i = inicio; i <= fim; i++)
        {
            if (i > inicio)
                System.out.print (", ");
            System.out.print ((int) vetor[i]);
        }
        System.out.println ("]");
    }
}
