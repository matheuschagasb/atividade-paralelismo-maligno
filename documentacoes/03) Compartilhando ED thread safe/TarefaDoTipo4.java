import java.util.Vector;

public class TarefaDoTipo4 implements Runnable
{
    private Vector<Character> armazenamento;
    
    public TarefaDoTipo4 (Vector<Character> armz) throws Exception
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
        while (!this.fim)
        {
            if (this.armazenamento.size()==0)
                this.tarefa.yield (); // abre mão de sua vez no processador
            else
            {
                char caractere = this.armazenamento.get(0);
                this.armazenamento.remove(0);
                System.out.println (caractere);
                try { this.tarefa.sleep (10); } catch (Exception erro) {}
                //try { this.tarefa.sleep (150); } catch (Exception erro) {}
                //try { this.tarefa.sleep (100); } catch (Exception erro) {}
                //try { this.tarefa.sleep (50); } catch (Exception erro) {}
            }
        }
        
        while (this.armazenamento.size()!=0)
        {
            char caractere = this.armazenamento.get(0);
            this.armazenamento.remove(0);
            System.out.println (caractere);
            try { this.tarefa.sleep (600); } catch (Exception erro) {}
        }
    }
}
