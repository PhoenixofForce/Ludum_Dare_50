package map.cutscenes;

import gameobjects.entities.Basic2DEntity;
import gameobjects.entities.Player;
import maths.Easing;
import meshes.dim2.Sprite;
import window.Window;

import java.util.ArrayList;
import java.util.List;

public class JuggleCutscene extends Cutscene {

	private List<String> texts;

	@Override
	public void init() {
		texts = new ArrayList<>(List.of(
				"Juggling? Again?",
				"I am going to be the champion soon",
				"Lets throw some balls",
				"I hope they dont fall on my head this time",
				"I could get some more balls",
				"Balls."
		));
	}

	@Override
	public void update(long dt) {
		super.update(dt);

		if(!activated) return;
		float tired = (float) (getTirednessFactor() * (dt / 6500.0) / (Math.abs(timesDone - 4) + 1));
		if(timesDone > 4) tired /= 15;
		else if(tired > 0) tired *= 1.5f;
		Window.INSTANCE.map.player.addTiredness(-tired);

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

				if(timesDone == 1) text = "Oh boi, i havent juggled in a long time!";
				else if(timesDone == 4) text = "I guess some more training cant hurt";
				else {
					double random = Math.random();
					for(int i = 0; i < texts.size(); i++) {
						if(random < (i+1) / (float) texts.size() && text.length() == 0) text = texts.get(i);
					}
				}

				dialogueTextBox.clear(50, 1500).addText(text).build();
			} else if(stage == 2) {
				Window.INSTANCE.map.juggleBalls.hide();
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
		Window.INSTANCE.map.juggleBalls.unhide();
	}

	@Override
	public Sprite getCurrentSprite() {
		if(stage == 2) {
			return Player.juggling;
		}


		return Player.idle;
	}

	public float getTirednessFactor() {
		if(timeRunning < 3500 || timeRunning >=  3500 + timesDone * 500L) return 0;
		float bookProgress = (timeRunning - 3500.0f) / (timesDone * 500);
		return (float)  (1.0f - Easing.ease(0.5f, -1.85f, bookProgress));
	}

}
