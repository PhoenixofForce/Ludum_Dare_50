package map.cutscenes;

import gameobjects.entities.Player;
import maths.Easing;
import maths.MathUtils;
import meshes.dim2.Sprite;
import window.Window;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookCutscene extends Cutscene {

	private float originX;
	private List<String> bookQuotes;

	private int currentBookLength = 20000;

	private long randomCooldown = 7500;
	private List<String> randomQuotes_1;

	@Override
	public void init() {
		originX = getGoal();

		randomQuotes_1 = List.of(
				"I havent seen that coming",
				"I dont like this character",
				"Ohhhhhh",
				"Another chapter done",
				"Ouch i cut my finger",
				"Why is this page torn?",
				"I dont remember reading this",
				"Narrr, i lost focus and have to reread the entire page",
				"A CAAAAAAAT",
				"A movie of this would never be as good",
				"Just one more page"
		);

		bookQuotes = new ArrayList<>(List.of(
				"Here i rather would watch the movie",
				"<Lord of the Moths> should do it",
				"Never finished <Thirst Games>",
				"Oh thats my favorite book",
				"Romance? Must have been a gift",
				"Hated the ending on this one",
				"Way better than the movie",
				"<How to create booktitles for dummies>, havent read that one",
				"<Game of Stools> everyone loves that right?"
		));

		Collections.shuffle(bookQuotes);
	}

	@Override
	public void update(long dt) {
		super.update(dt);

		if(!activated) return;
		float tired = (float) (getTirednessFactor() * (dt / 6500.0) / (timesDone));
		if(tired > 0) tired /= 10;
		Window.INSTANCE.map.player.addTiredness(-tired);


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

		if(timeRunning <= 3500) {
			setStage(1);
		}

		else if(timeRunning < 5500) {
			setStage(2);
		}

		else if(timeRunning < 5500 + currentBookLength) {
			setStage(3);
		}

		else if(timeRunning < 5500 + currentBookLength + 1000) {
			setStage(4);
		} else {
			setStage(0);
		}

		if(stageChanged) {
			if(stage == 1) {
				dialogueTextBox.clear(50, 1000).addText("Hmm, which book should i read?").build();
			}

			if(stage == 2) {
				Window.INSTANCE.map.bookcase.setSprite(new Sprite("books_used"));
				setX(getGoal() - 1f);

				String bookTitle = "Havent read that one";
				bookTitle = bookQuotes.remove(0);
				bookQuotes.add(bookTitle);

				dialogueTextBox.clear(50, 1000).addText(bookTitle).build();
				currentBookLength = (int) MathUtils.random(15000, 30000);
			}

			if(stage == 4) {
				String bookEndQuote = "";
				if(currentBookLength < 170000) bookEndQuote = "That was a short one";
				if(timesDone > 5) bookEndQuote = "How many books can i read in a day? Yes";

				dialogueTextBox.clear(50, 1000).addText(bookEndQuote).build();
			}
		}

		stageChanged = false;
	}

	@Override
	public void deactivate() {
		super.deactivate();
		setX(originX);
		Window.INSTANCE.map.bookcase.setSprite(new Sprite("books"));
		currentBookLength = 20000;
	}

	public float getTirednessFactor() {
		if(timeRunning < 5500 || timeRunning >= 5500 + currentBookLength + 1000) return 0;
		float bookProgress = (timeRunning - 5500.0f) / currentBookLength;
		return (float) Easing.ease(-1.7f, -1.4f, bookProgress);
	}


	@Override
	public Sprite getCurrentSprite() {
		if(stage == 3) {
			return Player.reading;
		}

		return null;
	}
}
