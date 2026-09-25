public class TarefaDoTipo3 extends Inutil implements Runnable
{
    private Thread  tarefa = new Thread (this);
    private boolean fim    = false;
    
    public void start ()
    {
        this.tarefa.start();
    }

    public void morra ()
    {
        this.fim=true;
    }

    @Override
    public void run ()
    {
        char caractere='0';
        while (!this.fim)
        {
            System.out.println (caractere);
            try { this.tarefa.sleep (500); } catch (Exception erro) {}
            if (caractere=='9')
                caractere = '0';
            else
                caractere = (char)(((int)caractere)+1);
        }
    }
}
