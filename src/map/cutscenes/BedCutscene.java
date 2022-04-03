package map.cutscenes;

import gameobjects.entities.Player;
import meshes.dim2.Sprite;
import window.Window;

public class BedCutscene extends Cutscene {

	@Override
	public void init() { }

	@Override
	public void activate() {
		if(!activated) Window.INSTANCE.map.done = true;
		super.activate();
	}

	@Override
	public void update(long dt) {
		super.update(dt);

		if(!activated) return;

		if(timeRunning <= 1500) {
			setStage(1);
		} else if(timeRunning <= 4000) {
			setStage(2);
		} else if(timeRunning <= 10500) {
			setStage(3);
		} else {
			setStage(4);
		}

		if(stageChanged) {
			if(stage == 1) {
				dialogueTextBox.clear(50, 1000).addText("Nevermind, i will start fresh tomorrow").build();
			}

			if(stage == 2) {
				Window.INSTANCE.sleepBar.hide();;
				Window.INSTANCE.button_left.hide();
				Window.INSTANCE.button_right.hide();
				Window.INSTANCE.map.player.hide();
				Window.INSTANCE.map.bed.setSprite(new Sprite("bed_used"));
			}

			if(stage == 3) {
				dialogueTextBox.clear(50).addText("Thank you for playing. Feel free to try again").build();
			}
		}

		stageChanged = false;
	}

	@Override
	public Sprite getCurrentSprite() {
		return Player.idle;
	}
}
