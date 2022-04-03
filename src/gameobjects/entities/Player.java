package gameobjects.entities;

import gameobjects.Entity;
import gameobjects.component_system.components.PlayerControlComponent;
import gameobjects.component_system.components.PositionComponent;
import gameobjects.component_system.components.rendering.SpriteRenderComponent;
import map.cutscenes.Cutscene;
import maths.Easing;
import maths.MathUtils;
import meshes.dim2.Sprite;
import meshes.loader.ObjHandler;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import window.Window;

public class Player extends Entity {

	public static final Sprite walk = new Sprite(100, "player_walk", 3),
			idle = new Sprite("player_idle_0"),
			juggling = new Sprite(100, "player_juggle", 9),
			reading = new Sprite("player_book"),
			sitting = new Sprite("player_tv"),
			dancing = new Sprite(100, "player_dance", 10),
			splishSplash = new Sprite(100, "player_wash", 2),
			drink = new Sprite(100, "player_drink_0", "player_drink_1", "player_drink_1", "player_drink_2", "player_drink_2", "player_drink_1", "player_drink_1", "player_drink_2", "player_drink_2", "player_drink_3", "player_drink_4"),
			eat = new Sprite(100, "player_eat", 13),
			handy = new Sprite("player_handy"),
			fall = new Sprite(300, "player_fall", 3),
			sleep = new Sprite(100, "player_sleep", 8)
			;

	private float x, y;
	private SpriteRenderComponent src;
	private PlayerControlComponent controller;
	private boolean hidden = true;

	private float tiredness = 0.0f;

	private int coffCount = 0;
	private long coffStart;
	private long coffDuration = 30000;

	public Player(float x, float y) {
		this.x = x;
		this.y = y;
		super.init();
	}

	@Override
	protected void addComponents() {
		src = new SpriteRenderComponent(this, ObjHandler.getModel("plane"), new Sprite("player_idle_0"));
		controller = new PlayerControlComponent(this);

		this.addComponent(new PositionComponent(this, new Vector3f(-3, y, x), new Vector3f(0,(float) Math.toRadians(90), 0), new Vector3f(1, 0.3f * 3, 0.3f * 3)));
		this.addComponent(src);
		this.addComponent(controller);
	}

	@Override
	public void update(long dt) {
		super.update(dt);
		coffStart -= dt;
		if(coffStart < - coffDuration) coffCount = 0;

		addTiredness(dt / 120000.0f);
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

	public void flip(boolean flipped) {
		src.flip(flipped);
	}

	public void addTiredness(double v) {
		int hours = Window.INSTANCE.map.clock.toTime()[0];
		float playerBonus = hours > 26? 2: 1;

		if(coffCount > 0) {
			float factor = (float) (MathUtils.map(coffStart, -coffDuration, coffDuration, 1, 2) * Math.log(coffCount + 1));
			if(coffDuration < 0 && v > 0) v *= factor / 2f;	//if the negatative effect is happening, increase the negative effects
			if(coffDuration > 0 && v < 0) v*= factor;
		}
		if(v > 0) v /= playerBonus;
		if(!Window.INSTANCE.map.done) this.tiredness = (float) Math.max(0, tiredness + v);
	}

	public float getTiredness() {
		return (float) Easing.easeOut(Math.min(1, tiredness));
	}

	public void addCoffine() {
		if(coffCount == 0) {
			coffStart = coffDuration;
		}

		coffCount++;
	}
}
