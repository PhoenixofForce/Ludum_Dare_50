package gameobjects.component_system.components.rendering;

import meshes.dim2.TextureAtlas;
import meshes.loader.TextureHandler;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import gameobjects.Entity;
import gameobjects.component_system.components.RenderingComponent;
import rendering.Renderable;
import rendering.Renderer;
import rendering.ShaderHandler;
import rendering.Uniform;

public class ModelRenderComponent extends RenderingComponent {

	private static final Matrix4f transformation = new Matrix4f(
			1, 0, 0, 0,
			0, 1, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1);

	private Uniform uniform;
	private Renderable model;
	private String texture;

	public ModelRenderComponent(Entity e, Renderable model, String texture) {
		super(e);
		this.model = model;
		this.texture = texture;

		uniform = new Uniform();
	}

	@Override
	public void render(Matrix4f projectionMatrix, Matrix4f viewMatrix) {
		TextureAtlas atlas = TextureHandler.getAtlas("textures");

		uniform.setMatrices(projectionMatrix, viewMatrix, pc != null? pc.transformationMatrix(): transformation);
		uniform.setVector3fs(new Vector3f(1, 0, 1));
		uniform.setTextures(atlas.getTexture());
		uniform.setVector4fs(atlas.getTextureBounds(texture));
		uniform.setFloats(0);

		Renderer.render(ShaderHandler.ShaderType.DEFAULT, model, uniform);
	}

}
