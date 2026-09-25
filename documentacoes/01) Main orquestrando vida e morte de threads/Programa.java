public class Programa
{
    public static void main (String[] args)
    {
        System.out.println ("Somente a main esta rodando;");
        System.out.println ("Tecle ENTER para ativar tarefa 1;");
        System.out.println ("Tecle novamente ENTER para ativar tarefa 2;");
        System.out.println ("Tecle novamente ENTER para ativar tarefa 3;");
        System.out.println ("Tecle novamente ENTER para desativar tarefa 3;");
        System.out.println ("Tecle novamente ENTER para desativar tarefa 2;");
        System.out.println ("Tecle novamente ENTER para desativar tarefa 1.");
        
        while (!Teclado.getUmString().equals(""))
           System.err.println("Só 1 ENTER é esperado!");
        TarefaDoTipo1 t1 = new TarefaDoTipo1 ();
        t1.start ();
        
        while (!Teclado.getUmString().equals(""))
           System.err.println("Só 1 ENTER é esperado!");
        TarefaDoTipo2 t2 = new TarefaDoTipo2 ();
        t2.start ();
        
        while (!Teclado.getUmString().equals(""))
           System.err.println("Só 1 ENTER é esperado!");
        TarefaDoTipo3 t3 = new TarefaDoTipo3 ();
        t3.start ();
        
        while (!Teclado.getUmString().equals(""))
           System.err.println("Só 1 ENTER é esperado!");
        t3.morra ();
        
        while (!Teclado.getUmString().equals(""))
           System.err.println("Só 1 ENTER é esperado!");
        t2.morra ();
        
        while (!Teclado.getUmString().equals(""))
           System.err.println("Só 1 ENTER é esperado!");
        t1.morra ();
        
        System.out.println ("De novo, somente a main esta rodando;");
        System.out.println ("Tecle ENTER para terminar o programa.");
        while (!Teclado.getUmString().equals(""))
           System.err.println("Só 1 ENTER é esperado!");
    }
}
