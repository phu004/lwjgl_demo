package entities;

import org.lwjgl.util.vector.Vector3f;

import terrains.Terrain;

public class Light {
	
	private Vector3f position;
	private Vector3f colour;
	private Vector3f attenuation;
	
	public Light(Vector3f position, Vector3f colour) {
		super();
		this.position = position;
		this.colour = colour;
		attenuation = new Vector3f(1,0,0);
	}
	
	public Light(Vector3f position, Vector3f colour, Vector3f attenuation) {
		super();
		this.position = position;
		this.colour = colour;
		this.attenuation = attenuation;
	}
	
	public Vector3f getAttenuation() {
		return attenuation;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public Vector3f getColour() {
		return colour;
	}
	public void setColour(Vector3f colour) {
		this.colour = colour;
	}
	
	public void adjustPositionBasedOnTerrain(Terrain terrain1, Terrain terrain2, float height) {
		if(terrain1.getHeightOfTerrain(position.x, position.z) != -9999999) {
			position.y = terrain1.getHeightOfTerrain(position.x, position.z) + height;
			
			
		}else if(terrain2.getHeightOfTerrain(position.x, position.z) != -9999999) {
			position.y = terrain2.getHeightOfTerrain(position.x, position.z) + height;
		}
	}
	
	
}
