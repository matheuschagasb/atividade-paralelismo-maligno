public class Aluno
{
    //...
    private int    idade;
    private String nome;
    private Data   nascimento;
    //...
    
    //...
    public Aluno (...,int i, String no, Data na,...)
    {
        ...
        this.setIdade      (i);
        this.setNome       (no);
        this.setNascimento (na);
        ...
    }

    public void setIdade (int i) throws Exception
    {
        if (i<=5)
            throw new Exception ("Idade invalida");
        
        this.idade = i;
    }
    
    public int getIdade ()
    {
        return this.idade;
    }
    
    public void setNome (String no) throws Exception
    {
        if (no==null || no.equals(""))
            throw Exception ("Nome ausente");
            
        this.nome = no;
    }
    
    public String getNome ()
    {
        return this.nome;
    }
            
    public void setNascimento (Data na) throws Exception
    {
        if (na==null)
            throw Exception ("Data ausente");
            
        /*
        this.nascimento = na; // sem noção em último grau
        */
        this.nascimento = new Data (na);
      //this.nascimento = (Data)na.clone();
    }
    
    public Data getNascimento ()
    {
        /*
        return this.nascimento; // sem noção em último grau
        */
        return new Data (this.nascimento);
      //return (Data)this.nascimento.clone();
    }
    //...
}
