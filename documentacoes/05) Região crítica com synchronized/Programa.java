import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Programa
{
    public static void main (String[] args)
    {
        try
        {
            ArrayList<Character> armazenamento = new ArrayList<Character> ();
            Semaphore livre                    = new Semaphore (1024,true);
            Semaphore ocupado                  = new Semaphore (0,true);

            System.out.println ("Tecle ENTER para ativar as tarefas e");
            System.out.println ("Tecle novamente ENTER para terminar o programa.");
            Teclado.getUmString();
            
            TarefaDoTipo1 t1 = new TarefaDoTipo1 (armazenamento, livre, ocupado);
            t1.start ();
            
            TarefaDoTipo2 t2 = new TarefaDoTipo2 (armazenamento, livre, ocupado);
            t2.start ();
            
            TarefaDoTipo3 t3 = new TarefaDoTipo3 (armazenamento, livre, ocupado);
            t3.start ();
            
            TarefaDoTipo4 t4 = new TarefaDoTipo4 (armazenamento, livre, ocupado);
            t4.start ();
            
            Teclado.getUmString();
                    
            t4.morra ();
            t3.morra ();
            t2.morra ();
            t1.morra ();
            /*
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            */
            System.out.println ("Execucao do programa finalizada.");
        }
        catch (Exception erro)
        {} // sei que não passei null para o construtor de nenhuma das tarefas
    }
}
