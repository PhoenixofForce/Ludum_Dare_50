package map.cutscenes;

import gameobjects.entities.Player;
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

	@Override
	public void init() {
		originX = getGoal();

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
				if(bookQuotes.size() > 0) bookTitle = bookQuotes.remove(0);

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


	@Override
	public Sprite getCurrentSprite() {
		if(stage == 3) {
			return Player.reading;
		}

		return null;
	}
}
