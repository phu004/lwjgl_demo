package guis;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import engine.Loader;
import models.RawModel;
import toolbox.Maths;

import static org.lwjgl.opengl.GL46.*;

public class GuiRenderer {
	
	private static RawModel quad;
	private static GuiShader shader;
	
	public static void init() {
		float[] positions = {-1,1,-1,-1,1,1,1,-1};
		quad = Loader.loadToVAO(positions);
		shader = new GuiShader();
	}
	
	public static void render(List<GuiTexture> guis) {
		shader.start();
		glBindVertexArray(quad.getVaoID());
		glEnableVertexAttribArray(0);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_DEPTH_TEST);
		for(GuiTexture gui: guis) {
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, gui.getTexture());
			Matrix4f matrix = Maths.createTransformationMatrix(gui.getPositions(), gui.getScales());
			shader.loadTransformation(matrix);
			glDrawArrays(GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		glEnable(GL_DEPTH_TEST);
		glDisable(GL_BLEND);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		shader.stop();
		System.out.println(shader);
	}
	
	public static void cleanUp() {
		
		shader.cleanUp();
	}
}
