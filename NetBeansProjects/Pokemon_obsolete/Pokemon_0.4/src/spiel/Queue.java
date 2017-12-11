package spiel;

public class Queue<E extends Object> implements java.io.Serializable {

    private Queue<E> next;
    public E wert;

    private Queue(E text) {
        wert = text;
    }

    public Queue() {
    }

    public void add(E text) {
        if (next == null) {
            next = new Queue(text);
        } else {
            next.add(text);
        }
    }

    public E pull() {
        if (next == null) {
            return null;
        } else {
            Queue<E> merk = next;
            next = next.next;
            return merk.wert;
        }
    }

    public E peak() {
        if (next == null) {
            return null;
        } else {
            return next.wert;
        }
    }

    public boolean isEmpty() {
        return next == null;
    }

    public int size() {
        if (next == null) {
            return 0;
        } else {
            return 1 + next.size();
        }
    }

    public Object[] toArray() {
        Object[] feld = new Object[size()];
        Queue<E> lauf = next;
        for (int i = 0; i < feld.length; i++) {
            feld[i] = lauf.wert;
            lauf = lauf.next;
        }
        return feld;
    }
}
