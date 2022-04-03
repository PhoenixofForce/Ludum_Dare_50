package map.cutscenes;

import gameobjects.entities.Player;
import meshes.dim2.Sprite;
import window.Window;

public class ClockCutscene extends Cutscene {


	public ClockCutscene() {
	}

	@Override
	public void init() {

	}

	@Override
	public void update(long dt) {
		super.update(dt);

		if(!activated) return;

		if(timeRunning <= 3000) {
			setStage(1);
		} else if(timeRunning <= 3500) {
			setStage(2);
		} else {
			setStage(0);
		}

		if(stageChanged) {
			int[] time =  Window.INSTANCE.map.clock.toTime();
			String timeStr = Window.INSTANCE.map.clock.timeString();;
			if(stage == 1) {
				String text = "";

				if(time[0] < 22) {
					if(Math.random()< 0.5) text = timeStr + "? It feels like hours already";
					else text = "Maybe time runs faster when i throw it out of the window";
				} else if(time[0] >= 26) {
					text = "Oh its already " + timeStr + " just a little more";
				} else {
					text = "Why is this day soooo long? Its just " + timeStr;
				}

				dialogueTextBox.clear(50, 1500).addText(text).build();
			}
		}

		stageChanged = false;
	}

	@Override
	public void deactivate() {
		super.deactivate();
	}

	@Override
	public Sprite getCurrentSprite() {
		return Player.idle;
	}

}