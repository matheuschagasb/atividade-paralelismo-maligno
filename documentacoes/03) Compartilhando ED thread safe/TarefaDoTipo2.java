import java.util.Vector;

public class TarefaDoTipo2 extends Thread
{
    private Vector<Character> armazenamento;
    
    public TarefaDoTipo2 (Vector<Character> armz) throws Exception
    {
        if (armz==null)
            throw new Exception ("Armazenamento ausente");
            
        this.armazenamento = armz;
    }
    
    private boolean fim = false;

    public void morra ()
    {
        this.fim=true;
    }

    public void run ()
    {
        char caractere='A';
        while (!this.fim)
        {
            this.armazenamento.add (caractere);
            try { this.sleep (800); } catch (Exception erro) {}
            if (caractere=='Z')
                caractere = 'A';
            else
                caractere = (char)(((int)caractere)+1);
        }
    }
}
