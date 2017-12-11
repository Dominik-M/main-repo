package mathe;

import java.util.Objects;

public abstract class Ausdruck {
    private String name; // Die Variable die den Ausdruck bezeichnet z.B. x
    
    public void setName(String n){
        name=n;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Ausdruck)
            return((Ausdruck)o).name.equals(name);
        else
            return super.equals(o);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.name);
        return hash;
    }
}