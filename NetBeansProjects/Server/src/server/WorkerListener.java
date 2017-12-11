package server;

public interface WorkerListener {

    public void receive(Worker source, String text);

    public void fire(Worker a);
}
