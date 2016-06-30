package com.scottyab.sample;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

public class MainActivity extends AppCompatActivity {

    private static String GITHUB_LINK = "https://github.com/scottyab/showhidepasswordedittext";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set the indicator tint programmatically
        ((ShowHidePasswordEditText) findViewById(R.id.javaTinting)).setTintColor(Color.RED);

        //shows setting a cutom font
        Typeface tf = Typeface.createFromAsset(getResources().getAssets(), "fonts/Rubrik-Medium.otf");
        ShowHidePasswordEditText customFontTV = (ShowHidePasswordEditText) findViewById(R.id.customFontAndHideShow);
        customFontTV.setTypeface(tf);

        //show the error icon
        final ShowHidePasswordEditText errorIcon = (ShowHidePasswordEditText) findViewById(R.id.errorIcon);
        Button validate = (Button) findViewById(R.id.validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorIcon.setError("This is a sample error message");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_github) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(GITHUB_LINK));
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
