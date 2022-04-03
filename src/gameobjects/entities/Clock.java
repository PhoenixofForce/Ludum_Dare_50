package gameobjects.entities;

import gameobjects.Entity;

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
		String hours = (timeParts[0] % 24) + "";
		if(hours.length() == 1) hours = "0" + hours;

		String minutes = timeParts[1] + "";
		if(minutes.length() == 1) minutes = "0" + minutes;

		return  hours + ":" + minutes;
	}

}
