package map.cutscenes;

import meshes.dim2.Sprite;
import window.Window;

public class SuccessCutscene extends Cutscene {

	@Override
	public void init() {
		Window.INSTANCE.sleepBar.hide();;
		Window.INSTANCE.button_left.hide();
		Window.INSTANCE.button_right.hide();
	}

	@Override
	public Sprite getCurrentSprite() {
		return null;
	}
}
