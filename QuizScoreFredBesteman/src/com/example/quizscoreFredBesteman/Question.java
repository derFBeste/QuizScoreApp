package com.example.quizscoreFredBesteman;

public class Question {
	private String text;
	private boolean correct;	
	private GuessStatus status;
	
	public Question(String text, boolean correct) {
		super();
		this.text = text;
		this.correct = correct;
		this.status = GuessStatus.NO_ANSWER;
	}

	public String getText() {
		return text;
	}

	public boolean isCorrect() {
		return correct;
	}

	public GuessStatus getStatus() {
		return status;
	}
	
	public void makeGuess(boolean guess) {
		if (guess == correct)
			this.status = GuessStatus.RIGHT;
		else
			this.status = GuessStatus.WRONG;
	}
	
	public void reset() {
		this.status = GuessStatus.NO_ANSWER;	
	}
}
