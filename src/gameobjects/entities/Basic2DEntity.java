package gameobjects.entities;

import gameobjects.Entity;
import gameobjects.component_system.components.PositionComponent;
import gameobjects.component_system.components.rendering.ModelRenderComponent;
import meshes.loader.ObjHandler;
import org.joml.Vector3f;

public class Basic2DEntity extends Entity {

	private float x, y;
	private String texture;

	public Basic2DEntity(float x, float y, String texture) {
		this.x = x;
		this.y = y;
		this.texture = texture;

		super.init();
	}

	@Override
	protected void addComponents() {
		this.addComponent(new PositionComponent(this, new Vector3f(-3, y, x), new Vector3f(0,(float) Math.toRadians(90), 0), 0.25f));
		this.addComponent(new ModelRenderComponent(this, ObjHandler.getModel("plane"), texture));
	}
}
