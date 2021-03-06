package map.cutscenes;

import gameobjects.entities.Player;
import maths.MathUtils;
import meshes.dim2.Sprite;
import window.Window;

public class SuccessCutscene extends Cutscene {

	@Override
	public void init() {
		Window.INSTANCE.sleepBar.hide();;
		Window.INSTANCE.button_left.hide();
		Window.INSTANCE.button_right.hide();
	}

	@Override
	public void update(long dt) {

		super.update(dt);

		if(timeRunning <= 3500) {
			setStage(1);
		} else if(timeRunning <= 8000) {
			setStage(2);
		} else if(timeRunning <= 10500) {
			setStage(3);
		} else {
			setStage(4);
		}

		if(stageChanged) {
			if(stage == 1) {
				dialogueTextBox.clear(50, 1000).addText("Oh already 3am, let me check the theme").build();
			}

			if(stage == 2) {
				dialogueTextBox.clear(50, 1000).addText("Its ").addText("<Delay the inevitable>", MathUtils.vecFromColor(255, 255, 0), 0.02f).addText(" I could make a point an click").build();
			}

			if(stage == 3) {
				dialogueTextBox.clear(50, 1000).addText("I can go to sleep now").build();
			}

			if (stage == 4) {
				dialogueTextBox.clear(50).addText("Thank you for playing").build();
			}
		}

		stageChanged = false;
	}

	@Override
	public Sprite getCurrentSprite() {
		return Player.handy;
	}
}
