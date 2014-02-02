package com.triskelion.move;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class StartPage extends Activity {
	
	private static int score=0;
	private final static int threshold=4;
	private static TextView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_page);
		view = (TextView)findViewById(R.id.story);
		Intent intent = getIntent();
		score=intent.getIntExtra("score", 0);
		if (score>threshold){
			showText();
			}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_page, menu);
		return true;
	}
	
	public void map(View view){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
	public void story(View view){
        Intent intent = new Intent(this, Story.class);
        startActivity(intent);
    }
	
	public static void setScore(int ss){
		score=ss;
		
	}
	
	private static void showText(){
		
		view.setText("Lorem ipsum \nLorem Lorem \nipsum ipsum \nLorem ipsum");		
	}
	
	
		
		
	

}
