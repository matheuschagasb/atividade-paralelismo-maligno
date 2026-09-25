public class ThreadOrdenadora extends Thread
{
    private int[] vetor;

    public ThreadOrdenadora (int[] vetor)
    {
        this.vetor = vetor;
    }

    @Override
    public void run ()
    {
        long inicio = System.nanoTime ();
        this.ordenar (0, this.vetor.length - 1);
        long fim = System.nanoTime ();

        System.out.printf ("%s ordenou %d elementos em %.3f ms%n",
                           this.getName (), this.vetor.length,
                           (fim - inicio) / 1_000_000.0);
    }

    private void ordenar (int inicio, int fim)
    {
        if (inicio >= fim)
            return;

        int meio = (inicio + fim) / 2;
        this.ordenar (inicio, meio);
        this.ordenar (meio + 1, fim);
        this.misturar (inicio, meio, fim);
    }

    private void misturar (int inicio, int meio, int fim)
    {
        int[] auxiliar = new int[fim - inicio + 1];
        int esquerda = inicio;
        int direita = meio + 1;
        int posicao = 0;

        while (esquerda <= meio && direita <= fim)
        {
            if (this.vetor[esquerda] <= this.vetor[direita])
                auxiliar[posicao++] = this.vetor[esquerda++];
            else
                auxiliar[posicao++] = this.vetor[direita++];
        }

        while (esquerda <= meio)
            auxiliar[posicao++] = this.vetor[esquerda++];

        while (direita <= fim)
            auxiliar[posicao++] = this.vetor[direita++];

        for (int i = 0; i < auxiliar.length; i++)
            this.vetor[inicio + i] = auxiliar[i];
    }

    public int[] getVetorOrdenado ()
    {
        return this.vetor;
    }
}
