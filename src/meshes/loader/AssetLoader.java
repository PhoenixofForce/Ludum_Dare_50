package meshes.loader;

import java.util.Optional;

public class AssetLoader {

	public static void loadAll() {
		loadTextures();
		loadModels();
	}

	public static void loadTextures() {
		TextureHandler.loadPixelTextureAtlasPNG("textures", "textures", Optional.empty());
		TextureHandler.loadPixelTextureAtlasPNG("Font", "Font", Optional.empty());
	}

	public static void loadModels() {
		//TODO:
		ObjHandler.loadOBJWithoutTexture("plane");
	}

	public static void unloadAll() {
		TextureHandler.cleanUp();
		ObjHandler.cleanUp();
	}
}
