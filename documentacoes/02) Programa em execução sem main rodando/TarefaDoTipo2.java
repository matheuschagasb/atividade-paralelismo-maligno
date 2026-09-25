public class TarefaDoTipo2 extends Thread
{
    private boolean fim=false;

    public void morra ()
    {
        this.fim=true;
    }

    @Override
    public void run ()
    {
        char caractere='A';
        while (!this.fim)
        {
            System.out.println (caractere);
            try { this.sleep (500); } catch (Exception erro) {}
            if (caractere=='Z')
                caractere = 'A';
            else
                caractere = (char)(((int)caractere)+1);
        }
    }
}
