public class Data implements Cloneable
{
    private byte  dia, mes;
    private short ano;

    private static int qtd=0;

    public static int getQtd ()
    {
        return Data.qtd;
    }

    public static boolean isBissexto (short ano)
    {
        // Calendario Juliano
        if (ano<1582)
            if (ano%4==0)
                return true;
            else
                return false;

        // Calendario Gregoriano
        if (ano%400==0) return true;
        if (ano%  4==0 && ano%100!=0) return true;
        return false;
    }

    public static boolean isValida (byte dia, byte mes, short ano)
    {
        if (ano<-45) return false; // antes do Calendario Juliano
        if (ano== 0) return false; // nao existiu ano 0; do ano 1ac foi direto para o ano 1dc
        if (ano==1582 && mes==10 && dia>=5 && dia<=14) return false; // dias cortados dos calendario pelo Papa Gregorio

        if (dia<1 || dia>31 || mes<1 || mes>12) return false;

        if (dia>30 && (mes==4 || mes==6 || mes==9 || mes==11)) return false;
        if (dia>29 && mes==2) return false;
        if (dia>28 && mes==2 && !Data.isBissexto(ano)) return false;

        return true;
    }

    public /*void*/ Data (byte dia, byte mes, short ano) throws Exception
    {
        if (!Data.isValida(dia,mes,ano))
            throw new Exception ("Data invalida");

        this.dia=dia;
        this.mes=mes;
        this.ano=ano;
    }

    public void setDia (byte dia) throws Exception
    {
        if (!Data.isValida(dia,this.mes,this.ano))
            throw new Exception ("Dia invalido");

        this.dia=dia;
    }

    public byte getDia ()
    {
        return this.dia;
    }
    
    public void setMes (byte mes) throws Exception
    {
        if (!Data.isValida(this.dia,mes,this.ano))
            throw new Exception ("Mes invalido");

        this.mes=mes;
    }

    public byte getMes ()
    {
        return this.mes;
    }
    
    public void setAno (short ano) throws Exception
    {
        if (!Data.isValida(this.dia,this.mes,ano))
            throw new Exception ("Ano invalido");

        this.ano=ano;
    }

    public short getAno ()
    {
        return this.ano;
    }
    
    public void avanceUmDia () // altera o this
    {
        if (Data.isValida((byte)(this.dia+1),this.mes,this.ano))
            this.dia++;
        else if (Data.isValida((byte)1,(byte)(this.mes+1),this.ano))
        {
            this.dia=(byte)1;
            this.mes++;
        }
        else if (Data.isValida((byte)1,(byte)1,(short)(this.ano+1)))
        {
            this.dia=(byte)1;
            this.mes=(byte)1;
            this.ano++;
        }
        else if (Data.isValida((byte)1,(byte)1,(short)(this.ano+2))) // ano -1
        {
            this.dia=(byte)1;
            this.mes=(byte)1;
            this.ano=(short)(this.ano+2);
        }
        else // ano==1582 && mes==10 && dia==4
            this.dia=(byte)15;	
    }

    public void avanceVariosDias (int qtd) throws Exception // altera o this
    {
        if (qtd<=0) throw new Exception ("Quantidade invalida");

        for (int i=0; i<qtd; i++)
            this.avanceUmDia();
    }

    public Data getDiaSeguinte () // não altera o this
    {
        Data retorno=null;
        try { retorno = new Data ((byte)(this.dia+1),this.mes,this.ano); }
        catch (Exception erro1) {
            try { retorno = new Data ((byte)1,(byte)(this.mes+1),this.ano); }
            catch (Exception erro2) {
                try { retorno = new Data ((byte)1,(byte)1,(short)(this.ano+1)); }
                catch (Exception erro3) {
                    try { retorno = new Data ((byte)1,(byte)1,(short)(this.ano+2)); }
                    catch (Exception erro4) {
                        try { retorno = new Data ((byte)15,this.mes,this.ano); }  // ano==1582 && mes==10 && dia==4
                        catch (Exception erro5) 
                        {} // é a ultima possibilidade; não deve dar erro 
                    }
                }
            }
         }
         return retorno;
    }
    /*
    public Data getDiaSeguinte () // não altera o this
    {
        Data retorno=null;
        try
        {
            retorno = new Data (this.dia,this.mes,this.ano);
        }
        catch (Exception erro)
        {} // sei que nao vai dar erro, pq parti duma data valida

        retorno.avanceUmDia();
        return retorno;
    */

