package entities;

import org.lwjgl.util.vector.Vector3f;

import engine.InputHandler;

public class Camera {

	private Vector3f position = new Vector3f(100,10,100);
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera() {
		
	}

	public void move() {
		if(InputHandler.upKeyPressed) {
			position.z-=0.2f;
		}
		if(InputHandler.rightKeyPressed) {
			position.x+=0.2f;
		}
		if(InputHandler.leftKeyPressed) {
			position.x-=0.2f;
		}
		if(InputHandler.downKeyPressed) {
			position.z+=0.2f;
		}
		
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
}
