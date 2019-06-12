
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector3f;

import engine.InputHandler;
import engine.Loader;
import engine.MasterRenderer;
import engine.OBJLoader;
import engine.Window;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import java.util.Random;

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

	
		
	
		MasterRenderer.init();
		 
		TexturedModel tree = new TexturedModel(OBJLoader.loadObjModel("tree"), new ModelTexture(Loader.loadTexture("tree")));
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel"), new ModelTexture(Loader.loadTexture("grassTexture")));
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern"), new ModelTexture(Loader.loadTexture("fern")));
		
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLightning(true);
		fern.getTexture().setHasTransparency(true);
		
		Entity[] entities = new Entity[1500];
		Random random = new Random();
		for(int i = 0; i < 1500; i+=3) {
			entities[i] = new Entity(tree, new Vector3f(random.nextFloat()*800 -400, 0, random.nextFloat() * -600), 0,0,0,3);
			entities[i+1] = new Entity(grass, new Vector3f(random.nextFloat()*800 -400, 0, random.nextFloat() * -600), 0,0,0,1);
			entities[i+2] = new Entity(fern, new Vector3f(random.nextFloat()*800 -400, 0, random.nextFloat() * -600), 0,0,0,1);
		}
		
		Light light = new Light(new Vector3f(3000,2000,2000), new Vector3f(1f,1f,1f));
		
		
		
		Terrain terrain = new Terrain(-1,-1, new ModelTexture(Loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(0,-1, new ModelTexture(Loader.loadTexture("grass")));
		
		Camera camera = new Camera();
		
		double previousTime = glfwGetTime();
		int frameCount = 0;
		
		 
		while ( !glfwWindowShouldClose(window) ) {
			InputHandler.processInput(); 
			
			
			camera.move();
			
			MasterRenderer.processTerrain(terrain);
			MasterRenderer.processTerrain(terrain2);
			
			for(int i = 0; i < entities.length; i++) {
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
		        //System.out.println(frameCount);

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