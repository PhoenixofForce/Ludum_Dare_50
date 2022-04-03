package map;

import gameobjects.Entity;
import gameobjects.entities.Basic2DEntity;
import gameobjects.entities.Clock;
import gameobjects.entities.Player;
import map.cutscenes.*;
import maths.MathUtils;
import org.joml.Matrix4f;
import utils.GameLoopObject;
import window.Window;
import window.inputs.InputHandler;

import java.util.ArrayList;
import java.util.List;

public class GameMap implements GameLoopObject {

	private List<Basic2DEntity> entities;
	public Player player;

	public Basic2DEntity juggleBalls;
	public Basic2DEntity chair;
	public Basic2DEntity bookcase;
	public Basic2DEntity discs;

	public Clock clock;

	public GameMap() {
		entities = new ArrayList<>();
	}

	@Override
	public void init() {
		clock = new Clock();

		for(int x = 0; x < 16; x++) {
			entities.add(new Basic2DEntity(-x * 0.5f, 0, "floor"));

			if(x < 4) {
				entities.add(new Basic2DEntity(-x * 0.5f, 0.5f, "floor_kitchen"));
				entities.add(new Basic2DEntity(-x * 0.5f, 1f, "kitchen"));
				entities.add(new Basic2DEntity(-x * 0.5f, 1.5f, "kitchen"));
			} else {
				int random_1 = (int) MathUtils.random(0, 4);
				int random_2 = (int) MathUtils.random(0, 4);
				int random_3 = (int) MathUtils.random(0, 4);

				entities.add(new Basic2DEntity(-x * 0.5f, 0.5f, "floor_living_" + random_1));
				entities.add(new Basic2DEntity(-x * 0.5f, 1f, "living_" + random_2));
				entities.add(new Basic2DEntity(-x * 0.5f, 1.5f, "living_" + random_3));
			}
		}

		entities.add(new Basic2DEntity(-1.5f, 0.5f, "fridge", true));

		entities.add(new Basic2DEntity(-1.5f, 1f, "clock", new ClockCutscene()));

		entities.add(new Basic2DEntity(-2.5f, 0.75f, 2, 2, "pc", true));

		chair = new Basic2DEntity(-2.5f, 0.75f, 1, 2, "chair_used", false);
		entities.add(chair);

		bookcase = new Basic2DEntity(-3.3f, 1f, 1, 3, "books", new BookCutscene());
		entities.add(bookcase);

		discs = new Basic2DEntity(-5.5f, 0.5f, "discs", new MusicCutscene());
		entities.add(discs);


		entities.add(new Basic2DEntity(-6.25f, 0.75f, 2, 2, "tv", new TvCutscene()));

		this.juggleBalls = new Basic2DEntity(-7f, 0.4f, "juggle", new JuggleCutscene());
		entities.add(juggleBalls);

		player = new Player(-2.25f, 0.25f);
	}

	@Override
	public void update(long dt) {
		clock.update(dt);

		float[] mouse = Window.INSTANCE.translateToMapSpace(InputHandler.mouseX, InputHandler.mouseY);
		entities.forEach(e -> e.hoverOver(mouse[0], mouse[1]));

		entities.forEach(e -> e.update(dt));
		player.update(dt);
	}

	@Override
	public void render(Matrix4f projectionMatrix, Matrix4f viewMatrix) {
		entities.forEach(e -> e.render(projectionMatrix, viewMatrix));
		player.render(projectionMatrix, viewMatrix);
	}

	@Override
	public void cleanUp() {
		entities.forEach(Entity::cleanUp);
	}

	public void handleClick(float x, float y) {
		Cutscene scene = null;
		for(Basic2DEntity e: entities) {
			if(e.handleClick(x, y)) {
				scene = e.getScene();
			}
		}

		player.setScene(scene);
	}
}
