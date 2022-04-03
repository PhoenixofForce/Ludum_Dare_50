package map.cutscenes;

import gameobjects.entities.Player;
import maths.MathUtils;
import meshes.dim2.Sprite;
import window.Window;

import java.util.List;

public class MusicCutscene extends Cutscene {
	private float originX;

	private long randomCooldown = 5000;
	private List<String> texts;
	private List<String> startTexts;

	@Override
	public void init() {
		originX = getGoal();
		texts = List.of(
				"This song is a banger",
				"I saw this band live",
				"Nobody should see me dance like this",
				"I had an excellent dancing teacher",
				"This is getting exhausting",
				"This lyrics are deep",
				"Ive got rythm in my blood"
		);

		startTexts = List.of(
				"Lets get noodlin",
				"Lets dance",
				"I hope i dont trip",
				"Havent danced in while",
				"Maybe some loud music can help"
		);
	}

	@Override
	public void update(long dt) {
		super.update(dt);

		if(!activated) return;

		randomCooldown -= dt;

		if(randomCooldown < 0) {
			String text = "";

			double random = Math.random();
			for(int i = 0; i < texts.size(); i++) {
				if(random < (i+1) / (float) texts.size() && text.length() == 0) text = texts.get(i);
			}

			dialogueTextBox.clear(50, 1500).addText(text).build();
			randomCooldown = (long) MathUtils.random(3000, 10000);
		}

		if(timeRunning <= 3500) {
			setStage(1);
		}

		else if(timeRunning < 3600) {
			setStage(2);
		}

		else {
			float tired = (float) ((float) (dt/ 30000.0 ) / timesDone - timeRunning / 35000000.0);
			Window.INSTANCE.map.player.addTiredness(-tired);
		}

		if(stageChanged) {
			if(stage == 1) {
				String text = "";

				double random = Math.random();
				for(int i = 0; i < startTexts.size(); i++) {
					if(random < (i+1) / (float) startTexts.size() && text.length() == 0) text = startTexts.get(i);
				}

				dialogueTextBox.clear(50, 1500).addText(text).build();
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
