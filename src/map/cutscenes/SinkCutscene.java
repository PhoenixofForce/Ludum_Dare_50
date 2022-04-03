package map.cutscenes;

import gameobjects.entities.Player;
import meshes.dim2.Sprite;
import window.Window;

import java.util.ArrayList;
import java.util.List;

public class SinkCutscene extends Cutscene {

	private List<String> texts;

	@Override
	public void init() {
		texts = new ArrayList<>(List.of(
				"I am going to splash some water in my face",
				"I need some cold water",
				"Maybe this can wake me up",
				"This is going to be refreshing"
		));
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
				String text = "";

				double random = Math.random();
				for(int i = 0; i < texts.size(); i++) {
					if(random < (i+1) / (float) texts.size() && text.length() == 0) text = texts.get(i);
				}

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