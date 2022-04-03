package map.cutscenes;

import gameobjects.entities.Player;
import meshes.dim2.Sprite;
import window.Window;

public class CoffeeCutscene extends Cutscene {

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
		} else if(timeRunning <= 4000 + Player.drink.getTotalTime()) {
			setStage(3);
		} else {
			setStage(0);
		}

		if(stageChanged) {
			if(stage == 1) {
				String text = "A delicous espresso";

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
	public void activate() {
		if(!activated) Window.INSTANCE.map.player.addCoffine();
		super.activate();
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