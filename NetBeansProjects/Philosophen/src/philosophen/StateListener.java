package philosophen;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public interface StateListener {
    
    void philosophStateChanged(int id, Zustand state);
    
    void stickStateChanged(int id, boolean isFree);
    
    void thinkpointsChanged(int value);
    
    void eatpointsChanged(int value);
}