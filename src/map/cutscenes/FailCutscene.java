package map.cutscenes;

import gameobjects.entities.Player;
import maths.MathUtils;
import meshes.dim2.Sprite;
import window.Window;

public class FailCutscene extends Cutscene {

	@Override
	public void init() {
		Window.INSTANCE.sleepBar.hide();;
		Window.INSTANCE.button_left.hide();
		Window.INSTANCE.button_right.hide();
	}

	@Override
	public void update(long dt) {

		super.update(dt);

		if(timeRunning <= Player.fall.getTotalTime()) {
			setStage(1);
		} else if(timeRunning <= Player.fall.getTotalTime() + 2000) {
			setStage(2);
		} else if(timeRunning <= 10500) {
			setStage(3);
		} else {
			setStage(4);
		}

		if(stageChanged) {
			if(stage == 1) {
				dialogueTextBox.clear(50, 1000).addText("I.. am...so... tired").build();
			}

			if(stage == 2) {
			}

			if(stage == 3) {
				dialogueTextBox.clear(50).addText("Thank you for playing. Feel free to try again\r\n You have made it til " + Window.INSTANCE.map.clock.timeString()).build();
			}
		}

		stageChanged = false;
	}

	@Override
	public Sprite getCurrentSprite() {
		if(stage == 1) return Player.fall;
		else return Player.sleep;
	}
}
