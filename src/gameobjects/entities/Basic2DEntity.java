package gameobjects.entities;

import gameobjects.Entity;
import gameobjects.component_system.components.PositionComponent;
import gameobjects.component_system.components.RenderingComponent;
import gameobjects.component_system.components.rendering.ModelRenderComponent;
import gameobjects.component_system.components.rendering.SpriteRenderComponent;
import jdk.jfr.Percentage;
import map.cutscenes.Cutscene;
import meshes.dim2.Sprite;
import meshes.loader.ObjHandler;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import utils.Constants;

public class Basic2DEntity extends Entity {

	private float x, y;
	private Sprite sprite;

	private Cutscene scene;
	private boolean interactable;
	private boolean hoveredOver;

	private boolean hidden = false;

	private int w, h;
	private SpriteRenderComponent src;

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
		this.sprite = new Sprite(texture);
		this.w = w;
		this.h = h;

		super.init();
	}

	public Basic2DEntity(float x, float y, String texture, Cutscene scene) {
		this(x, y, 1, 1, texture, scene);
	}

	public Basic2DEntity(float x, float y, int w, int h, String texture, Cutscene scene) {
		scene.setX(x);
		this.interactable = true;
		this.scene = scene;
		this.x = x;
		this.y = y;
		this.sprite = new Sprite(texture);
		this.w = w;
		this.h = h;

		init();
	}

	@Override
	public void init() {
		super.init();
		if(scene != null) scene.init();
	}


	@Override
	protected void addComponents() {
		src = new SpriteRenderComponent(this, ObjHandler.getModel("plane"), sprite);

		this.addComponent(new PositionComponent(this, new Vector3f(-3, y, x), new Vector3f(0,(float) Math.toRadians(90), 0), new Vector3f(1, 0.25f * h, 0.25f * w)));
		this.addComponent(src);
	}

	public void hoverOver(float mx, float my) {
		if(interactable && containsPoint(mx, my)) {
			hoveredOver = true;
		} else {
			hoveredOver = false;
		}

		src.setHovered(hoveredOver);
	}

	public Cutscene getScene() {
		return scene;
	}

	public boolean handleClick(float cx, float cy) {
		if(scene != null) {
			if(containsPoint(cx, cy)) {
				return true;
			}
		}

		return false;
	}

	public boolean containsPoint(float cx, float cy) {
		if(!hidden && interactable && cx >= x - 0.25 * w && cx < x + 0.25 * w &&
			cy >= y - 0.25 * h && cy < y + 0.25 * h) {
			return true;
		}

		return false;
	}

	@Override
	public void render(Matrix4f m1, Matrix4f m2) {
		if(!hidden) {
			super.render(m1, m2);
		}
	}

	public void hide() {
		hidden = true;
	}

	public void unhide() {
		hidden = false;
	}

	public void setSprite(Sprite sprite) {
		src.setSprite(sprite);
	}
}
