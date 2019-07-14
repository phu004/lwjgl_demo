package engine;


import static org.lwjgl.opengl.GL46.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import shaders.StaticShader;
import shaders.TerrainShader;
import terrains.Terrain;

public class MasterRenderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	private static final float RED = 0.529f;
	private static final float GREEN = 0.808f;
	private static final float BLUE = 0.922f;

	
	private static Matrix4f projectionMatrix;
	
	private static StaticShader shader;
	private static TerrainShader terrainShader;
	
	private static Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private static List<Terrain> terrains = new ArrayList<Terrain>();
	
	public static void init() {
		enableCulling();
		createProjectionMatrix();
		shader = new StaticShader();
		terrainShader = new TerrainShader();
		EntityRenderer.init(shader, projectionMatrix);
		TerrainRenderer.init(terrainShader, projectionMatrix);
	}
	
	public static void enableCulling() {
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}
	
	public static void disableCulling() {
		glDisable(GL_CULL_FACE);
	}
	
	public static void render(List<Light> lights, Camera camera) {
		prepare();
		shader.start();
		shader.loadSkyColour(RED, GREEN, BLUE);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		EntityRenderer.render(entities);
		shader.stop();
		
		terrainShader.start();
		terrainShader.loadSkyColour(RED, GREEN, BLUE);
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		TerrainRenderer.render(terrains);
		terrainShader.stop();
		terrains.clear();
		entities.clear();
	}
	
	public static void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}
	
	public static void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch!= null) {
			batch.add(entity);
		}else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	public static void cleanUp() {
		shader.cleanUp();
		terrainShader.cleanUp();
	}
	
	public static void prepare() {
		// Set the clear color
		glClearColor(0.529f, 0.808f, 0.922f, 0.0f);
		
		glEnable(GL_DEPTH_TEST);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
	}
	
	private static void createProjectionMatrix() {
		float aspectRatio = (float)Window.getWidth()/(float)Window.getHeight();
		float y_scale = (float)((1f/Math.tan(Math.toRadians(FOV/2f)))*aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_leanth = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) /frustum_leanth);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_leanth);
		projectionMatrix.m33 = 0;
	}
}
