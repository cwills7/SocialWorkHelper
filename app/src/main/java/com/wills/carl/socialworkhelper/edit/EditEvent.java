package com.wills.carl.socialworkhelper.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wills.carl.socialworkhelper.R;
import com.wills.carl.socialworkhelper.SettingsActivity;
import com.wills.carl.socialworkhelper.Supervision;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Carl on 1/15/2018.
 */

public class EditEvent extends AppCompatActivity {

    TextView tv_edit_date_title;

    EditText mSupervisionHours;
    EditText mSupervisionNotes;
    RadioGroup mSupervisionType;

    EditText mCeuHours;
    EditText mCeuNotes;
    RadioGroup mCeuType;

    private SimpleDateFormat dateFormatForEditTitle = new SimpleDateFormat("MMMM d, yyyy");
    long dateLong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setContentView(R.layout.edit_item);

        String dateTitleInput = getIntent().getStringExtra("dateInMillis");
        tv_edit_date_title = findViewById(R.id.edit_date_title);
        Date dateTitle;
        if(dateTitleInput != null && !dateTitleInput.isEmpty()){
            dateTitle = new Date(Long.parseLong(dateTitleInput));
            dateLong = Long.parseLong(dateTitleInput);
        } else{
            dateTitle = new Date();
            dateLong = System.currentTimeMillis();
        }

        tv_edit_date_title.setText(dateFormatForEditTitle.format(dateTitle));

        String [] items = {"Add Supervision Information", "Add CEU Information"};

        ListView list = (ListView) findViewById(R.id.lv_edit_list);
        EditAdapter adapter = new EditAdapter(this, R.layout.edit_item, items);
        list.setAdapter(adapter);
        list.setVisibility(View.VISIBLE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(0).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.save_button){
            saveItemAndExit();
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveItemAndExit(){
        //save only if some stuff is filled out;
        //Save null to things that are not filled out
        Supervision supervision = new Supervision(dateLong);


        Intent returnIntent = new Intent();
        returnIntent.putExtra("date", dateLong);
        returnIntent.putExtra("data", supervision.toString());
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
