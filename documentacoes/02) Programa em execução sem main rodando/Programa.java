public class Programa
{
    public static void main (String[] args)
    {
        System.out.println ("Somente a main esta rodando;");
        System.out.println ("Tecle ENTER para ativar tarefa 1;");
        System.out.println ("Tecle novamente ENTER para ativar tarefa 2;");
        System.out.println ("Tecle novamente ENTER para ativar tarefa 3.");
        
        Teclado.getUmString();
        TarefaDoTipo1 t1 = new TarefaDoTipo1 ();
        t1.start ();
        
        Teclado.getUmString();
        TarefaDoTipo2 t2 = new TarefaDoTipo2 ();
        t2.start ();
        
        Teclado.getUmString();
        TarefaDoTipo3 t3 = new TarefaDoTipo3 ();
        t3.start ();
        
        System.out.println ("A main morreu!");
    }
}
