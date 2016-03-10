package com.scottyab.sample;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

public class MainActivity extends AppCompatActivity {

  private static String GITHUB_LINK = "https://github.com/scottyab/showhidepasswordedittext";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ((ShowHidePasswordEditText) findViewById(R.id.javaTinting)).setTintColor(Color.RED);
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
