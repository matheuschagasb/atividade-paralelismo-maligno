public class MainSequencial
{
    public static void main (String[] args)
    {
        Main.executar (false);
    }

    public static byte[] ordenar (byte[] entrada, boolean mostrarMensagens)
    {
        if (entrada == null)
            throw new IllegalArgumentException ("Vetor de entrada ausente");

        long inicio = System.nanoTime ();
        byte[] resultado = entrada.clone ();
        byte[] auxiliar = new byte[resultado.length];
        ordenar (resultado, auxiliar, 0, resultado.length - 1);
        long fim = System.nanoTime ();

        if (mostrarMensagens)
            System.out.printf ("Ordenação sequencial de %d elementos em %.3f ms%n",
                               resultado.length, (fim - inicio) / 1_000_000.0);
        return resultado;
    }

    private static void ordenar (byte[] vetor, byte[] auxiliar, int inicio, int fim)
    {
        if (inicio >= fim)
            return;

        int meio = inicio + (fim - inicio) / 2;
        ordenar (vetor, auxiliar, inicio, meio);
        ordenar (vetor, auxiliar, meio + 1, fim);
        misturar (vetor, auxiliar, inicio, meio, fim);
    }

    private static void misturar (byte[] vetor, byte[] auxiliar,
                                  int inicio, int meio, int fim)
    {
        int esquerda = inicio;
        int direita = meio + 1;
        int posicao = inicio;

        while (esquerda <= meio && direita <= fim)
        {
            if (vetor[esquerda] <= vetor[direita])
                auxiliar[posicao++] = vetor[esquerda++];
            else
                auxiliar[posicao++] = vetor[direita++];
        }

        while (esquerda <= meio)
            auxiliar[posicao++] = vetor[esquerda++];

        while (direita <= fim)
            auxiliar[posicao++] = vetor[direita++];

        for (int i = inicio; i <= fim; i++)
            vetor[i] = auxiliar[i];
    }
}
