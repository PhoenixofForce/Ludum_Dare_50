package utils;

import org.joml.Matrix4f;

public interface GameLoopObject {

	void init();
	void update(long dt);
	void render(Matrix4f projectionMatrix, Matrix4f viewMatrix);
	void cleanUp();

}
