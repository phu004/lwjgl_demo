package engine;

import static org.lwjgl.opengl.GL46.*;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import shaders.ShaderProgram;
import shaders.StaticShader;
import toolbox.Maths;

public class Renderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private static Matrix4f projectionMatrix;
	
	public static void init(StaticShader shader) {
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public static void prepare() {
		glEnable(GL_DEPTH_TEST);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

	}
	
	public static void render(Entity entity, StaticShader shader, Camera camera, Light light) {
		TexturedModel texturedModel = entity.getModel();
		RawModel rawModel = texturedModel.getRawModel();
		glBindVertexArray(rawModel.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadLight(light);
		shader.loadViewMatrix(camera);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturedModel.getTexture().getID());
		glDrawElements(GL_TRIANGLES, rawModel.getVertexCount(),  GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}
	
	private static void createProjectionMatrix() {
		float aspectRatio = (float)Window.width/(float)Window.height;
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
