package com.gale_matany.ex1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button playBtn;
    private Switch startMusic;
    private boolean playMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = (Button) findViewById(R.id.play_btn_id);
        playBtn.setOnClickListener(this);
        startMusic = (Switch) findViewById(R.id.music_switch_id);
        playMusic = false;

        startMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                playMusic = isChecked;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(R.id.play_btn_id == v.getId()){
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("music", playMusic);
            startActivity(intent);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        // menu options shown in the 3 dots menu
        MenuItem aboutMenu = menu.add(getString(R.string.menu_about));
        MenuItem exitMenu = menu.add(getString(R.string.menu_exit));
        //  for the about app
        aboutMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                showAboutDialog();
                return true;
            }
        });
        // for the exit app
        exitMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                showExitDialog();
                return true;
            }
        });
        return true;
    }

    public void showAboutDialog()
    {
        String aboutApp = getString(R.string.app_name) +" (" + getPackageName() + ")\n\n" +
                "By "+ getString(R.string.creators) + ", 24/03/2021.";

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle(getString(R.string.about_app_title));
        dialog.setMessage(aboutApp);
        dialog.setCancelable(false);
        dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showExitDialog()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(R.drawable.ic_exit);
        dialog.setTitle(getString(R.string.exit_app_title));
        dialog.setMessage(getString(R.string.exit_app));
        dialog.setCancelable(false);
        dialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // close the app
                finish();
            }
        });
        dialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        dialog.show();
    }

}