public class ThreadMergeadora extends Thread
{
    private int[] primeiro;
    private int[] segundo;
    private int[] resultado;

    public ThreadMergeadora (int[] primeiro, int[] segundo)
    {
        this.primeiro = primeiro;
        this.segundo = segundo;
    }

    @Override
    public void run ()
    {
        this.resultado = new int[this.primeiro.length + this.segundo.length];
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
    }

    public int[] getVetorMisturado ()
    {
        return this.resultado;
    }
}
