package snake;

public interface ProfileListener {
	public void listChanged(ListChangeEvent evt);

	public void profileSwitched(Profile current);

	public void preferenceChanged(Profile.Preferences prefs);

	public void archievementUnlocked(Profile.Archievement a);

	public enum ListChangeEvent {
		REMOVE, ADD, RENAME;
	}
}
