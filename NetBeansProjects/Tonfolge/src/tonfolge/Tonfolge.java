package tonfolge;

import javax.sound.midi.*;

/**
 * Diese Klasse stellt einen einfachen Zugriff auf die Midi-F?higkeiten eines
 * Rechners zur Verf?gung. Eine Tonfolge ist eine Menge von Noten mit einem
 * bestimmten Anfangszeitpunkt, einer bestimmten Dauer und gespielt von einem
 * bestimmten Instrument.<p>
 * Ein Programm, das eine Tonfolge abspielen soll, belegt schon bei der
 * Erzeugung einer Tonfolge Systemresourcen (zwei Threads steuern die
 * Soundkarte). Weil eine Tonfolge mehrmals abspielbar sein soll, k?nnen diese
 * Resourcen nach dem ersten Abspielen nicht freigegeben werden. Ein Programm
 * mit nicht beendeten Threads endet aber nicht gew?hnlich, sondern muss mit
 * <CODE>System.exit(0)</CODE> beendet werden. Damit werden dann die beiden
 * erw?hnten Threads auch get?tet.<p>
 * Wenn dieses (logische) Verhalten nicht gew?nscht wird, weil etwa eine
 * Tonfolge nur ein einziges Mal abgespielt und die Geschichte damit beendet
 * sein soll, kann mit der Methode {@link endNote()} eine Abbruchnachricht in
 * die Tonsequenz eingeschoben werden. Diese sorgt daf?r, dass die beiden
 * Threads am Ende get?tet werden. Die Tonfolge ist danach aber nicht weiter zu
 * gebrauchen. Da solch ein Verhalten un?blich ist, ist die Methode endNote
 * eigentlich eine technische Schweinerei und deshalb <B>deprecated</B>.<p>
 * N?here Informationen zur Tonerzeugung findet man in doc/guide/sound.
 */
public class Tonfolge implements MetaEventListener
{

    private Track track;
    private Sequencer sequencer;
    private Sequence sequence;
    private MetaMessage stopmessage = new MetaMessage();
    /**
     * gibt die zeitlich hinterste Position des Liedes an.
     */
    private int lastTick;
    private java.util.LinkedList<TonListener> tonListener = new java.util.LinkedList();

