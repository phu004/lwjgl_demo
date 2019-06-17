package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

public class OBJLoader {

		public static RawModel loadObjModel(String fileName) {
			ModelData data = OBJFileLoader.loadOBJ(fileName);
			return Loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		}
		
	
}
