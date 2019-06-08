
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector3f;

import engine.InputHandler;
import engine.Loader;
import engine.MasterRenderer;
import engine.OBJLoader;
import engine.Renderer;
import engine.Window;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import textures.ModelTexture;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


public class lwjglDemo {

	// The window handle
	private long window;

	public void run() {
		
		init();
		loop();

		Window.destory(window);
	}

	private void init() {
		
		window = Window.init(1024,640);
		
		InputHandler.init(window);
	}

	private void loop() {
		
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
	
		MasterRenderer.init();
		 
		RawModel model = OBJLoader.loadObjModel("dragon");
		
		
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(Loader.loadTexture("white")));
		staticModel.getTexture().setShineDamper(10);
		staticModel.getTexture().setReflectivity(3);
		
		
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,0.2f,0.2f));
		
		Entity[] entities = new Entity[2];
		for(int i = 0; i< entities.length; i++) {
			entities[i] = new Entity(staticModel, new Vector3f(-2+i*6f,-5,-80), 0,0,0,1);
		}
		
		
		
		Camera camera = new Camera();
		
		double previousTime = glfwGetTime();
		int frameCount = 0;
		
		 
		while ( !glfwWindowShouldClose(window) ) {
			InputHandler.processInput(); 
			
			
			camera.move();
			
			for(int i = 0; i < entities.length; i++) {
				entities[i].increaseRotation(0,1,0);
			
				MasterRenderer.processEntity(entities[i]);
			}
		
			MasterRenderer.render(light, camera);
			
				
			glfwSwapBuffers(window); // swap the color buffers
			
			
			// Measure speed
		    double currentTime = glfwGetTime();
		    frameCount++;
		    // If a second has passed.
		    if ( currentTime - previousTime >= 1.0 )
		    {
		        // Display the frame count here any way you want.
		        System.out.println(frameCount);

		        frameCount = 0;
		        previousTime = currentTime;
		    }
		}
		MasterRenderer.cleanUp();
		Loader.cleanUp();
	}

	public static void main(String[] args) {
		
		new lwjglDemo().run();
	}

}