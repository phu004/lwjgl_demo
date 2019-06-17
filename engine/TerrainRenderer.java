package engine;

import static org.lwjgl.opengl.GL46.*;

import java.util.List;

import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import shaders.TerrainShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexturePack;
import toolbox.Maths;

public class TerrainRenderer {
	private static TerrainShader shader;
	
	public static void init(TerrainShader shader, Matrix4f projectionMatrix) {
		TerrainRenderer.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}
	
	public static void render(List<Terrain> terrains) {
		for(Terrain terrain:terrains) {
			prepareTerrain(terrain);
			loadModelMatrix(terrain);
			glDrawElements(GL_TRIANGLES, terrain.getModel().getVertexCount(),  GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
			
		}
	}
	
	private static void prepareTerrain(Terrain terrain) {
		RawModel rawModel = terrain.getModel();
		glBindVertexArray(rawModel.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		shader.loadShineVariable(1,0);
		bindTextures(terrain);
	}
	
	private static void bindTextures(Terrain terrain) {
		TerrainTexturePack texturePack = terrain.getTexturePack();
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturePack.getBackgroundTexture().getTextureID());
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, texturePack.getrTexture().getTextureID());
		glActiveTexture(GL_TEXTURE2);
		glBindTexture(GL_TEXTURE_2D, texturePack.getgTexture().getTextureID());
		glActiveTexture(GL_TEXTURE3);
		glBindTexture(GL_TEXTURE_2D, texturePack.getbTexture().getTextureID());
		glActiveTexture(GL_TEXTURE4);
		glBindTexture(GL_TEXTURE_2D, terrain.getBlendMap().getTextureID());
		
	}
	
	private static void unbindTexturedModel() {
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}
	
	private static void loadModelMatrix(Terrain terrain) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 0,0,0, 1);
		shader.loadTransformationMatrix(transformationMatrix);
	}
	
}
