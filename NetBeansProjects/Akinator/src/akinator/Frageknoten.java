package akinator;

/**
 *
 * @author Dominik
 */
public class Frageknoten implements java.io.Serializable{
    private Frageknoten ja,nein;
    private String text;
    
    public Frageknoten(String name)
    {
        text = name;
        ja = null;
        nein = null;
    }
    
    public boolean isEndknoten()
    {
        return ((ja==null) && (nein==null));
    }
    
    public Frageknoten getJaKnoten()
    {
        return ja;
    }
    
    void setJaKnoten(Frageknoten k)
    {
        ja = k;
    }
    
    public Frageknoten getNeinKnoten()
    {
        return nein;
    }
    
    void setNeinKnoten(Frageknoten k)
    {
        nein = k;
    }
    
    @Override
    public String toString()
    {
        return text;
    }
    
    /**
     * Fügt einen Frageknoten mit einer neuen Person ein.
     * @param name Name eine neuen Person.
     * @param frage Eine Frage, die bei der neuen Person mit Ja und
     *              bei der alten mit Nein beantwortet wird.
     */
    public void addJaFrage(String name,String frage)
    {
        Frageknoten neu = new Frageknoten(name);
        Frageknoten alt = new Frageknoten(text);
        alt.ja = ja;
        alt.nein = nein;
        text = frage;
        ja = neu;
        nein = alt;
    }
    
    /**
     * Fügt einen Frageknoten mit einer neuen Person ein.
     * @param name Name eine neuen Person.
     * @param frage Eine Frage, die bei der neuen Person mit Nein und
     *              bei der alten mit Ja beantwortet wird.
     */
    public void addNeinFrage(String name,String frage)
    {
        Frageknoten neu = new Frageknoten(name);
        Frageknoten alt = new Frageknoten(text);
        alt.ja = ja;
        alt.nein = nein;
        text = frage;
        ja = alt;
        nein = neu;
    }
    
    public String printPreOrder()
    {
        String s=text;
        if(ja!=null)s=ja.printPreOrder()+" "+s;
        if(nein!=null)s=s+nein.printPreOrder();
        return s;
    }
    
    public String[] printAlphabetical()
    {
        SortedStringList list = new SortedStringList();
        list.add(text);
        if(ja != null)
        {
            for(String s:ja.printAlphabetical())
                list.add(s);
        }
        if(nein != null)
        {
            for(String s:nein.printAlphabetical())
                list.add(s);
        }
        return list.getValues();
    }
}