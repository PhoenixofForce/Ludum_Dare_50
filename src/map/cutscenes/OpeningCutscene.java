package map.cutscenes;

import gameobjects.entities.Basic2DEntity;
import gameobjects.entities.Player;
import maths.MathUtils;
import meshes.dim2.Sprite;
import org.joml.Vector3f;
import window.Window;

public class OpeningCutscene extends Cutscene {

	private Basic2DEntity chair;
	private Player player;

	public OpeningCutscene(Player p) {
		this.chair = Window.INSTANCE.map.chair;
		this.player = p;
	}


	@Override
	public void init() {}

	@Override
	public void update(long dt) {
		super.update(dt);

		if(!activated) return;

		if(timeRunning <= 3500) {
			chair.setSprite(new Sprite("chair_used"));
			player.hide();
			setStage(1);
		}
		else if(timeRunning <= 5500) {
			setStage(2);
		} else {
			setStage(0);
		}


		if(stageChanged) {
			if(stage == 1) {
				dialogueTextBox.clear(50, 1000)
						.addText("Its just 7 Hours until the next ")
						.addText("Dudum ", MathUtils.vecFromColor(246, 146, 64), 0.05f)
						.addText("Lare ", MathUtils.vecFromColor(237, 87, 58), 0.05f)
						.addText("starts")
						.build();
			}

			if(stage == 2) {
				dialogueTextBox.clear(50, 1000)
						.addText("I better stay awake so i can start right away!").build();
			}
		}

		stageChanged = false;
	}

	@Override
	public void deactivate() {
		super.deactivate();
		chair.setSprite(new Sprite("chair"));
		player.unhide();
	}

	@Override
	public Sprite getCurrentSprite() {
		return Player.idle;
	}
}
