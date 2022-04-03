package map.cutscenes;

import gameobjects.entities.Player;
import maths.MathUtils;
import meshes.dim2.Sprite;
import window.Window;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicCutscene extends Cutscene {
	private float originX;

	private long randomCooldown = 5000;

	@Override
	public void init() {
		originX = getGoal();
	}

	@Override
	public void update(long dt) {
		super.update(dt);

		if(!activated) return;
		randomCooldown -= dt;

		System.out.println(randomCooldown);
		if(randomCooldown < 0) {
			String text;

			double random = Math.random();
			if(random < 1 / 6.0) text = "This song is a banger";
			else if(random < 2 / 6.0) text = "This lyrics are deep";
			else if(random < 3 / 6.0) text = "I saw this band live";
			else if(random < 4 / 6.0) text = "Nobody should see me dance like this";
			else if(random < 5 / 6.0) text = "I had an excellent dancing teacher";
			else text = "This is getting exhausting";

			dialogueTextBox.clear(50, 1000).addText(text).build();
			randomCooldown = (long) MathUtils.random(3000, 10000);
		}

		if(timeRunning <= 3500) {
			setStage(1);
		}

		else if(timeRunning < 7500) {
			setStage(2);
		}

		else if(timeRunning < 15000) {
			setStage(3);
		}

		if(stageChanged) {
			if(stage == 1) {
				dialogueTextBox.clear(50, 1000).addText("Lets dance!").build();
			}

			if(stage == 2) {
				Window.INSTANCE.map.discs.setSprite(new Sprite("discs_used"));
				setX(getGoal() - 1f);
			}
		}

		stageChanged = false;
	}

	@Override
	public void deactivate() {
		super.deactivate();
		setX(originX);
		Window.INSTANCE.map.discs.setSprite(new Sprite("discs"));
		randomCooldown = 5000;
	}

	@Override
	public Sprite getCurrentSprite() {
		if(stage >= 2) {
			return Player.dancing;
		}

		return null;
	}
}
