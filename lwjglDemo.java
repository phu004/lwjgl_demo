
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector3f;

import engine.InputHandler;
import engine.Loader;
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
		
		StaticShader shader = new StaticShader();
		Renderer.init(shader);
		
		 
		RawModel model = OBJLoader.loadObjModel("dragon");
		
		
		ModelTexture texture = new ModelTexture(Loader.loadTexture("white"));
		TexturedModel staticModel = new TexturedModel(model, texture);
		 
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-50), 0,0,0,1);
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,0.2f,0.2f));
		
		Camera camera = new Camera();
		 
		while ( !glfwWindowShouldClose(window) ) {
			InputHandler.processInput(); 
			
			entity.increaseRotation(0,1,0);
			camera.move();
			Renderer.prepare();
			shader.start();
			 
			Renderer.render(entity, shader, camera, light);
			shader.stop();
			
			
			
			glfwSwapBuffers(window); // swap the color buffers
		}
		shader.cleanUp();
		Loader.cleanUp();
	}

	public static void main(String[] args) {
		
		new lwjglDemo().run();
	}

}