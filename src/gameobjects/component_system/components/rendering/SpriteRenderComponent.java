package gameobjects.component_system.components.rendering;

import gameobjects.Entity;
import gameobjects.component_system.components.RenderingComponent;
import meshes.dim2.Sprite;
import meshes.dim2.TextureAtlas;
import meshes.loader.TextureHandler;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import rendering.Renderable;
import rendering.Renderer;
import rendering.ShaderHandler;
import rendering.Uniform;
import utils.TimeUtils;

public class SpriteRenderComponent extends RenderingComponent {

	private static final Matrix4f transformation = new Matrix4f(
			1, 0, 0, 0,
			0, 1, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1);

	private Uniform uniform;
	private Renderable model;
	private boolean hovered;
	private Sprite sprite;

	private long start;

	public SpriteRenderComponent(Entity e, Renderable model, Sprite sprite) {
		super(e);
		this.model = model;
		this.sprite = sprite;
		start = TimeUtils.getTime();

		uniform = new Uniform();
	}

	@Override
	public void render(Matrix4f projectionMatrix, Matrix4f viewMatrix) {
		TextureAtlas atlas = TextureHandler.getAtlas("textures");

		uniform.setMatrices(projectionMatrix, viewMatrix, pc != null? pc.transformationMatrix(): transformation);
		uniform.setVector3fs(new Vector3f(1, 0, 1));
		uniform.setTextures(atlas.getTexture());
		uniform.setVector4fs(atlas.getTextureBounds(sprite.getTexture(start)));
		uniform.setFloats(0, hovered? 1: 0);

		Renderer.render(ShaderHandler.ShaderType.DEFAULT, model, uniform);
	}

	public void setSprite(Sprite sprite) {
		if(this.sprite != sprite) {
			this.sprite = sprite;
			this.start = TimeUtils.getTime();
		}
	}

	public void setHovered(boolean b) {
		this.hovered = b;
	}
}
