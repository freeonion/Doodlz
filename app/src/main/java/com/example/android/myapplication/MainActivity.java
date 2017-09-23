package com.example.android.myapplication;

import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

//?几个dialog的layout
//？文件的读写

public class MainActivity extends AppCompatActivity implements LineWidthDialogFragment.LineWidthDialogListener,ColorSelectionDialogFragment.ColorSelectionDialogListener {

    //? 默认值怎么设置
    private float mLineWidth = 10.0f;
    private int mColor = Color.BLACK;
    private DrawView mDrawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolBar = (Toolbar)findViewById(R.id.my_toolbar);
        mDrawView = (DrawView)findViewById(R.id.drawView);
        mDrawView.setPaint(mColor,mLineWidth);
        setSupportActionBar(myToolBar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_color:
                ColorSelectionDialogFragment dialogColor = ColorSelectionDialogFragment.newInstance(mColor);
                dialogColor.show(getSupportFragmentManager(),"ColorSelectionDialogFragment");
                return true;
            case R.id.action_line:
                LineWidthDialogFragment dialogLine =  LineWidthDialogFragment.newInstance(mLineWidth);
                dialogLine.show(getSupportFragmentManager(),"LineWidthDialogFragment");
                return true;
            case R.id.action_erase:
                mDrawView.erasePath();
                return true;
            case R.id.action_print:
                return true;
            case R.id.action_save:
                boolean saveSucceed = false;
 //             String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String saveToastStr = getResources().getString(R.string.save_toast_fail);
                if(isExternalStorageWritable())
                {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/drawView.png");
                    if(mDrawView.saveToPic(file))
                    {
                        saveSucceed = true;
                    }
                }
                if(saveSucceed)
                {
                    saveToastStr = getResources().getString(R.string.save_toast_succeed);
                }
                Toast.makeText(this,saveToastStr,Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogLineSetClick(AppCompatDialogFragment dialog) {
        LineWidthDialogFragment dialogFragment = (LineWidthDialogFragment)dialog;
        mLineWidth = dialogFragment.getLineWidth();
        mDrawView.setPaint(mColor,mLineWidth);
    }

    @Override
    public void onDialogColorSetClick(AppCompatDialogFragment dialog) {
        ColorSelectionDialogFragment dialogFragment = (ColorSelectionDialogFragment)dialog;
        mColor = dialogFragment.getColor();
        mDrawView.setPaint(mColor,mLineWidth);
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
