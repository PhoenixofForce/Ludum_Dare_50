package gameobjects.entities;

import gameobjects.Entity;

import java.util.Arrays;

public class Clock extends Entity {

	private long time;

	@Override
	protected void addComponents() {}

	@Override
	public void update(long dt) {
		time += dt;
	}

	public int[] toTime() {
		int minutes = (int) Math.floor(time / 500.0);
		int hours = (int) (Math.floor(minutes / 60.0));
		minutes -= hours * 60;

		return new int[]{hours + 20, minutes};
	}

	public String timeString() {
		int[] timeParts = toTime();
		return (timeParts[0] % 24) + ":" + timeParts[1];
	}

}
