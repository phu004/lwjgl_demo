package engine;

import static org.lwjgl.glfw.GLFW.*;

public class InputHandler {

	public static char[] inputBuffer = new char[256];
	public static char[] keyReleaseBuffer = new char[256];	
	public static int inputCounter, inputBufferIndex, keyReleaseCounter, keyReleaseBufferIndex;
	public static boolean leftKeyPressed, rightKeyPressed, upKeyPressed, downKeyPressed, moveUp, moveDown, scrollUp, scrollDown;
	public static float yScrollOffset;
	
	public static void init(long window) {
		glfwSetInputMode(window, GLFW_LOCK_KEY_MODS, GLFW_TRUE);

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (myWindow, key, scancode, actions, mods) -> {
			if (actions == GLFW_PRESS || actions == GLFW_REPEAT) {
				
				handleKeyPress(getASCII(key, mods));
			}
			
			
			if (actions == GLFW_RELEASE) {
				handleKeyRelease(getASCII(key, mods));
			}
			
		});
		
		glfwSetScrollCallback(window, (myWindow, xoffset, yoffset) ->{
			yScrollOffset = (float)yoffset;
		});
	}
	
	public static char getASCII(int key, int mods) {

		boolean shiftPressed = false;
		if((mods&0xf) == 1)
			shiftPressed = true;
		
		boolean capLockEnabled = false;
		if(((mods >> 4)&0xf) == 1)
			capLockEnabled = true;
		
		char c = (char)key;
		
		//convert letters TO ASCII
		if(key >= 65 && key <=90) {
			if(!shiftPressed && !capLockEnabled) {
				c =  (char) (key+32);
			}
		}else {
			//convert none letters to ASCII
			if(shiftPressed) {	
				switch ((char)key) {
				  case '`': c = '~'; break;
				  case '1': c = '!'; break;
				  case '2': c = '@'; break;
				  case '3': c = '#'; break;
				  case '4': c = '$'; break;
				  case '5': c = '%'; break;
				  case '6': c = '^'; break;
				  case '7': c = '&'; break;
				  case '8': c = '*'; break;
				  case '9': c = '('; break;
				  case '0': c = ')'; break;
				  case '-': c = '_'; break;
				  case '=': c = '+'; break;
				  case '[': c = '{'; break;
				  case ']': c = '}'; break;
				  case ';': c = ':'; break;
				  case '\'': c = '"'; break;
				  case '\\': c = '|'; break;
				  case ',': c = '<'; break;
				  case '.': c = '>'; break;
				  case '/': c = '?'; break;
				}
			}
		}
		
		return c;
	}
	
	public static void handleKeyPress(char c){
		inputBuffer[inputCounter] = c;
		inputCounter++;
		if(inputCounter == inputBuffer.length)
			inputCounter = 0;
		
	}
	
	public static void handleKeyRelease(char c){
		keyReleaseBuffer[keyReleaseCounter] = c;
		keyReleaseCounter++;
		if(keyReleaseCounter == keyReleaseBuffer.length)
			keyReleaseCounter = 0;
	}
	
	public static void processInput() {
		// Poll for window events. The key callback  will only be
		// invoked during this call.
		glfwPollEvents();
		
		
		//read input char
		int theCounter = inputCounter;  
		while(inputBufferIndex < theCounter){
			char c = inputBuffer[inputBufferIndex];
			
			if(c == 265){
				upKeyPressed = true;
			}
			if(c == 264) {
				downKeyPressed = true;
			}
			if(c == 263) {
				leftKeyPressed = true;
			}
			if(c == 262) {
				rightKeyPressed = true;
			}
			if(c == 'a') {
				moveUp = true;
			}
			if(c == 'z') {
				moveDown = true;
			}
			
			inputBufferIndex++;
		}
		
		//clear key input buffer
		inputBufferIndex = 0;
		inputCounter = 0;
		
		//read released char
		theCounter = keyReleaseCounter;
		while(keyReleaseBufferIndex < theCounter){
			char c = keyReleaseBuffer[keyReleaseBufferIndex];
			
			if(c == 265){
				upKeyPressed = false;
			}
			if(c == 264) {
				downKeyPressed = false;
			}
			if(c == 263) {
				leftKeyPressed = false;
			}
			if(c == 262) {
				rightKeyPressed = false;
			}
			if(c == 'a') {
				moveUp = false;
			}
			if(c == 'z') {
				moveDown = false;
			}
			keyReleaseBufferIndex++;
		}
		
		//clear key release buffer
		keyReleaseBufferIndex = 0;
		keyReleaseCounter = 0;
		
		
		if(yScrollOffset!=0) {
			if(yScrollOffset > 0)
				scrollUp = true;
			if(yScrollOffset < 0)
				scrollDown = true;
			yScrollOffset = 0;
		}else {
			scrollUp = false;
			scrollDown = false;
		}
		
		//reset scroll offset
		if(yScrollOffset !=0)
			yScrollOffset = 0;
		
		
	}
}
