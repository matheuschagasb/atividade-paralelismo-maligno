import java.util.Random;
import java.util.Scanner;

public class Main
{
    private static int lerInteiro (Scanner teclado, String mensagem)
    {
        while (true)
        {
            System.out.print (mensagem);
            if (teclado.hasNextInt ())
                return teclado.nextInt ();
            System.out.println ("Digite um numero inteiro valido.");
            teclado.next ();
        }
    }

    private static void imprimir (int[] vetor, int inicio, int fim)
    {
        System.out.print ("[");
        for (int i = inicio; i <= fim; i++)
        {
            if (i > inicio)
                System.out.print (", ");
            System.out.print (vetor[i]);
        }
        System.out.println ("]");
    }

    public static void main (String[] args) throws InterruptedException
    {
        Scanner teclado = new Scanner (System.in);
        int quantidade;
        do
        {
            quantidade = lerInteiro (teclado, "Quantos elementos deseja ter no vetor? ");
            if (quantidade <= 0)
                System.out.println ("A quantidade deve ser maior que zero.");
        }
        while (quantidade <= 0);

        int opcao;
        do
        {
            opcao = lerInteiro (teclado,
                    "Digite 1 para informar os elementos ou 2 para gera-los aleatoriamente: ");
            if (opcao != 1 && opcao != 2)
                System.out.println ("Opcao invalida.");
        }
        while (opcao != 1 && opcao != 2);

        int[] vetor = new int[quantidade];
        if (opcao == 1)
            for (int i = 0; i < vetor.length; i++)
                vetor[i] = lerInteiro (teclado, "Elemento " + i + ": ");
        else
        {
            Random aleatorio = new Random ();
            for (int i = 0; i < vetor.length; i++)
                vetor[i] = aleatorio.nextInt (1000);
        }

        int quantidadeDeThreads = Runtime.getRuntime ().availableProcessors () - 1;
        if (quantidadeDeThreads < 1)
            quantidadeDeThreads = 1;

        ThreadOrdenadora[] ordenadoras = new ThreadOrdenadora[quantidadeDeThreads];
        int tamanhoBase = vetor.length / quantidadeDeThreads;
        int restantes = vetor.length % quantidadeDeThreads;
        int inicioDoPedaco = 0;
        long inicioDaOrdenacao = System.nanoTime ();

        for (int i = 0; i < quantidadeDeThreads; i++)
        {
            int tamanhoDoPedaco = tamanhoBase + (i < restantes ? 1 : 0);
            int[] pedaco = new int[tamanhoDoPedaco];
            for (int j = 0; j < tamanhoDoPedaco; j++)
                pedaco[j] = vetor[inicioDoPedaco + j];

            inicioDoPedaco += tamanhoDoPedaco;
            ordenadoras[i] = new ThreadOrdenadora (pedaco);
            ordenadoras[i].start ();
        }

        int[][] resultados = new int[quantidadeDeThreads][];
        for (int i = 0; i < quantidadeDeThreads; i++)
        {
            ordenadoras[i].join ();
            resultados[i] = ordenadoras[i].getVetorOrdenado ();
        }

        while (resultados.length > 1)
        {
            int quantidadeDeMergeadoras = resultados.length / 2;
            ThreadMergeadora[] mergeadoras = new ThreadMergeadora[quantidadeDeMergeadoras];

            for (int i = 0; i < quantidadeDeMergeadoras; i++)
            {
                mergeadoras[i] = new ThreadMergeadora (resultados[i * 2],
                                                       resultados[i * 2 + 1]);
                mergeadoras[i].start ();
            }

            int quantidadeDeResultados = quantidadeDeMergeadoras + resultados.length % 2;
            int[][] proximosResultados = new int[quantidadeDeResultados][];
            for (int i = 0; i < quantidadeDeMergeadoras; i++)
            {
                mergeadoras[i].join ();
                proximosResultados[i] = mergeadoras[i].getVetorMisturado ();
            }

            if (resultados.length % 2 != 0)
                proximosResultados[quantidadeDeResultados - 1] = resultados[resultados.length - 1];
            resultados = proximosResultados;
        }

        vetor = resultados[0];
        long fimDaOrdenacao = System.nanoTime ();
        double tempoEmMilissegundos = (fimDaOrdenacao - inicioDaOrdenacao) / 1_000_000.0;

        int escolha;
        do
        {
            System.out.println ("\n1 - Printar o vetor ordenado inteiro");
            System.out.println ("2 - Printar um pedaco do vetor ordenado");
            System.out.println ("3 - Printar o tempo da ordenacao");
            System.out.println ("0 - Sair");
            escolha = lerInteiro (teclado, "Escolha: ");

            if (escolha == 1)
                imprimir (vetor, 0, vetor.length - 1);
            else if (escolha == 2)
            {
                int inicio = lerInteiro (teclado, "Indice inicial: ");
                int fim = lerInteiro (teclado, "Indice final: ");
                if (inicio < 0 || fim >= vetor.length || inicio > fim)
                    System.out.println ("Intervalo invalido. Use indices entre 0 e "
                                        + (vetor.length - 1) + ".");
                else
                    imprimir (vetor, inicio, fim);
            }
            else if (escolha == 3)
                System.out.printf ("Tempo total da ordenacao: %.3f ms%n", tempoEmMilissegundos);
            else if (escolha != 0)
                System.out.println ("Opcao invalida.");
        }
        while (escolha != 0);

        teclado.close ();
    }
}
