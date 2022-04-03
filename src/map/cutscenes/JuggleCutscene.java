package map.cutscenes;

import gameobjects.entities.Basic2DEntity;
import gameobjects.entities.Player;
import meshes.dim2.Sprite;
import window.Window;

public class JuggleCutscene extends Cutscene {

	private Basic2DEntity juggleBalls;

	public JuggleCutscene() {
		this.juggleBalls = Window.INSTANCE.map.juggleBalls;
	}

	@Override
	public void init() {

	}

	@Override
	public void update(long dt) {
		super.update(dt);

		if(!activated) return;

		int juggleTime = timesDone * 500;
		if(timeRunning <= 3000) {
			setStage(1);
		} else if(timeRunning <= 3500) {
			setStage(2);
		} else if(timeRunning >= 3500 + juggleTime + 500) {
			if(timesDone == 4 && stage != 5) {
				setStage(5);
			} else {
				setStage(0);
			}
		}

		if(stageChanged) {
			if(stage == 1) {
				String text = "";

				if(timesDone <= 2) text = "Oh boi, i havent juggled in a long time!";
				else if(timesDone <= 7) text = "I guess some more training cant hurt";
				else text = "Juggling? Again?";

				dialogueTextBox.clear(50, 1500).addText(text).build();
			} else if(stage == 2) {
				juggleBalls.hide();
			}

			else if(stage == 5) {
				dialogueTextBox.clear(50, 1500).addText("I think i got the drill").build();
			}
		}

		stageChanged = false;
	}

	@Override
	public void deactivate() {
		super.deactivate();
		juggleBalls.unhide();
	}

	@Override
	public Sprite getCurrentSprite() {
		if(stage == 2) {
			return Player.juggling;
		}


		return Player.idle;
	}

}
