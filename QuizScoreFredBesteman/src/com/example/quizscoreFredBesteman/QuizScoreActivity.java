/**
 * Fred Besteman
 * CIS 2818
 * Jackson
 * 
 * Application 3: QuizScoreYourName
 * 
 * QuizScoreActivity.java
 * This is the main activity.
 */



package com.example.quizscoreFredBesteman;

import java.util.Random;

import com.example.quizme.AboutActivity;
import com.example.quizme.R;
import com.example.quizme.SettingsActivity;
import com.example.quizme.StatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizScoreActivity extends Activity {	
	TextView labelQuestion;
	TextView labelRight;
	TextView labelWrong;
	TextView displayCount;
	RadioButton radioYes;
	RadioButton radioNo;
	Button buttonSubmit;
	Button buttonNext;
	
	Model model;
	int questionQty;
	int score = 0;
	int numberRight;
	SharedPreferences savedValues;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_score_fred_besteman);
        
        model = new Model();        
        labelQuestion = (TextView) findViewById (R.id.labelQuestion);
        displayCount = (TextView) findViewById (R.id.displayCount);
        
        radioYes = (RadioButton) findViewById (R.id.radioYes);
        radioNo = (RadioButton) findViewById (R.id.radioNo);
        
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonNext = (Button) findViewById(R.id.buttonNext);
        
        //The following code is used to pass questionQty to other activities.
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        questionQty = savedValues.getInt("questionQty", 3);
        model.setNumQuestions(questionQty);
        
        model.startTime();    //used to track the quiz length  
        model.reset();
        refreshView();
    }
    
    public void onSubmit(View view) {    	
    	model.makeGuess(radioYes.isChecked());
        if(model.isActive() == false){
        	Toast.makeText(this, "Quiz Finished!", Toast.LENGTH_SHORT).show();
        	buttonSubmit.setEnabled(false);
        	radioYes.setEnabled(false);
        	radioNo.setEnabled(false);
        	
        	//The following code is used to pass the quiz length to other activities.
        	double gameTime = model.elapsedTime();       	
        	Editor editor = savedValues.edit();
        	editor.putFloat("questionTime", (float)gameTime);
        	editor.commit();
        }
    	refreshView();
    }
    
    public void onNext(View view) {
        model.nextQuestion();
    	refreshView();
    }
    
    private void refreshView() {
    	labelQuestion.setText(model.getCurrentQuestionText());
    	
    	//This if block defines behaviour for when a quiz is finished.
    	if (model.getCurrentQuestionStatus() == GuessStatus.NO_ANSWER) {
    		labelQuestion.setBackgroundColor(Color.WHITE);
            buttonSubmit.setEnabled(true);
            buttonNext.setEnabled(false);
            displayCount.setText(model.countQuestion() + " of " + model.getNumQuestions() + " questions");
    	}
    	else{ 
            buttonSubmit.setEnabled(false);
            buttonNext.setEnabled(true);
            //Disables next button when the quiz is finished.
            if(model.isActive() == false)
            	buttonNext.setEnabled(false);
    		if (model.getCurrentQuestionStatus() == GuessStatus.RIGHT){
	    		labelQuestion.setBackgroundColor(Color.GREEN);
	    		score = score + 1;
    	        Editor editor = savedValues.edit();
    	        editor.putInt("questionsCorrect", score);
    	        editor.commit();
    		}
	    	else
	    		labelQuestion.setBackgroundColor(Color.RED);
    	}
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//This switch case sets up behaviour for a new quiz.
		case R.id.new_quiz:
			model.setNumQuestions(questionQty);
			radioYes.setEnabled(true);
			radioNo.setEnabled(true);
			model.reset();
			refreshView();
			model.startTime();
			return true;
		case R.id.about:			
			startActivity(new Intent(getApplicationContext(), AboutActivity.class));
			return true;
		case R.id.statistics:
			startActivity(new Intent(getApplicationContext(), StatActivity.class));
			return true;
		case R.id.settings:
			startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}		
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		MenuItem settingsOption = menu.findItem(R.id.settings);
		MenuItem statsOption = menu.findItem(R.id.statistics);		
		if(model.isActive() == false){
			settingsOption.setEnabled(true);
			statsOption.setEnabled(true);
		}
		else{
			settingsOption.setEnabled(false);
			statsOption.setEnabled(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}
        
}
