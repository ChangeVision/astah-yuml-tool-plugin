package net.astah.plugin.yuml.draw;

public enum UrlType {
	PNG(".png"), PDF(".pdf"), JPEG(".jpg"), JSON(".json"), SVG(".svg");
	
	private String text;
	
	UrlType(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
