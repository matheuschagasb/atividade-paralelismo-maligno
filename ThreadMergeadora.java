public class ThreadMergeadora extends Thread
{
    private final byte[] primeiro;
    private final byte[] segundo;
    private final boolean logs;
    private byte[] resultado;
    private volatile Throwable falha;

    public ThreadMergeadora (byte[] primeiro, byte[] segundo)
    {
        this (primeiro, segundo, true);
    }

    public ThreadMergeadora (byte[] primeiro, byte[] segundo, boolean logs)
    {
        if (primeiro == null || segundo == null)
            throw new IllegalArgumentException ("Vetor ausente para mistura");

        this.primeiro = primeiro;
        this.segundo = segundo;
        this.logs = logs;
    }

    @Override
    public void run ()
    {
        long inicio = System.nanoTime ();
        try
        {
            long tamanhoTotal = (long)this.primeiro.length + this.segundo.length;
            if (tamanhoTotal > Integer.MAX_VALUE)
                throw new IllegalArgumentException ("Resultado maior que o tamanho máximo de um vetor");
            this.resultado = new byte[(int)tamanhoTotal];
            int i = 0;
            int j = 0;
            int k = 0;

            while (i < this.primeiro.length && j < this.segundo.length)
            {
                if (this.primeiro[i] <= this.segundo[j])
                    this.resultado[k++] = this.primeiro[i++];
                else
                    this.resultado[k++] = this.segundo[j++];
            }

            while (i < this.primeiro.length)
                this.resultado[k++] = this.primeiro[i++];

            while (j < this.segundo.length)
                this.resultado[k++] = this.segundo[j++];

            long fim = System.nanoTime ();
            if (this.logs)
                System.out.printf ("%s misturou %d + %d elementos em %.3f ms%n",
                                   this.getName (), this.primeiro.length,
                                   this.segundo.length, (fim - inicio) / 1_000_000.0);
        }
        catch (RuntimeException | Error erro)
        {
            this.falha = erro;
        }
    }

    public byte[] getVetorMisturado ()
    {
        if (this.getState () != Thread.State.TERMINATED)
            throw new IllegalStateException ("A mistura ainda não terminou");
        if (this.falha != null)
        {
            if (this.falha instanceof Error)
                throw (Error)this.falha;
            throw (RuntimeException)this.falha;
        }
        if (this.resultado == null)
            throw new IllegalStateException ("A mistura terminou sem produzir um resultado");
        return this.resultado;
    }
}
