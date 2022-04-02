package map;

import gameobjects.Entity;
import gameobjects.entities.Basic2DEntity;
import maths.MathUtils;
import org.joml.Matrix4f;
import utils.GameLoopObject;

import java.util.ArrayList;
import java.util.List;

public class GameMap implements GameLoopObject {

	private List<Entity> entities;

	public GameMap() {
		entities = new ArrayList<>();
	}

	@Override
	public void init() {

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

			entities.add(new Basic2DEntity(-1.5f, 0.5f, "fridge"));
		}

	}

	@Override
	public void update(long dt) {
		entities.forEach(e -> e.update(dt));
	}

	@Override
	public void render(Matrix4f projectionMatrix, Matrix4f viewMatrix) {
		entities.forEach(e -> e.render(projectionMatrix, viewMatrix));
	}

	@Override
	public void cleanUp() {
		entities.forEach(Entity::cleanUp);
	}
}
