import java.util.Vector;

public class Programa
{
    public static void main (String[] args)
    {
        try
        {
            Vector<Character> armazenamento;
            armazenamento = new Vector<Character> ();

            System.out.println ("Tecle ENTER para ativar as tarefas e");
            System.out.println ("Tecle novamente ENTER para terminar o programa.");
            Teclado.getUmString();
            
            TarefaDoTipo1 t1 = new TarefaDoTipo1 (armazenamento);
            t1.start ();
            
            TarefaDoTipo2 t2 = new TarefaDoTipo2 (armazenamento);
            t2.start ();
            
            TarefaDoTipo3 t3 = new TarefaDoTipo3 (armazenamento);
            t3.start ();
            
            TarefaDoTipo4 t4 = new TarefaDoTipo4 (armazenamento);
            t4.start ();
            
            Teclado.getUmString();
                    
            t4.morra ();
            t3.morra ();
            t2.morra ();
            t1.morra ();
            
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            
            System.out.println ("Execucao do programa finalizada.");
        }
        catch (Exception erro)
        {} // sei que não passei null para o construtor de nenhuma das tarefas
    }
}
