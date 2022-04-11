package edu.brynmawr.buildingaccessibility;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int VIEW_ACTIVITY_ID = 1;
    private static final int SEARCH_ACTIVITY_ID = 2;
    private static final int POST_ACTIVITY_ID = 3;
    private static final int ABOUT_ACTIVITY_ID = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onViewButtonClick(View v){
        Intent i = new Intent(this, ViewActivity.class);
        i.putExtra("MESSAGE", "Going to view all buildings");
        startActivityForResult(i,VIEW_ACTIVITY_ID);
    }

    public void onSearchButtonClick(View view) {
        Intent i = new Intent(this, SearchActivity.class);
        i.putExtra("MESSAGE", "Going to search a building");
        startActivityForResult(i,SEARCH_ACTIVITY_ID);
    }

    public void onPostButtonClick(View view) {
        Intent i = new Intent(this, PostActivity.class);
        i.putExtra("MESSAGE", "Going to post on the forum");
        startActivityForResult(i,POST_ACTIVITY_ID);
    }

    public void onAboutClick(View view) {
        Intent i = new Intent(this, AboutActivity.class);
        i.putExtra("MESSAGE", "Going to about page");
        startActivityForResult(i, ABOUT_ACTIVITY_ID);
    }
}