    public Data getVariosDiasAdiante (int qtd) throws Exception // não altera o this
    {
        if (qtd<=0) throw new Exception ("Quantidade invalida");

        Data retorno = new Data (this.dia,this.mes,this.ano);
        retorno.avanceVariosDias(qtd);

        return retorno;
    }

    public void retrocedaUmDia () // altera o this
    {
        if (this.ano==1 && this.mes==1 && this.dia==1)
        {
            this.ano=(short)-1;
            this.mes=(byte)12;
            this.dia=(byte)31;
        }
        else if (this.ano==1582 && this.mes==10 && this.dia==15)
            this.dia=(byte)4;
        else if (Data.isValida((byte)(this.dia-1),this.mes,this.ano))
            this.dia--;
        else if (Data.isValida((byte)31,(byte)(this.mes-1),this.ano))
        {
            this.dia=(byte)31;
            this.mes--;
        }
        else if (Data.isValida((byte)30,(byte)(this.mes-1),this.ano))
        {
            this.dia=(byte)30;
            this.mes--;
        }
        else if (Data.isValida((byte)29,(byte)(this.mes-1),this.ano))
        {
            this.dia=(byte)31;
            this.mes--;
        }
        else if (Data.isValida((byte)28,(byte)(this.mes-1),this.ano))
        {
            this.dia=(byte)31;
            this.mes--;
        }
        else // if (this.mes==1 && this.dia==1)
        {
            this.dia=(byte)31;
            this.mes=(byte)12;
            this.ano--;
        }
    }

    public void retrocedaVariosDias (int qtd) throws Exception // altera o this
    {
        if (qtd<=0) throw new Exception ("Quantidade invalida");

        for (int i=0; i<qtd; i++)
            this.retrocedaUmDia();
    }

    public Data getDiaAnterior () // não altera o this
    {
        Data ret=null;

        if (this.ano==1 && this.mes==1 && this.dia==1) { try {
            ret = new Data ((byte)31,(byte)12,(short)-1);
        } catch (Exception erro1) {}} else if (this.ano==1582 && this.mes==10 && this.dia==15) { try {
            ret = new Data ((byte)4,(byte)10,(short)1582);
        } catch (Exception erro2) {}} else { try {
            ret = new Data ((byte)(this.dia-1),this.mes,this.ano);
        } catch (Exception erro3) { try {
            ret = new Data ((byte)31,(byte)(this.mes-1),this.ano);
        } catch (Exception erro4) { try {
            ret = new Data ((byte)30,(byte)(this.mes-1),this.ano);
        } catch (Exception erro5) { try {
            ret = new Data ((byte)29,(byte)(this.mes-1),this.ano);
        } catch (Exception erro6) { try {
            ret = new Data ((byte)28,(byte)(this.mes-1),this.ano);
        } catch (Exception erro7) { try {
            ret = new Data ((byte)1,(byte)1,(short)(this.ano-1));
        } catch (Exception erro8) {}}}}}}}

        return ret;
    }
    
    public Data getVariosDiasAtras (int qtd) throws Exception // não altera o this
    {
        if (qtd<=0) throw new Exception ("Quantidade invalida");

        Data retorno = new Data (this.dia,this.mes,this.ano);
        retorno.retrocedaVariosDias(qtd);

        return retorno;
    }

    @Override
    public String toString ()
    {
        return
        (this.dia<10?"0":"")+this.dia+"/"+
        (this.mes<10?"0":"")+this.mes+"/"+
        (this.ano<0?-this.ano+"ac":this.ano);
    }

    @Override
    public boolean equals (Object obj)
    {
        if (obj==this) return true;
        if (obj==null) return false;
        if (obj.getClass()!=this.getClass()) return false;
        Data d = (Data)obj;
        if (d.dia!=this.dia || d.mes!=this.mes || d.ano!=this.ano) return false;
        return true;
    }

    public /*void*/ Data (Data modelo) throws Exception
    {
        if (modelo==null) throw new Exception ("Modelo ausente");

        this.dia=modelo.dia;
        this.mes=modelo.mes;
        this.ano=modelo.ano;
        // nao clonamos os atributos de modelo, pq nao sao
        // objetos de classe que tenham métodos que alteram
        // atributos nao static
    }

    @Override
    public Object clone ()
    {
        Data retorno=null;
        try
        {
            retorno = new Data (this);
        }
        catch (Exception erro)
        {} // sei que o cc só da erro quando recebe null, e this nunca é null

        return retorno;
    }
}

