/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package akinator;

/**
 *
 * @author Dominik
 */
public class SortedStringList {
    private String text;
    private SortedStringList next;
    
    public void add(String s)
    {
        if(text==null)
            text=s;
        else if(s.compareTo(text) < 0)
        {
            SortedStringList neu = new SortedStringList();
            neu.text = text;
            text = s;
            neu.next = next;
            next = neu;
        }
        else if(next == null)
        {
            next = new SortedStringList();
            next.text = s;
        }
        else next.add(s);
    }
    
    public int getLength()
    {
        if(text==null)
            return 0;
        else if(next!=null)
            return 1+next.getLength();
        else 
            return 1;
    }
    
    public String[] getValues()
    {
        int len=getLength();
        String[] values = new String[len];
        SortedStringList lauf = this;
        for(int i=0; i<len; i++)
        {
            values[i]=lauf.text;
            lauf = lauf.next;
        }
        return values;
    }
}
