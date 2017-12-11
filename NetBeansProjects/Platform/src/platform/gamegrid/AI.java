package platform.gamegrid;

import platform.utils.SerializableReflectObject;

public abstract class AI extends SerializableReflectObject {

	private static final long serialVersionUID = -5844154815782727243L;

	public abstract void preAct(Actor me);

	@Override
	public String toString() {
		return "DummyAI";
	}
}
