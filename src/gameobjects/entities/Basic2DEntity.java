package gameobjects.entities;

import gameobjects.Entity;
import gameobjects.component_system.components.PositionComponent;
import gameobjects.component_system.components.RenderingComponent;
import gameobjects.component_system.components.rendering.ModelRenderComponent;
import meshes.loader.ObjHandler;
import org.joml.Vector3f;
import utils.Constants;

public class Basic2DEntity extends Entity {

	private float x, y;
	private String texture;
	private boolean interactable;
	private boolean hoveredOver;

	private int w, h;

	private ModelRenderComponent mrc;

	public Basic2DEntity(float x, float y, String texture) {
		this(x, y, 1, 1, texture, false);
	}

	public Basic2DEntity(float x, float y, String texture, boolean interactable) {
		this(x, y, 1, 1, texture, interactable);
	}

	public Basic2DEntity(float x, float y, int w, int h, String texture, boolean interactable) {
		this.interactable = interactable;
		this.x = x;
		this.y = y;
		this.texture = texture;
		this.w = w;
		this.h = h;

		super.init();
	}

	@Override
	protected void addComponents() {
		mrc = new ModelRenderComponent(this, ObjHandler.getModel("plane"), texture);

		this.addComponent(new PositionComponent(this, new Vector3f(-3, y, x), new Vector3f(0,(float) Math.toRadians(90), 0), new Vector3f(1, 0.25f * h, 0.25f * w)));
		this.addComponent(mrc);
	}

	public void hoverOver(float mx, float my) {
		if(interactable && containsPoint(mx, my)) {
			hoveredOver = true;
		} else {
			hoveredOver = false;
		}

		mrc.setHovered(hoveredOver);
	}

	public boolean containsPoint(float cx, float cy) {
		if(interactable && cx >= x - 0.25 * w && cx < x + 0.25 * w &&
			cy >= y - 0.25 * h && cy < y + 0.25 * h) {
			return true;
		}

		return false;
	}
}
