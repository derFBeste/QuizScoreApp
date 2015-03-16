/**
 * Fred Besteman
 * CIS 2818
 * Jackson
 * 
 * Application 3: QuizScoreYourName
 * SettingsActivity.java
 */

package com.example.quizme;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;


public class SettingsActivity extends Activity {
	
	EditText numQuestions;
	SharedPreferences savedValues;
	Integer questionQty;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
		
		numQuestions = (EditText) findViewById(R.id.questionQty);
		numQuestions.setOnEditorActionListener(editTextListener);		     
	}
	
	private OnEditorActionListener editTextListener = new OnEditorActionListener(){

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			//The following code is used to convert String input values to an integer
			String convertToValue = numQuestions.getText().toString();
			questionQty = Integer.parseInt(convertToValue);
			
			//This is used to pass questionQty to other activities.
	        Editor editor = savedValues.edit();
	        editor.putInt("questionQty", questionQty);
	        editor.commit();
		
			return false;
		}		
	};	
}
