
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import engine.InputHandler;
import engine.Loader;
import engine.MasterRenderer;
import engine.OBJLoader;
import engine.Window;
import entities.Camera;
import entities.Entity;
import entities.Light;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
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
			
		TexturedModel tree = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree"), new ModelTexture(Loader.loadTexture("lowPolyTree")));
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel"), new ModelTexture(Loader.loadTexture("grassTexture")));
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern"), new ModelTexture(Loader.loadTexture("fern")));
		
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLightning(true);
		grass.getTexture().setNumberOfRows(4);
		fern.getTexture().setHasTransparency(true);
		fern.getTexture().setNumberOfRows(2);
		
		TerrainTexture backgroundTexture = new TerrainTexture(Loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(Loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(Loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(Loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(Loader.loadTexture("blendMap"));
		
		
		Terrain terrain = new Terrain(-1,-1, texturePack, blendMap, "heightmap");
		Terrain terrain2 = new Terrain(0,-1, texturePack, blendMap, "heightmap");
		
		
		Entity[] entities = new Entity[1500];
		Random random = new Random();
		for(int i = 0; i < 1500; i+=3) {
			entities[i] = new Entity(tree, 0.6f);
			entities[i].adjustPosition(terrain, terrain2);
			entities[i+1] = new Entity(grass, random.nextInt(9),1);
			entities[i + 1].adjustPosition(terrain, terrain2);
			entities[i+2] = new Entity(fern, random.nextInt(4), 1);
			entities[i + 2].adjustPosition(terrain, terrain2);
		}
		
		Light light = new Light(new Vector3f(3000,6000,2000), new Vector3f(1f,1f,1f));		
		Camera camera = new Camera();
		
		while ( !glfwWindowShouldClose(window) ) {
			InputHandler.processInput(); 
			
			camera.move();
			
			MasterRenderer.processTerrain(terrain);
			MasterRenderer.processTerrain(terrain2);
			
			for(int i = 0; i < entities.length; i++) {
				MasterRenderer.processEntity(entities[i]);
			}
			
			MasterRenderer.render(light, camera);
			Window.updateDisplay(window);
			
		}
		MasterRenderer.cleanUp();
		Loader.cleanUp();
	}

	public static void main(String[] args) {
		
		new lwjglDemo().run();
	}

}