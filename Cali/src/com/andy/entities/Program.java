package com.andy.entities;

public class Program {

	private String goal;
	private String intensity;

	public Program(String goal, String intensity) {

		this.goal = goal;
		this.intensity = intensity;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getIntensity() {
		return intensity;
	}

	public void setIntensity(String intensity) {
		this.intensity = intensity;
	}
}
