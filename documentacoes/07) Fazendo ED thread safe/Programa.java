public class Programa
{
    public static void main (String[] args)
    {
        java.util.Vector<String> v1 =
        new java.util.Vector<String> ();        
        v1.add("C");
        v1.add("C++");
        v1.add("Java");
        System.out.println (v1);
        v1.remove(1);
        System.out.println (v1);        

        System.out.println ();

        Vector<String> v2 =
        new Vector<String> ();        
        v2.add("C");
        v2.add("C++");
        v2.add("Java");
        System.out.println (v2);
        v2.remove(1);
        System.out.println (v2);        
    }
}
