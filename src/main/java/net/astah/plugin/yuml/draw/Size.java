package net.astah.plugin.yuml.draw;

public enum Size {
	TINY(60), SMALL(80), NORMAL(100), BIG(120), HUGE(180);
	
	private int scale;
	
	Size(int scale) {
		this.scale = scale;
	}
	
	public int getScale() {
		return scale;
	}
}
