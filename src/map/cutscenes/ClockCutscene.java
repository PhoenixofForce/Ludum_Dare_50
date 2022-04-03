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
		Window.INSTANCE.map.player.addTiredness(- dt / 120000.0f / (timesDone));

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

				if(timesDone == 1) {
					text = "Weird, its feels like the time just froze";
				} else if(time[0] < 22) {
					double random = Math.random();
					if(random< 0.33) text = timeStr + "? It feels like hours already";
					else if(random < 0.9) text = "Maybe time runs faster when i throw it out of the window";
					else text = "Weird, its feels like the time runs slower";
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