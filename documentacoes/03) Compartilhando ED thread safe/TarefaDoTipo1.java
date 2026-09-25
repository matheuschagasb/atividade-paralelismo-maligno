import java.util.Vector;

public class TarefaDoTipo1 extends Thread
{
    private Vector<Character> armazenamento;
    
    public TarefaDoTipo1 (Vector<Character> armz) throws Exception
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
        char caractere='a';
        while (!this.fim)
        {
            this.armazenamento.add (caractere);
            try { this.sleep (900); } catch (Exception erro) {}
            if (caractere=='z')
                caractere = 'a';
            else
                caractere = (char)(((int)caractere)+1);
        }
    }
}
