package map.cutscenes;

import gameobjects.entities.Player;
import maths.MathUtils;
import meshes.dim2.Sprite;
import window.Window;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TvCutscene extends Cutscene {

	private float originX;
	private List<String> shows;

	private Sprite tv = new Sprite(5000, "tv_anim", 14);

	private long randomCooldown = 7500;
	private List<String> randomQuotes_1;
	private List<String> startTexts;

	@Override
	public void init() {
		originX = getGoal();
		shows = new ArrayList<>(List.of(
				"Who wants to have 10 Dollars",
				"How i met my grandpa. another one?",
				"Weather with Pukicha",
				"How to make a game online (fast)",
				"Nothing good, i just gonna watch Schnetzflix",
				"Just some news",
				"Oh i know this movie",
				"Isnt this the movie where the main character dies?"
		));

		randomQuotes_1 = List.of(
				"I would have known that",
				"ITS A",
				"ITS C",
				"Ads? Not Again",
				"Why is there no sound?",
				"Where is the remote?",
				"100 Degrees? Thats sooo hot",
				"I loved this as a child",
				"My grandpa can play better than you",
				"I could use a new tv",
				"Oh this is already out?"
		);

		startTexts = List.of(
				"Lets see what the tv has to offer",
				"I thought i had a gaming console",
				"Lets turn on this bad boy",
				"Quiet dusty isnt it",
				"I have no choice but to turn on the tv",
				"I need a bigger one",
				"Where is the power button"
		);

		Collections.shuffle(shows);
	}

	@Override
	public void update(long dt) {
		super.update(dt);

		if(!activated) return;

		randomCooldown -= dt;
		if(randomCooldown < 0) {
			String text = "";

			List<String> quotes = randomQuotes_1;

			double random = Math.random();
			for(int i = 0; i < quotes.size(); i++) {
				if(random < (i+1) / (float) quotes.size() && text.length() == 0) text = quotes.get(i);
			}

			dialogueTextBox.clear(50, 1000).addText(text).build();
			randomCooldown = (long) MathUtils.random(3000, 10000);
		}

		float tvInterest = (float) Math.sin(timeRunning / 1000.0f) * 0.5f + 0.1f;
		float tired = (float) ((tvInterest * (dt + Math.random() / 1000.0)) / 10000.0 / timesDone);
		Window.INSTANCE.map.player.addTiredness(-tired / 2);

		if(timeRunning <= 3500) {
			setStage(1);
		}

		else if(timeRunning < 5500) {
			setStage(2);
		}

		else if(timeRunning < 5500 + 20000) {
			setStage(3);
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
				setX(getGoal() - 0.5f);
				String bookTitle = "Oh a game show";

				bookTitle = shows.remove(0);
				shows.add(bookTitle);

				dialogueTextBox.clear(50, 1000).addText(bookTitle).build();
				Window.INSTANCE.map.tv.setSprite(tv);
			}
		}

		stageChanged = false;
	}

	@Override
	public void deactivate() {
		super.deactivate();
		setX(originX);
		Window.INSTANCE.map.tv.setSprite(new Sprite("tv"));
	}


	@Override
	public Sprite getCurrentSprite() {
		if(stage == 3) {
			return Player.sitting;
		}

		return null;
	}
}
