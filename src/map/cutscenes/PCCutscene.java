package map.cutscenes;

import gameobjects.entities.Player;
import maths.MathUtils;
import meshes.dim2.Sprite;
import window.Window;

import java.util.List;

public class PCCutscene extends Cutscene {
	private float originX;

	private long randomCooldown = 5000;
	private List<String> randomQuotes_1;
	private List<String> randomQuotes_2;

	@Override
	public void init() {
		originX = getGoal();
		randomQuotes_1 = List.of(
				"Oh a new episode of my favorite podcast <BrainGain>",
				"Which bread are you? I have to find out",
				"Lets play a round of <Gang of Geniuses>",
				"What is snooker?",
				"First blood nice",
				"GG EZ",
				"Why is everyone plaing <Elden Bracelett>?",
				"I got enough time for a quick Harmony call",
				"This streamer just wants money"
		);

		randomQuotes_2 = List.of("I havent add text boxes yet",
				"This bug is annoying",
				"What were the final themes again?",
				"I could copy this from an older project",
				"How do i add to string again?",
				"Maybe i should to RealEngine6",
				"I wonder if got enough time for audio",
				"The theme is gonna be bad anyway",
				"Playing 20 games is take sooo long",
				"Has the voting stopped already?",
				"Maybe i should tweak this value",
				"What is a coyote time?",
				"I will just make a point and click",
				"Doors Update? Now?"
				);
	}

	@Override
	public void update(long dt) {
		super.update(dt);

		if(!activated) return;
		randomCooldown -= dt;

		int[] time = Window.INSTANCE.map.clock.toTime();

		if(randomCooldown < 0) {
			String text = "";

			List<String> quotes = time[0] > 24? randomQuotes_2: randomQuotes_1;

			double random = Math.random();
			for(int i = 0; i < quotes.size(); i++) {
				if(random < (i+1) / (float) quotes.size() && text.length() == 0) text = quotes.get(i);
			}

			dialogueTextBox.clear(50, 1000).addText(text).build();
			randomCooldown = (long) MathUtils.random(3000, 10000);
		}

		if(timeRunning <= 1500) {
			setStage(1);
		}

		else if(timeRunning < 5500) {
			setStage(2);
		}

		else if(timeRunning < 15000) {
			setStage(3);
		}

		if(stageChanged) {
			if(stage == 1) {
				String text = "";
				if(time[0] >= 24) text = "I could start working on my engine";
				else text = "Lets waste some time";

				dialogueTextBox.clear(50, 1000).addText(text).build();
			}

			if(stage == 2) {
				Window.INSTANCE.map.player.hide();
				Window.INSTANCE.map.chair.setSprite(new Sprite("chair_used"));
			}
		}

		stageChanged = false;
	}

	@Override
	public void deactivate() {
		super.deactivate();
		setX(originX);
		Window.INSTANCE.map.player.unhide();
		Window.INSTANCE.map.chair.setSprite(new Sprite("chair"));
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

