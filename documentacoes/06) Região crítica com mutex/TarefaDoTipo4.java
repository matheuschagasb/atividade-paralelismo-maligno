import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class TarefaDoTipo4 implements Runnable
{
    private ArrayList<Character> armazenamento;
    private Semaphore livre;
    private Semaphore ocupado;
    private Semaphore mutex;
    
    public TarefaDoTipo4 (ArrayList<Character> armz, Semaphore lvr, Semaphore ocp, Semaphore mtx) throws Exception
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
        while (!this.fim)
        {
            this.ocupado.acquireUninterruptibly();
            char caractere=' ';

            this.mutex.acquireUninterruptibly();
            caractere = this.armazenamento.get(0);
            this.armazenamento.remove(0);
            this.mutex.release();

            this.livre.release();
            System.out.println (caractere);
            try { this.tarefa.sleep (200); } catch (Exception erro) {}
            //try { this.tarefa.sleep (150); } catch (Exception erro) {}
            //try { this.tarefa.sleep (100); } catch (Exception erro) {}
            //try { this.tarefa.sleep (50); } catch (Exception erro) {}
        }
        
        while (this.armazenamento.size()!=0)
        {
            this.ocupado.acquireUninterruptibly();
            
            synchronized (this.armazenamento)
            {
                char caractere = this.armazenamento.get(0);
                this.armazenamento.remove(0);
            }
            
            this.livre.release();
            System.out.println (caractere);
            try { this.tarefa.sleep (600); } catch (Exception erro) {}
        }
    }
}
