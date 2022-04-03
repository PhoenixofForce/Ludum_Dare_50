package map.cutscenes;

import meshes.dim2.Sprite;
import org.joml.Matrix4f;
import utils.GameLoopObject;
import utils.TimeUtils;
import window.Window;
import window.gui.GuiText;

public abstract class Cutscene implements GameLoopObject {

	protected int timesDone;

	private float x;

	protected boolean activated = false;
	protected long timeRunning;

	protected int stage = 0;
	protected boolean stageChanged = false;

	protected GuiText dialogueTextBox = Window.INSTANCE.dialogueTextBox;

	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void render(Matrix4f projectionMatrix, Matrix4f viewMatrix) { }

	@Override
	public void update(long dt) {
		timeRunning += dt;
	}

	@Override
	public void cleanUp() { }

	public abstract Sprite getCurrentSprite();

	public float getGoal() {
		return x;
	}

	public void activate() {
		if(!activated) {
			timeRunning = 0;
			this.activated = true;
			timesDone++;
		}
	}

	protected void setStage(int stage) {
		if(this.stage != stage) {
			this.stage = stage;
			this.stageChanged = true;

			if(stage == 0) {
				Window.INSTANCE.map.player.setScene(null);
				deactivate();
			}
		}
	}

	public void deactivate() {
		this.activated = false;
	}
}
