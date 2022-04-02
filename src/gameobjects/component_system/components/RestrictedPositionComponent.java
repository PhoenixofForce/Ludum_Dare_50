package gameobjects.component_system.components;

import gameobjects.Entity;
import maths.MathUtils;
import org.joml.Vector3f;

public class RestrictedPositionComponent extends PositionComponent {

	private float xMin, xMax;

	public RestrictedPositionComponent(Entity e) {
		super(e);
	}

	public RestrictedPositionComponent(Entity e, float scale) {
		super(e, scale);
	}

	public RestrictedPositionComponent(Entity e, Vector3f position) {
		super(e, position);
	}

	public RestrictedPositionComponent(Entity e, Vector3f position, float scale) {
		super(e, position, scale);
	}

	public RestrictedPositionComponent(Entity e, Vector3f position, Vector3f rotation) {
		super(e, position, rotation);
	}

	public RestrictedPositionComponent(Entity e, Vector3f position, Vector3f rotation, float scale) {
		super(e, position, rotation, scale);
	}

	public RestrictedPositionComponent(Entity e, Vector3f position, Vector3f rotation, Vector3f scale) {
		super(e, position, rotation, scale);
	}

	@Override
	public void update(long dt) {
		super.update(dt);
		enforceRestriction();
	}

	@Override
	public void add(Vector3f toAdd) {
		position.add(toAdd);
		enforceRestriction();
	}

	private void enforceRestriction() {
		position.x = (float) MathUtils.clamp(xMin, position.x, xMax);
	}

	public RestrictedPositionComponent setXRestriction(float xMin, float xMax) {
		this.xMax = xMax;
		this.xMin = xMin;

		return this;
	}
}
