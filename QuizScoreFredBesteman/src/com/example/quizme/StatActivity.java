/**
 * Fred Besteman
 * CIS 2818
 * Jackson
 * 
 * Application 3: QuizScoreYourName
 * StatActivity.java
 */


package com.example.quizme;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class StatActivity extends Activity {
	
	TextView numberCorrect;
	TextView percentCorrect;
	TextView totalTime;
	TextView averageTime;
	
	SharedPreferences savedValues;

	int score;
	int questionQty;
	double scorePercent;
	double questionAvg;
	double time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stat);
		
		numberCorrect = (TextView) findViewById(R.id.numberCorrect);
		percentCorrect = (TextView) findViewById(R.id.percentCorrect);
		totalTime = (TextView) findViewById(R.id.totalTime);
		averageTime = (TextView) findViewById(R.id.averageTime);
		
		savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
		
		//Gets shared values and assigns them to variables.
		score = savedValues.getInt("questionsCorrect", 1);
		questionQty = savedValues.getInt("questionQty", 0);
		time = savedValues.getFloat("questionTime", 0);
				
		scorePercent = ((double)score / (double)questionQty) * 100; //calculates the quizes percentage right
		questionAvg = time / (double)questionQty; //calculates the average time per question
		
		DecimalFormat df = new DecimalFormat("####0.00"); //used to format output
		
		numberCorrect.setText(score + " of " + questionQty + " correct");
		percentCorrect.setText(scorePercent + "% correct");
		totalTime.setText(df.format(time) + " total time in seconds");
		averageTime.setText(df.format(questionAvg) + " avg seconds per question");
	}
		
}
