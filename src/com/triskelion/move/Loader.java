package com.triskelion.move;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

public class Loader extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        
        if(!checkFirstStart()){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor prefEditor = prefs.edit();
                    prefEditor.putBoolean("Auth", true);
                    prefEditor.commit();
                    signInStarter();
                }

            }, 1000);
        }

        else{
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    menuStarter();
                }
            }, 1000);
        }
    }
    
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.loader, menu);
    return true;
    }


private void signInStarter(){
    Intent intent = new Intent(this, SignIn.class);
    startActivity(intent);
}
private void menuStarter(){
    Intent intent = new Intent(this, StartPage.class);
    startActivity(intent);
}

private boolean checkFirstStart(){
    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    boolean auth = sharedPreferences.getBoolean("Auth",false);
    return auth;
}


    
}
