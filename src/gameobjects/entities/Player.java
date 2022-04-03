package gameobjects.entities;

import gameobjects.Entity;
import gameobjects.component_system.components.PlayerControlComponent;
import gameobjects.component_system.components.PositionComponent;
import gameobjects.component_system.components.rendering.SpriteRenderComponent;
import map.cutscenes.Cutscene;
import meshes.dim2.Sprite;
import meshes.loader.ObjHandler;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Player extends Entity {

	public static final Sprite walk = new Sprite(100, "player_walk", 3),
			idle = new Sprite("player_idle_0"),
			juggling = new Sprite(100, "player_juggle", 9),
			reading = new Sprite("player_book");
	;

	private float x, y;
	private SpriteRenderComponent src;
	private PlayerControlComponent controller;
	private boolean hidden = true;

	public Player(float x, float y) {
		this.x = x;
		this.y = y;
		super.init();
	}

	@Override
	protected void addComponents() {
		src = new SpriteRenderComponent(this, ObjHandler.getModel("plane"), new Sprite("player_idle_0"));
		controller = new PlayerControlComponent(this);

		this.addComponent(new PositionComponent(this, new Vector3f(-3, y, x), new Vector3f(0,(float) Math.toRadians(90), 0), new Vector3f(1, 0.25f * 3, 0.25f * 3)));
		this.addComponent(src);
		this.addComponent(controller);
	}

	@Override
	public void render(Matrix4f m1, Matrix4f m2) {
		if(!hidden) {
			super.render(m1, m2);
		}
	}

	public void setSprite(Sprite sprite) {
		src.setSprite(sprite);
	}

	public void setScene(Cutscene scene) {
		controller.setCurrentScene(scene);
	}

	public void hide() {
		this.hidden = true;
	}

	public void unhide() {
		this.hidden = false;
	}
}
