package entities;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import terrains.Terrain;

public class Entity {
	
	
	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	
	private int textureIndex = 0;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public Entity(TexturedModel model, Vector3f position,  float scale) {
		this.model = model;
		this.position = position;
		this.rotX = 0;
		this.rotY = 0;
		this.rotZ = 0;
		this.scale = scale;
	}
	
	public Entity(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.textureIndex = textureIndex;
	}
	
	public Entity(TexturedModel model, float scale) {
		Random random = new Random();
		this.model = model;
		this.position = new Vector3f(random.nextFloat()*800 -400, 0, random.nextFloat() * -600);
		this.rotX = 0;
		this.rotY = random.nextFloat()*360;
		this.rotZ =0;
		this.scale = scale;
	}
	
	public Entity(TexturedModel model,  int textureIndex, float scale) {
		Random random = new Random();
		this.model = model;
		this.position = new Vector3f(random.nextFloat()*800 -400, 0, random.nextFloat() * -600);
		this.rotX = 0;
		this.rotY = random.nextFloat()*360;
		this.rotZ =0;
		this.scale = scale;
		this.textureIndex = textureIndex;
	}
	
	public float getTextureXOffset() {
		int column = textureIndex%model.getTexture().getNumberOfRows();
		return (float)column/(float)model.getTexture().getNumberOfRows();
	}
	
	public float getTextureYOffset() {
		int row = textureIndex/model.getTexture().getNumberOfRows();
		return (float)row/(float)model.getTexture().getNumberOfRows();
	}
	
	public void adjustPosition(Terrain terrain1, Terrain terrain2) {
		if(terrain1.getHeightOfTerrain(position.x, position.z) != -9999999) {
			position.y = terrain1.getHeightOfTerrain(position.x, position.z);
		}else if(terrain2.getHeightOfTerrain(position.x, position.z) != -9999999) {
			position.y = terrain2.getHeightOfTerrain(position.x, position.z);
		}
	}
	
	
	
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x+=dx;
		this.position.y+=dy;
		this.position.z+=dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX+=dx;
		this.rotY+=dy;
		this.rotZ+=dz;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
}
