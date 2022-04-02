package window.gui;

import maths.SmoothFloat;
import meshes.ScreenRect;
import meshes.dim2.TextureAtlas;
import meshes.loader.TextureHandler;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import rendering.Renderer;
import rendering.ShaderHandler;
import rendering.Uniform;
import window.Window;

public class GuiButton extends GuiElement {

	private float widthSave, heightSave;

	private String texture;
	private SmoothFloat size;

	public GuiButton(GuiElement parent, Anchor xAnchor, Anchor yAnchor, float xOffset, float yOffset, float width, float height, String texture) {
		super(parent, xAnchor, yAnchor, xOffset, yOffset, width, height);
		this.widthSave = width;
		this.heightSave = height;
		this.texture = texture;
		this.size = new SmoothFloat(1);
	}

	public GuiButton(GuiElement parent, Anchor[] anchors, float xOffset, float yOffset, float width, float height, String texture) {
		super(parent, anchors, xOffset, yOffset, width, height);
		this.widthSave = width;
		this.heightSave = height;
		this.texture = texture;
		this.size = new SmoothFloat(1);
	}

	public GuiButton(GuiElement parent, float xOffset, float yOffset, float width, float height, String texture) {
		super(parent, xOffset, yOffset, width, height);
		this.widthSave = width;
		this.heightSave = height;
		this.texture = texture;
		this.size = new SmoothFloat(1);
	}

	public GuiButton(float xOffset, float yOffset, float width, float height, String texture) {
		super(xOffset, yOffset, width, height);
		this.widthSave = width;
		this.heightSave = height;
		this.texture = texture;
		this.size = new SmoothFloat(1);
	}

	@Override
	public void renderComponent() {
		if(isMouseEntered() && size.getValue() == 1) {
			this.setRawWidth(widthSave * 1.1f);
			this.setRawHeight(heightSave * 1.1f);
		} else if(!isMouseEntered() && size.getValue() == 1) {
			this.setRawWidth(widthSave);
			this.setRawHeight(heightSave);
		}

		float translationX = toScreenSpace(getCenterX(), Window.INSTANCE.getWidth());
		float translationY = toScreenSpace(getCenterY(), Window.INSTANCE.getHeight());

		float glWidth = getWidth() / Window.INSTANCE.getWidth();
		float glHeight = getHeight() / Window.INSTANCE.getHeight();

		Matrix4f transformationMatrix = new Matrix4f();
		transformationMatrix.translate(translationX, translationY, 0);
		transformationMatrix.scale(glWidth, glHeight, 1);

		TextureAtlas atlas = TextureHandler.getAtlas("textures");
		Vector4f bounds = atlas.getTextureBounds(texture);

		Uniform uniform = new Uniform();
		uniform.setMatrices(new Matrix4f(), new Matrix4f(), transformationMatrix);
		uniform.setTextures(atlas.getTexture());
		uniform.setVector3fs(new Vector3f(1));
		uniform.setVector4fs(bounds);
		uniform.setFloats(1, 0);
		Renderer.renderArrays(ShaderHandler.ShaderType.DEFAULT, ScreenRect.getInstance(), uniform);
	}

	@Override
	public void onClick(int event, int button) {
		super.onClick(event, button);
		if(event == GLFW.GLFW_RELEASE) {
			size.setSmooth(1, 100);
		} else {
			size.set(0.8f);
		}
	}

	@Override
	public void updateComponent(long dt) {
		size.update();
		this.setRawWidth(widthSave * size.getValue());
		this.setRawHeight(heightSave * size.getValue());
	}
}
