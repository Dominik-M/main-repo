package server;

public interface WorkerListener {
    public void receive(String text);
    
    public void fire(Worker a);
}
