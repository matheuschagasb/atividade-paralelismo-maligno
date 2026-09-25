import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class TarefaDoTipo1 extends Thread
{
    private ArrayList<Character> armazenamento;
    private Semaphore livre;
    private Semaphore ocupado;
    
    public TarefaDoTipo1 (ArrayList<Character> armz, Semaphore lvr, Semaphore ocp) throws Exception
    {
        if (armz==null)
            throw new Exception ("Armazenamento ausente");
            
        if (lvr==null)
            throw new Exception ("Livre ausente");
            
        if (ocp==null)
            throw new Exception ("Ocupado ausente");
            
        this.armazenamento = armz;
        this.livre         = lvr;
        this.ocupado       = ocp;
    }
    
    private boolean fim = false;

    public void morra ()
    {
        this.fim=true;
    }

    public void run ()
    {
        char caractere='a';
        while (!this.fim)
        {
            this.livre.acquireUninterruptibly();
            
            synchronized (this.armazenamento)
            {
                this.armazenamento.add (caractere);
            }
            
            this.ocupado.release();
            try { this.sleep (150); } catch (Exception erro) {}
            if (caractere=='z')
                caractere = 'a';
            else
                caractere = (char)(((int)caractere)+1);
        }
    }
}
