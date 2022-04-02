package gameobjects.input_provider;

import maths.SmoothFloat;
import utils.ControllableAction;

public class ExternInputProvider implements InputProvider {

	private boolean moveLeft = false,
					moveRight = false;

	@Override
	public boolean moveLeft() {
		return moveLeft;
	}

	@Override
	public boolean moveRight() {
		return moveRight;
	}

	@Override
	public boolean moveUp() {
		return ControllableAction.MOVE_LEFT.anyPressed();
	}

	@Override
	public boolean moveDown() {
		return ControllableAction.MOVE_RIGHT.anyPressed();
	}

	@Override
	public boolean moveForward() {
		return false;
	}

	@Override
	public boolean moveBackward() {
		return false;
	}

	@Override
	public boolean turnLeft() {
		return false;
	}

	@Override
	public boolean turnRight() {
		return false;
	}

	@Override
	public boolean turnUp() {
		return false;
	}

	@Override
	public boolean turnDown() {
		return false;
	}

	public void setMoveLeft(boolean left) {
		moveLeft = left;
	}

	public void setMoveRight(boolean moveRight) {
		this.moveRight = moveRight;
	}
}
