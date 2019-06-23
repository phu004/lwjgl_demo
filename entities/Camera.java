package entities;

import org.lwjgl.util.vector.Vector3f;

import engine.InputHandler;
import engine.Window;

public class Camera {

	private Vector3f position = new Vector3f(100,40,-100);
	private Vector3f centerPosition = new Vector3f(position.x, position.y, position.z);
	private float pitch = 45;
	private float yaw;
	private float roll;
	
	private static final float TURN_SPEED = 60;
	private static final float MOVE_SPEED = 20;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	
	private float distanceFromMiddle = 50;
	
	
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
			centerPosition.y+=5f*frameTimeSeconds;
		}
		if(InputHandler.moveDown) {
			centerPosition.y-=5f*frameTimeSeconds;
		}
		
		yaw+=currentTurnSpeed*frameTimeSeconds;
		
		centerPosition.x+= currentSpeed*frameTimeSeconds*Math.sin(Math.toRadians(yaw));
		centerPosition.z-= currentSpeed*frameTimeSeconds*Math.cos(Math.toRadians(yaw));
		
		calculateZoom();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance,verticalDistance);
		
		
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
	
	private void calculateZoom() {
		float zoomLevel = 0;
		
		if(InputHandler.scrollUp)
			zoomLevel = 2.5f;
		if(InputHandler.scrollDown)
			zoomLevel = -2.5f;
		distanceFromMiddle -=zoomLevel;
	}
	
	private float calculateHorizontalDistance() {
		return (float)(distanceFromMiddle * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance() {
		return (float)(distanceFromMiddle * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		position.set(centerPosition);
		position.y = position.y + verticDistance;
		position.x-= horizDistance*Math.sin(Math.toRadians(yaw));
		position.z+= horizDistance*Math.cos(Math.toRadians(yaw));
	}
	
}
