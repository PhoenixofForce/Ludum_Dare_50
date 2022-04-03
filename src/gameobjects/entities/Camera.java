package gameobjects.entities;

import gameobjects.Entity;
import gameobjects.component_system.components.LookingComponent;
import gameobjects.component_system.components.MovementComponent;
import gameobjects.component_system.components.PositionComponent;
import gameobjects.component_system.components.RestrictedPositionComponent;
import gameobjects.input_provider.ExternInputProvider;
import org.joml.Vector3f;

public class Camera extends Entity {

	private ExternInputProvider provider;
	public Camera() {
		provider = new ExternInputProvider();
		super.init();
	}

	@Override
	protected void addComponents() {
		this.addComponent(new RestrictedPositionComponent(this, new Vector3f(-2.5f, 0.75f, 0.75f)).setXRestriction(-6f, -1));
		this.addComponent(new LookingComponent(this, new Vector3f(0, 0, 1)));
		this.addComponent(new MovementComponent(this, provider));
	}

	public Vector3f getPosition() {
		return getComponent(PositionComponent.class).get().getPosition();
	}

	public Vector3f getLookingDirection() {
		return getComponent(LookingComponent.class).get().getLookingDirection();
	}

	public Vector3f getUp() {
		return getComponent(LookingComponent.class).get().getUpAxis();
	}

	public ExternInputProvider getInputProvider() {
		return provider;
	}
}
