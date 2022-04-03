package map.cutscenes;

import gameobjects.entities.Player;
import meshes.dim2.Sprite;
import window.Window;

public class CoffeCutscene extends Cutscene {

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
		} else if(timeRunning <= 4400) {
			setStage(3);
		} else {
			setStage(0);
		}

		if(stageChanged) {
			if(stage == 1) {
				String text = "A delicous expresso";

				dialogueTextBox.clear(50, 1500).addText(text).build();
			}

			if(stage == 2) {
				Window.INSTANCE.map.coffee.setSprite(new Sprite("coffee_machine_used"));
			}

			if(stage == 3) {
				Window.INSTANCE.map.coffee.setSprite(new Sprite("coffee_machine"));
			}
		}

		stageChanged = false;
		}

	@Override
	public void deactivate() {
		super.deactivate();
		Window.INSTANCE.map.coffee.setSprite(new Sprite("coffee_machine"));
	}

	@Override
	public Sprite getCurrentSprite() {
		if(stage >= 3) {
			return Player.drink;
		}

		return Player.idle;
	}

}