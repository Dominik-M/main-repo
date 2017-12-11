package snake;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@SuppressWarnings("serial")
public class Profile implements java.io.Serializable {

    public static final File PROFILES_FILE = new File("profiles.dat");
    private static java.util.LinkedList<Profile> allProfiles = new java.util.LinkedList<>();
    private static final java.util.LinkedList<ProfileListener> listeners = new java.util.LinkedList<ProfileListener>();
    private static Profile currentProfile;

    private String name;
    private Preferences prefs;
    public final Archievement[] archievements = Archievement.values();

    static class Archievement implements java.io.Serializable {

        public final ArchievementName NAME;
        private boolean unlocked;

        private Archievement(ArchievementName name) {
            this.NAME = name;
            unlocked = false;
        }

        @Override
        public String toString() {
            return NAME.name();
        }

        public boolean isUnlocked() {
            return unlocked;
        }

        public void unlock() {
            if (!unlocked) {
                unlocked = true;
                saveProfiles(PROFILES_FILE);
                for (ProfileListener l : listeners) {
                    l.archievementUnlocked(this);
                }
            }
        }

        public enum ArchievementName {

            INSANE("Play with INSANE speed");

            public final String description;

            ArchievementName(String description) {
                this.description = description;
            }
        }

        public static Archievement[] values() {
            Archievement[] values = new Archievement[ArchievementName.values().length];
            for (int i = 0; i < values.length; i++) {
                values[i] = new Archievement(ArchievementName.values()[i]);
            }
            return values;
        }
    }

    class Preferences implements java.io.Serializable {

        public int windowWidth = 400, windowHeight = 400,
                clockDelay = SnakePanel.CLOCK_DELAY_FAST, breaks = 3,
                powerupDuration = 100;
        public double powerupChance = 2.5;
        public Snake.Level favLevel = Snake.Level.LEVEL_LAB;

        private Preferences() {
        }

        @Override
        public Preferences clone() {
            Preferences clone = new Preferences();
            clone.windowHeight = this.windowHeight;
            clone.windowWidth = this.windowWidth;
            clone.clockDelay = this.clockDelay;
            clone.breaks = this.breaks;
            clone.powerupChance = this.powerupChance;
            clone.powerupDuration = this.powerupDuration;
            clone.favLevel = this.favLevel;
            return clone;
        }
    }

    private Profile(String name) {
        this.name = name;
        prefs = new Preferences();
    }

    @Override
    public String toString() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        saveProfiles(PROFILES_FILE);
        for (ProfileListener l : listeners) {
            l.listChanged(ProfileListener.ListChangeEvent.RENAME);
        }
    }

    public Preferences getPreferences() {
        return prefs.clone();
    }

    public void setPreferences(Preferences prefs) {
        this.prefs = prefs;
        saveProfiles(PROFILES_FILE);
        for (ProfileListener l : listeners) {
            l.preferenceChanged(prefs.clone());
        }
    }

    public Archievement getArchievement(Archievement.ArchievementName name) {
        for (Archievement a : this.archievements) {
            if (a.NAME == name) {
                return a;
            }
        }
        return null;
    }

    public static Profile addProfile(String name) {
        Profile neu = new Profile(name);
        allProfiles.addFirst(neu);
        if (currentProfile == null) {
            currentProfile = allProfiles.getFirst();
        }
        saveProfiles(PROFILES_FILE);
        for (ProfileListener l : listeners) {
            l.listChanged(ProfileListener.ListChangeEvent.ADD);
        }
        return neu;
    }

    public static boolean removeProfile(Profile p) {
        if (allProfiles.remove(p)) {
            saveProfiles(PROFILES_FILE);
            if (p.equals(currentProfile)) {
                if (allProfiles.size() > 0) {
                    switchProfile(allProfiles.getFirst());
                } else {
                    currentProfile = null;
                }
            }
            for (ProfileListener l : listeners) {
                l.listChanged(ProfileListener.ListChangeEvent.REMOVE);
            }
            return true;
        }
        return false;
    }

    public static Profile getCurrentProfile() {
        return currentProfile;
    }

    public static boolean switchProfile(Profile otherProfile) {
        if (otherProfile != null && !otherProfile.equals(currentProfile)) {
            currentProfile = otherProfile;
            for (ProfileListener l : listeners) {
                l.profileSwitched(currentProfile);
            }
            for (ProfileListener l : listeners) {
                l.preferenceChanged(currentProfile.prefs.clone());
            }
            return true;
        }
        return false;
    }

    public static Profile[] getProfiles() {
        Profile[] profiles = new Profile[allProfiles.size()];
        for (int i = 0; i < profiles.length; i++) {
            profiles[i] = allProfiles.get(i);
        }
        return profiles;
    }

    public static void addProfileListener(ProfileListener l) {
        listeners.add(l);
    }

    public static boolean removeProfileListener(ProfileListener l) {
        return listeners.remove(l);
    }

    public static void saveProfiles(File f) {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(allProfiles);
            fos.close();
            oos.close();
        } catch (Exception ex) {
            System.err.println("failed to save Profiles");
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean loadProfiles(File f) {
        try {
            allProfiles = new java.util.LinkedList<Profile>();
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            allProfiles = ((java.util.LinkedList<Profile>) ois.readObject());
            fis.close();
            ois.close();
            return true;
        } catch (Exception ex) {
            System.err.println("Cannot load Profiles");
            ex.printStackTrace();
            return false;
        }
    }
}
