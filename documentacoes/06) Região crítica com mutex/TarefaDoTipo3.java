import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class TarefaDoTipo3 implements Runnable
{
    private ArrayList<Character> armazenamento;
    private Semaphore livre;
    private Semaphore ocupado;
    private Semaphore mutex;
    
    public TarefaDoTipo3 (ArrayList<Character> armz, Semaphore lvr, Semaphore ocp, Semaphore mtx) throws Exception
    {
        if (armz==null)
            throw new Exception ("Armazenamento ausente");
            
        if (lvr==null)
            throw new Exception ("Livre ausente");
            
        if (ocp==null)
            throw new Exception ("Ocupado ausente");
        
        if (mtx==null)
            throw new Exception ("Mutex ausente");
    
        this.armazenamento = armz;
        this.livre         = lvr;
        this.ocupado       = ocp;
        this.mutex         = mtx;
    }
    
    private Thread  tarefa = new Thread (this);
    
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
            this.livre.acquireUninterruptibly();
            
            this.mutex.acquireUninterruptibly();
            this.armazenamento.add (caractere);
            this.mutex.release();
            
            this.ocupado.release();
            try { this.tarefa.sleep (450); } catch (Exception erro) {}
            if (caractere=='9')
                caractere = '0';
            else
                caractere = (char)(((int)caractere)+1);
        }
    }
}
