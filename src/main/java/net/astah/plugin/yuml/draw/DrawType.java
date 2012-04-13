package net.astah.plugin.yuml.draw;

public enum DrawType {
	BORING("nofunky"), PLAIN("plain"), SCRUFFY("scruffy");

	private String text;
	
	DrawType(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
