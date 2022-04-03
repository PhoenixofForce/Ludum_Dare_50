package map.cutscenes;

import gameobjects.entities.Player;
import meshes.dim2.Sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TvCutscene extends Cutscene {

	private float originX;
	private List<String> shows;

	@Override
	public void init() {
		originX = getGoal();
		shows = new ArrayList<>(List.of(
				"Who wants to have 10 Dollars",
				"How i met my grandpa. another one?",
				"News with Pukicha",
				"How to make a game online (fast)",
				"Nothing good, i just gonna watch Schnetzlix",
				"Just some news",
				"Oh i know this movie",
				"Isnt this the movie where the main character dies?"
		));
		Collections.shuffle(shows);
	}

	@Override
	public void update(long dt) {
		super.update(dt);

		if(!activated) return;

		if(timeRunning <= 3500) {
			setStage(1);
		}

		else if(timeRunning < 5500) {
			setStage(2);
		}

		else if(timeRunning < 5500 + 20000) {
			setStage(3);
		}

		else if(timeRunning < 5500 + 20000 + 1000) {
			setStage(4);
		} else {
			setStage(0);
		}

		if(stageChanged) {
			if(stage == 1) {
				dialogueTextBox.clear(50, 1000).addText("Lets see what tv has to offer").build();
			}

			if(stage == 2) {
				setX(getGoal() - 0.5f);
				String bookTitle = "Oh a game show";
				if(shows.size() > 0) bookTitle = shows.remove(0);
				dialogueTextBox.clear(50, 1000).addText(bookTitle).build();
			}

			if(stage == 4) {
				String bookEndQuote = "That was good";
				if(timesDone > 5) bookEndQuote = "I could have binged my favorite show in that time";

				dialogueTextBox.clear(50, 1000).addText(bookEndQuote).build();
			}
		}

		stageChanged = false;
	}

	@Override
	public void deactivate() {
		super.deactivate();
		setX(originX);
	}


	@Override
	public Sprite getCurrentSprite() {
		if(stage == 3) {
			return Player.sitting;
		}

		return null;
	}
}
