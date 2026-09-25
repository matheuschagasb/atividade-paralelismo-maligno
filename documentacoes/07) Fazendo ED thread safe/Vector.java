public class Vector <X> implements Cloneable
{
    private static final int TAMANHO_INICIAL = 10;

    private X[] elem;
    private int qtd;

    public Vector ()
    {
      //this.elem = new X [Vector.TAMANHO_INICIAL]; <- não compila
        this.elem = (X[])new Object [Vector.TAMANHO_INICIAL];
        this.qtd  = 0;
    }

    public synchronized int size ()
    {
        return this.qtd;
    }

    private void redimensioneSe (float taxaDeRedim)
    {
        X[] novo;
        
      //novo = new X [Math.round(this.elem.length*taxaDeRedim)]; <- não compila
        novo = (X[])new Object [Math.round(this.elem.length*taxaDeRedim)];

        for (int i=0; i<this.qtd; i++)
            novo[i] = this.elem[i];

        this.elem = novo;
    }

    public synchronized void add (X x)
    {
        if (this.qtd==this.elem.length)
            this.redimensioneSe (2.0F);

        if (x instanceof Cloneable)
            this.elem[this.qtd] = new Clonador<X>().clone(x);
        else
            this.elem[this.qtd] = x;

        this.qtd++;
    }

    public synchronized X get (int posicao) // posicao vai de 0 a this.qtd-1
    {
        if (posicao<0 || posicao>this.qtd-1)
            throw new java.lang.ArrayIndexOutOfBoundsException (posicao);
            
        if (this.elem[posicao] instanceof Cloneable)
            return new Clonador<X>().clone(this.elem[posicao]);
        else
            return this.elem[posicao];
    }

    public synchronized void remove (int posicao) throws ArrayIndexOutOfBoundsException
    {
        if (posicao<0 || posicao>this.qtd-1)
            throw new java.lang.ArrayIndexOutOfBoundsException (posicao);

        for (int i=posicao+1; i<this.qtd; i++)
            this.elem[i-1] = this.elem[i];

        this.qtd--;
        this.elem[this.qtd] = null;

        if (this.elem.length>Vector.TAMANHO_INICIAL &&
            this.qtd<=Math.round(this.elem.length*0.25F))
            this.redimensioneSe (0.5F);
    }

    @Override
    public synchronized String toString ()
    {
        String ret="[";

        for (int i=0; i<this.qtd-1; i++)
            ret = ret+this.elem[i]+", ";

        if (this.qtd>0)
            ret = ret+this.elem[this.qtd-1];

        return ret+"]";
    }

    @Override
    public synchronized boolean equals (Object obj)
    {
        if (this==obj)
            return true;
            
        if (obj==null)
            return false;
            
        if (this.getClass()!=obj.getClass())
            return false;

        Vector<X> vec = (Vector<X>)obj;

        for (int i=0; i<this.qtd; i++)
            if (!this.elem[i].equals(vec.elem[i]))
                return false;
                
        return true;
    }

    @Override
    public synchronized int hashCode ()
    {
        int ret=1/*um inteiro positivo qualquer*/;

        ret = ret*2/*um nº primo qualquer*/ + ((Integer)this.qtd).hashCode();

        for (int i=0; i<this.qtd; i++)
            ret = ret*2/*um nº primo qualquer*/ + this.elem[i].hashCode();

        if (ret<0) ret=-ret;
        return ret;
    }

    // construtor de cópia
    public Vector (Vector<X> modelo) throws Exception
    {
        if (modelo==null) throw new Exception ("Modelo ausente");

        this.qtd=modelo.qtd;

      //this.elem = new X [modelo.elem.length]; <- não compila
        this.elem = (X[])new Object [modelo.elem.length)];


        for (int i=0; i<this.qtd; i++)
            this.elem[i] = modelo[i];
    }

    @Override
    public synchronized Object clone ()
    {
        Vector<X> ret=null;

        try
        {
            ret = new Vector<X> (this);
        }
        catch (Exception erro)
        {} // nenhum tratamento pois sei que não acontecerá exceção

        return ret;
    }
}
