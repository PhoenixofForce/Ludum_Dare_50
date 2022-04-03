package map.cutscenes;

import gameobjects.entities.Player;
import meshes.dim2.Sprite;
import window.Window;

public class SinkCutscene extends Cutscene {

	@Override
	public void init() {

	}

	@Override
	public void update(long dt) {
		super.update(dt);

		if(!activated) return;

		if(timeRunning <= 3000) {
			setStage(1);
		} else if(timeRunning <= 4500) {
			setStage(2);
		} else {
			setStage(0);
		}

		if(stageChanged) {
			if(stage == 1) {
				String text = "I am going to splash some water in my face";

				dialogueTextBox.clear(50, 1500).addText(text).build();
			}

			if(stage == 2) {
				Window.INSTANCE.map.sink.setSprite(new Sprite("sink_used"));
			}
		}

		stageChanged = false;
	}

	@Override
	public void deactivate() {
		super.deactivate();
		Window.INSTANCE.map.sink.setSprite(new Sprite("sink"));
	}

	@Override
	public void activate() {
		if(!activated) {
			Window.INSTANCE.map.player.addTiredness(-0.15f / (timesDone * 0.5 + 0.5));
		}
		super.activate();
	}

	@Override
	public Sprite getCurrentSprite() {
		if(stage >= 2) {
			return Player.splishSplash;
		}

		return Player.idle;
	}
}