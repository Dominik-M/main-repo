package dame;


public enum Team {

	SCHWARZ,
	WEIS;

	public Team getOpposite() {
		if (this == SCHWARZ) {
			return WEIS;
		} else {
			return SCHWARZ;
		}
	}
}
