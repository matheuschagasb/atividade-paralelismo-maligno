import java.util.Vector;

public class TarefaDoTipo3 implements Runnable
{
    private Vector<Character> armazenamento;
    
    public TarefaDoTipo3 (Vector<Character> armz) throws Exception
    {
        if (armz==null)
            throw new Exception ("Armazenamento ausente");
            
        this.armazenamento = armz;
    }
    
    private Thread tarefa = new Thread (this);
    
    public void start ()
    {
        this.tarefa.start();
    }
    
    public void join () throws InterruptedException
    {
        this.tarefa.join();
    }

    private boolean fim = false;

    public void morra ()
    {
        this.fim=true;
    }

    public void run ()
    {
        char caractere='0';
        while (!this.fim)
        {
            this.armazenamento.add (caractere);
            try { this.tarefa.sleep (700); } catch (Exception erro) {}
            if (caractere=='9')
                caractere = '0';
            else
                caractere = (char)(((int)caractere)+1);
        }
    }
}
