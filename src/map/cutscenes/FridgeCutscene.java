package map.cutscenes;

import gameobjects.entities.Player;
import meshes.dim2.Sprite;
import window.Window;

import java.util.ArrayList;
import java.util.List;

public class FridgeCutscene extends Cutscene {

	private List<String> texts;

	@Override
	public void init() {
		texts = new ArrayList<>(List.of(
				"I wonder if the light stays on when the door closes",
				"Oh a nice old cheese",
				"I could have sworn i looked in here two minutes ago",
				"Ew this has gone bad",
				"I need to go grocery shopping"
		));
	}

	@Override
	public void update(long dt) {
		super.update(dt);

		if(!activated) return;

		 if(timeRunning <= 2000) {
			setStage(2);
		} else if(timeRunning <= 2000 + Player.eat.getTotalTime()) {
			setStage(3);
		} else {
			setStage(0);
		}

		if(stageChanged) {

			if(stage == 2) {
				String text = "";

				double random = Math.random();
				for(int i = 0; i < texts.size(); i++) {
					if(random < (i+1) / (float) texts.size() && text.length() == 0) text = texts.get(i);
				}

				dialogueTextBox.clear(50, 1500).addText(text).build();
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
			float percantage = 0.12f / (timesDone + 1);
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