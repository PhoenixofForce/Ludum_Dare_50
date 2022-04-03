package map.cutscenes;

import gameobjects.entities.Player;
import meshes.dim2.Sprite;
import window.Window;

public class FridgeCutscene extends Cutscene {

	@Override
	public void init() {

	}

	@Override
	public void update(long dt) {
		super.update(dt);

		if(!activated) return;

		if(timeRunning <= 3000) {
			setStage(1);
		} else if(timeRunning <= 4000) {
			setStage(2);
		} else if(timeRunning <= 4000 + Player.eat.getTotalTime()) {
			setStage(3);
		} else {
			setStage(0);
		}

		if(stageChanged) {
			if(stage == 1) {
				String text = "I wonder if the light stays on when the door closes";
				dialogueTextBox.clear(50, 1500).addText(text).build();
			}

			if(stage == 2) {
				Window.INSTANCE.map.fridge.setSprite(new Sprite("fridge_used"));
			}

			if(stage == 3) {
				Window.INSTANCE.map.fridge.setSprite(new Sprite("fridge"));
			}
		}

		stageChanged = false;
	}

	@Override
	public void activate() {
		if(!activated) {
			float percantage = 0.12f / (timesDone);
			Player p = Window.INSTANCE.map.player;
			p.addTiredness(-percantage * p.getTiredness());
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		super.deactivate();
		Window.INSTANCE.map.fridge.setSprite(new Sprite("fridge"));
	}

	@Override
	public Sprite getCurrentSprite() {
		if(stage >= 3) {
			return Player.eat;
		}

		return Player.idle;
	}
}