package philosophen;

import gui.Window;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Main {
    
    private static Zustand[] states;
    private static boolean[] free;
    private static Philosoph[] philosophen;
    private static int thinkpoints,eatpoints,hits;
    public static final int P_ANZ=5;
    private static StateListener listener;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        states=new Zustand[P_ANZ];
        free=new boolean[P_ANZ];
        for(int i=0; i<P_ANZ; i++){
            setState(i,Zustand.THINKING);
            free[i]=true;
        }
        Window gui=new Window();
        gui.setVisible(true);
        listener=gui;
        philosophen=new Philosoph[P_ANZ];
        for(int i=0; i<P_ANZ; i++){
            philosophen[i]=new Philosoph(gui.progressBars[i]);
            philosophen[i].start();
        }
        boolean running=true;
        while(running){
            running=false;
            for(Philosoph p: philosophen)
                if(p.isAlive()){
                    running=true;
                    break;
                }
        }
        System.out.println("Alle Philosophen sind tot");
        System.out.println("Sie haben für PI den Wert "+4.0*hits/thinkpoints+" ermittelt");
    }
    
    public static synchronized Zustand stateOf(int id){
        while(id>=states.length)id-=P_ANZ;
        return states[id];
    }
    
    private static synchronized void setState(int id, Zustand state){
        while (id >= states.length) {
            id -= P_ANZ;
        }
        if (state != states[id]) {
            switch (state) {
                case THINKING:
                    System.out.println("Philosoph " + id + " denkt nach.");
                    break;
                case HUNGRY:
                    System.out.println("Philosoph " + id + " wartet darauf, dass er essen kann.");
                    break;
                case DEAD:
                    System.out.println("Philosoph "+id+" ist gestorben.");
                    break;
            }
            states[id] = state;
            if(listener!=null)listener.philosophStateChanged(id, state);
        }
    }
    
    public static synchronized void stopEating(int id){
        while(id>=states.length)id-=P_ANZ;
        System.out.println("Philosoph "+id+" hat aufgehört zu essen.");
        setState(id, Zustand.THINKING);
        setFree(id,true);
        setFree(id+1,true);
    }
    
    public static synchronized boolean startEating(int id){
        while(id>=P_ANZ)id-=P_ANZ;
        if(stateOf(id)==Zustand.THINKING)
                System.out.println("Philosoph "+id+" ist hungrig.");
        if(isFree(id) && isFree(id+1) && getLongestWaiting(id)==id){
            setState(id,Zustand.EATING);
            System.out.println("Philosoph "+id+" isst jetzt.");
            setFree(id,false);
            setFree(id+1,false);
            return true;
        }else{
            setState(id,Zustand.HUNGRY);
            return false;
        }
    }
    
    public static void die(int id){
        setState(id,Zustand.DEAD);
        setFree(id,true);
        setFree(id+1,true);
    }
    
    public static synchronized boolean isFree(int id){
        while(id>=free.length)id-=free.length;
        return free[id];
    }
    
    private static synchronized void setFree(int id, boolean isFree){
        while(id>=free.length)id-=free.length;
        if(free[id]!=isFree && listener!=null)listener.stickStateChanged(id, isFree);
        free[id]=isFree;
    }
    
    public static synchronized int getLongestWaiting(int id){
        int maxID=id;
        int max=0;
        for(Philosoph p: philosophen)
            if(stateOf(p.ID)==Zustand.HUNGRY && p.getTime()>max){
                max=p.getTime();
                maxID=p.ID;
            }
        return maxID;
    }
    
    public static synchronized void increaseThinkPoints(){
        thinkpoints++;
        double x=Math.random(),y=Math.random();
        if(x*x+y*y<=1)hits++;
        if(listener!=null)listener.thinkpointsChanged(thinkpoints);
    }
    
    public static synchronized void increaseEatPoints(){
        eatpoints++;
        if(listener!=null)listener.eatpointsChanged(eatpoints);
    }
}
