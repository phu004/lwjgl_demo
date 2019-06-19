package entities;

import org.lwjgl.util.vector.Vector3f;

import engine.InputHandler;
import engine.Window;

public class Camera {

	private Vector3f position = new Vector3f(100,5,0);
	private float pitch;
	private float yaw;
	private float roll;
	
	private static final float TURN_SPEED = 60;
	private static final float MOVE_SPEED = 20;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	
	public Camera() {
		
	}

	public void move() {
		if(InputHandler.upKeyPressed) {
			currentSpeed = MOVE_SPEED;
		}else if(InputHandler.downKeyPressed) {
			currentSpeed = -MOVE_SPEED;
		}else {
			currentSpeed = 0;
		}
		
		if(InputHandler.rightKeyPressed) {
			currentTurnSpeed = TURN_SPEED;
		}else if(InputHandler.leftKeyPressed) {
			currentTurnSpeed = -TURN_SPEED;
		}else {
			currentTurnSpeed = 0;
		}
		
		float frameTimeSeconds = Window.getFrameTimeSeconds();
		
		if(InputHandler.moveUp) {
			position.y+=5f*frameTimeSeconds;
		}
		if(InputHandler.moveDown) {
			position.y-=5f*frameTimeSeconds;
		}
		
		
		
		yaw+=currentTurnSpeed*frameTimeSeconds;
		
		position.x+= currentSpeed*frameTimeSeconds*Math.sin(Math.toRadians(yaw));
		position.z-= currentSpeed*frameTimeSeconds*Math.cos(Math.toRadians(yaw));
		
		
		
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
