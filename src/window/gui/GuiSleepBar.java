package window.gui;

import meshes.ScreenRect;
import meshes.dim2.Sprite;
import meshes.dim2.TextureAtlas;
import meshes.loader.TextureHandler;
import org.joml.Matrix4f;
import rendering.Renderer;
import rendering.ShaderHandler;
import rendering.Uniform;
import utils.Constants;
import utils.TimeUtils;
import window.Window;

public class GuiSleepBar extends GuiElement {

	private Sprite animation = new Sprite(200, "bar_anim", 16);
	private long startTime = TimeUtils.getTime();

	public GuiSleepBar(GuiElement parent, Anchor xAnchor, Anchor yAnchor, float xOffset, float yOffset, float width, float height) {
		super(parent, xAnchor, yAnchor, xOffset, yOffset, width, height);
	}

	public GuiSleepBar(GuiElement parent, Anchor[] anchors, float xOffset, float yOffset, float width, float height) {
		super(parent, anchors, xOffset, yOffset, width, height);
	}

	public GuiSleepBar(GuiElement parent, float xOffset, float yOffset, float width, float height) {
		super(parent, xOffset, yOffset, width, height);
	}

	public GuiSleepBar(float xOffset, float yOffset, float width, float height) {
		super(xOffset, yOffset, width, height);
	}

	@Override
	public void updateComponent(long dt) {

	}

	@Override
	public void renderComponent() {
		float translationX = toScreenSpace(getCenterX(), Window.INSTANCE.getWidth());
		float translationY = toScreenSpace(getCenterY(), Window.INSTANCE.getHeight());

		float glWidth = getWidth() / Window.INSTANCE.getWidth();
		float glHeight = getHeight() / Window.INSTANCE.getHeight();

		Matrix4f transformationMatrix = new Matrix4f();
		transformationMatrix.translate(translationX, translationY, 0);
		transformationMatrix.scale(glWidth, glHeight, 1);

		TextureAtlas atlas = TextureHandler.getAtlas("textures");

		Uniform uniform = new Uniform();
		uniform.setMatrices(new Matrix4f(), new Matrix4f(), transformationMatrix);
		uniform.setTextures(atlas.getTexture());
		uniform.setFloats(0, 1, Window.INSTANCE.map.player.getTiredness());
		uniform.setVector4fs(atlas.getTextureBounds(animation.getTexture(startTime, TimeUtils.getTime())), atlas.getTextureBounds("bar_mask"));

		Renderer.renderArrays(ShaderHandler.ShaderType.SLEEPBAR, ScreenRect.getInstance(), uniform);
	}
}
