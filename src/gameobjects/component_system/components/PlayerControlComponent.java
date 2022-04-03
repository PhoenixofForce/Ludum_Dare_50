package gameobjects.component_system.components;

import gameobjects.component_system.Component;
import gameobjects.component_system.components.rendering.SpriteRenderComponent;
import gameobjects.entities.Player;
import map.cutscenes.Cutscene;
import map.cutscenes.OpeningCutscene;
import meshes.dim2.Sprite;
import org.joml.Vector3f;
import window.Window;

public class PlayerControlComponent extends Component {

	private static final float maxXDistance = 0.3f;

	private Player p;
	private PositionComponent position;
	private Cutscene currentScene;

	public PlayerControlComponent(Player e) {
		super(e);
		this.p = e;
		this.currentScene = new OpeningCutscene(e);
	}

	@Override
	public boolean init() {
		if(p.hasComponent(PositionComponent.class)) {
			position = p.getComponent(PositionComponent.class).get();
		} else return false;

		currentScene.setX(position.getPosition().z);

		return true;
	}

	@Override
	public void update(long dt) {
		float dts = dt / 1000.0f;

		if(currentScene == null) {
			p.setSprite(Player.idle);
			return;
		}

		currentScene.update(dt);

		if(currentScene == null) {
			p.setSprite(Player.idle);
			return;
		}

		if(!Window.INSTANCE.map.done) {
			if(Math.abs(currentScene.getGoal() - position.getPosition().z) >= maxXDistance) {
				float dir = Math.signum(currentScene.getGoal() - position.getPosition().z);
				p.flip(dir > 0);

				position.add(new Vector3f(0, 0, dir * dts));

				p.setSprite(Player.walk);
				return;
			}
		}

		currentScene.activate();

		Sprite sprite = currentScene.getCurrentSprite();
		p.setSprite(sprite == null? Player.idle: sprite);

	}

	public void setCurrentScene(Cutscene scene) {
		if(this.currentScene != null && currentScene != scene) {
			this.currentScene.deactivate();
		}
		this.currentScene = scene;
	}
}
