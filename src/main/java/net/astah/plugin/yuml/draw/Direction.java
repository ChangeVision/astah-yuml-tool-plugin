package net.astah.plugin.yuml.draw;

public enum Direction {
	LEFT_TO_RIGHT("LR"), TOP_DOWN("TB"), RIGHT_TO_LEFT("RL");
	
	private String text;
	
	Direction(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