    /**
     * Erzeugt eine Tonfolge.
     */
    public Tonfolge()
    {
        try
        {
            stopmessage.setMessage(47, null, 0);//47=MetaMessage: Liedende
            sequencer = MidiSystem.getSequencer();
            sequence = new Sequence(Sequence.PPQ, 4);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        sequencer.addMetaEventListener(this);
        track = sequence.createTrack();
    }

    /**
     * Spielt die Tonfolge mit einer bestimmten Geschwindigkeit ab. Die Angabe
     * erfolgt in Beats per Minute. Ein Beat ist vier Ticks lang.
     *
     * @param bpm die Geschwindigkeit in Beats per Minute.
     */
    public void start(final int bpm)
    {
        try
        {
            sequencer.open();
            sequencer.setSequence(sequence);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        sequencer.setTempoInBPM(bpm);
        sequencer.start();
    }

    public void stop()
    {
        if (sequencer.isRunning())
        {
            sequencer.stop();
        }
        if (sequencer.isOpen())
        {
            sequencer.close();
        }
        for (TonListener tl : tonListener)
        {
            tl.ende();
        }
    }

    /**
     * Gibt an, ob die Tonfolge gerade im Begriff ist, abgespielt zu werden.
     *
     * @return true, wenn noch T?ne zu spielen sind, sonst false.
     */
    public boolean nochTöne()
    {
        return sequencer.isRunning();
    }

    /**
     * erzeugt eine neue Note.
     *
     * @param note die Nummer der gew?nschten Note. 60 steht f?r das
     * ein-gestrichene c. Jede Zahl steht f?r einen Halbton.
     * @param vonTick die Startzeit der Note in Ticks ab dem Anfang der Folge.
     * @param lenTick die L?nge der Note in Ticks. 4 Ticks sind ein Beat (Takt).
     */
    public MidiEvent[] neueNote(int note, int vonTick, int lenTick)
    {
        MidiEvent[] retVal = new MidiEvent[2];
        retVal[0] = startNote(note, vonTick);
        retVal[1] = stopNote(note, vonTick + lenTick);
        return retVal;
    }

    /**
     * schl?gt eine Note an.
     *
     * @param note die Nummer der gew?nschten Note.
     * @param tick die Startzeit der Note ab dem Anfang der Folge in Ticks. 4
     * Ticks sind ein Beat (Takt).
     */
    public MidiEvent startNote(int note, int tick)
    {
        return setShortMessage(ShortMessage.NOTE_ON, note, tick);
    }

    /**
     * beendet eine Note.
     *
     * @param note die Nummer der zu beendenden Note.
     * @param tick die Zeit des Endes in Ticks ab dem Start der Folge.
     */
    public MidiEvent stopNote(int note, int tick)
    {
        MidiEvent retVal = setShortMessage(ShortMessage.NOTE_OFF, note, tick);
        if (lastTick <= tick)
        {
            lastTick = tick;
        }
        return retVal;
    }

    /**
     * h?ngt eine Note an das Ende der Folge.
     *
     * @param note die Nummer der Note.
     * @param lenTick die L?nge der Note in Ticks.
     */
    public MidiEvent[] hintenNote(int note, int lenTick)
    {
        return neueNote(note, lastTick, lenTick);
    }

    /**
     * h?ngt hinten eine Pause an.
     *
     * @param lenTick die L?nge der Pause in Ticks.
     */
    public void hintenPause(int lenTick)
    {
        lastTick += lenTick;
    }

    /**
     * gibt die L?nge des Liedes an. return die Tickposition nach dem letzten
     * Ton.
     */
    public int lastTick()
    {
        return lastTick;
    }

    /**
     * schaltet um auf ein neues Instrument. Die Zuordnungen der Instrumente zu
     * den positiven Zahlen folgt der Midi-Spezifikation.
     *
     * @param instr die Nummer des Instruments.
     * @return Erzeugtes MidiEvent für den Instrument wechsel
     */
    public MidiEvent setInstrument(int instr)
    {
        return setShortMessage(ShortMessage.PROGRAM_CHANGE, instr, lastTick);
    }

    private MidiEvent setShortMessage(int befehl, int note, int tick)
    {
        ShortMessage message = new ShortMessage();
        try
        {
            message.setMessage(befehl, 0, note, 90);
            MidiEvent event = new MidiEvent(message, tick);
            track.add(event);
            return event;
        }
        catch (InvalidMidiDataException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean removeMidiEventFromTrack(MidiEvent event)
    {
        return track.remove(event);
    }

    /**
     * Diese Methode muss einmal am Ende des St?ckes aufgerufen werden, um eine
     * end-Markierung im Lied zu setzen. Dadurch kann dann die Tonwiedergabe
     * beendet werden. Wird eine Note zeitlich nach der Endnote gesetzt, so wird
     * sie beim Abspielen nicht wiedergegeben. Eine Tonfolge bei Verwendung
     * dieser Methode nur ein einziges Mal abspielbar. Ein nachtr?gliches
     * Bearbeiten der T?ne hat also keinen Sinn. Diese Methode zu verwenden hat
     * nur dann Sinn, wenn das verwendende Programm ein Konsolenprogramm ist,
     * das nach der Abarbeitung der main-Methode terminieren soll, dies aber auf
     * Grund der hier erzeugten Threads dies nicht tun w?rde.
     */
    public void endnote()
    {
        MidiEvent event = new MidiEvent(stopmessage, lastTick + 2);
        track.add(event);
    }

    /**
     * wird aufgerufen, wenn in der Tonfolge ein MetaEvent passiert, also etwas,
     * das mit der Musik nichts zu tun hat. Die Methode t?tet dann alle Threads.
     * Ein Programm, das eine <CODE>Tonfolge</CODE> verwendet, endet dann auf
     * gew?hnlichem Weg. Dies ist nur dann von Belang, wenn es sich um ein
     * Konsolenprogramm handelt. Programme mit GUI enden sowieso nicht
     * gew?hnlich.
     *
     * @param mm die MetaMessage. Tonfolge reagiert nur auf Typ 47 (Abbruch).
     */
    @Override
    public void meta(MetaMessage mm)
    {
        if (mm.getType() != 47)
        {
            return;
        }
        sequencer.close();
        ThreadGroup g = Thread.currentThread().getThreadGroup();
        Thread[] alle = new Thread[g.activeCount()];
        g.enumerate(alle);
        for (Thread alle1 : alle)
        {
            if (alle1.getName().toLowerCase().contains("sound"))
            {
                alle1.interrupt();
            }
        }
        for (TonListener tl : tonListener)
        {
            tl.ende();
        }
    }

    public void addTonListener(TonListener tl)
    {
        tonListener.add(tl);
    }

    public void removeTonListener(TonListener tl)
    {
        tonListener.remove(tl);
    }
}
