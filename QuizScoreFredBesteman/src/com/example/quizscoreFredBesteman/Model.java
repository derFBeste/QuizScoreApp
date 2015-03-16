/**
 * Fred Besteman
 * CIS 2818
 * Jackson
 * 
 * Application 3: QuizScoreYourName
 * Model.java
 */



package com.example.quizscoreFredBesteman;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Model {
	Question [] quizQues = {
			new Question("Is this a valid Java identifer?\n@TheStart\n\n\n", false), 
			new Question("Is this a valid Java identifer?\nTax4State\n\n\n", true),
			new Question("Will this code compile?\ninterface A { void run(); }\nclass B implements A { }\n\n", false),
			new Question("Will this code compile?\ninterface A { void run(); }\nabstract class B implements A { }\n\n", true),
			new Question("Will this code compile?\ninterface A { void run(); }\nclass B implements A {\n   public void run() {} \n}", true),
			new Question("Will this code compile?\nclass X { private int x; }\nclass Y extends X { }\n\n", true),
			new Question("Will this code compile?\nclass X { private int x; }\nclass Y extends X {\n   public Y() { x = 10; }\n", false),
			new Question("Will this code compile?\nclass X { int x; }\nclass Y extends X {\n   public Y() { x = 10; }\n", true),
			new Question("Will this code compile?\ninterface A { void run(); }\nclass B implements A {\n   void run() {} \n}", false)
	};
	
	ArrayList<Question> questions;
	
	private int current;
	private int numQuestions;
	private int score = 0;
	private int questionCount;
	
	public double startTime;
	
	public Model() {  
		questions = new ArrayList<Question>();
        for (Question q: quizQues)
        	questions.add(q);
        
        reset();
	}
	
	public void reset() {
		for (Question q : questions)
			q.reset();
		
        Collections.shuffle(questions);
        current = 0;
	}
	
	public void setNumQuestions(){
		this.numQuestions = 3;
	}
	
	public void setNumQuestions(int numQuestions){		
		if(this.numQuestions <= questions.size())
			this.numQuestions = numQuestions;
		else
			this.numQuestions = 3;
	}
	
	public int getNumQuestions(){
		return numQuestions;
	}
	
	public String getCurrentQuestionText() {
		return questions.get(current).getText();
	}
	
	public boolean isCurrentQuestionCorrect() {
		return questions.get(current).isCorrect();
	}
	
	public int countCorrectAnswer(){		
		if(isCurrentQuestionCorrect() == true)
			score = score + 1;
		return score;
	}
	
	public int countQuestion(){
		return current + 1;	
	}
	
	public GuessStatus getCurrentQuestionStatus() {
		return questions.get(current).getStatus();
	}
	
	public void nextQuestion() {
		if (current < numQuestions - 1) 
			current++;
		else
			reset();
	}
	
	public void makeGuess(boolean guess) {
		questions.get(current).makeGuess(guess);
	}
	
	public boolean isActive(){
		if(current < numQuestions - 1)
			return true;
		else{
			return false;
		}
	}
	
	public void startTime(){
		startTime = System.currentTimeMillis();
	}
	
	public double elapsedTime(){
		double now = System.currentTimeMillis();
		return (now - startTime) / 1000.0;
	}
		
}
