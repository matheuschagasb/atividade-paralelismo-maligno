public class ThreadOrdenadora extends Thread
{
    private final byte[] vetor;
    private final boolean logs;
    private volatile Throwable falha;

    public ThreadOrdenadora (byte[] vetor)
    {
        this (vetor, true);
    }

    public ThreadOrdenadora (byte[] vetor, boolean logs)
    {
        if (vetor == null)
            throw new IllegalArgumentException ("Vetor ausente");

        this.vetor = vetor;
        this.logs = logs;
    }

    @Override
    public void run ()
    {
        long inicio = System.nanoTime ();
        try
        {
            byte[] auxiliar = new byte[this.vetor.length];
            this.ordenar (0, this.vetor.length - 1, auxiliar);
            long fim = System.nanoTime ();

            if (this.logs)
                System.out.printf ("%s ordenou %d elementos em %.3f ms%n",
                                   this.getName (), this.vetor.length,
                                   (fim - inicio) / 1_000_000.0);
        }
        catch (RuntimeException | Error erro)
        {
            this.falha = erro;
        }
    }

    private void ordenar (int inicio, int fim, byte[] auxiliar)
    {
        if (inicio >= fim)
            return;

        int meio = inicio + (fim - inicio) / 2;
        this.ordenar (inicio, meio, auxiliar);
        this.ordenar (meio + 1, fim, auxiliar);
        this.misturar (inicio, meio, fim, auxiliar);
    }

    private void misturar (int inicio, int meio, int fim, byte[] auxiliar)
    {
        int esquerda = inicio;
        int direita = meio + 1;
        int posicao = inicio;

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

        for (int i = inicio; i <= fim; i++)
            this.vetor[i] = auxiliar[i];
    }

    public byte[] getVetorOrdenado ()
    {
        if (this.getState () != Thread.State.TERMINATED)
            throw new IllegalStateException ("A ordenação ainda não terminou");
        if (this.falha instanceof Error)
            throw (Error)this.falha;
        if (this.falha instanceof RuntimeException)
            throw (RuntimeException)this.falha;
        return this.vetor;
    }
}
