package guis;

import org.lwjgl.util.vector.Vector2f;

public class GuiTexture {
	private int texture;
	private Vector2f positions;
	private Vector2f scales;
	
	public GuiTexture(int texture, Vector2f positions, Vector2f scales) {
		this.texture = texture;
		this.positions = positions;
		this.scales = scales;
	}

	public int getTexture() {
		return texture;
	}

	public Vector2f getPositions() {
		return positions;
	}

	public Vector2f getScales() {
		return scales;
	}
	
	
}
