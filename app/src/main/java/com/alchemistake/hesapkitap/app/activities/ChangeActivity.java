package com.alchemistake.hesapkitap.app.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.alchemistake.hesapkitap.app.R;
import com.alchemistake.hesapkitap.app.database.DataSource;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by Alchemistake on 07/01/16.
 */
public class ChangeActivity extends ActionBarActivity {

    TextView time;
    EditText name,income,outcome;
    double incomeD, outcomeD;
    int year, month, day;

    DataSource dataSource;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateTime(){
        StringBuilder sb = new StringBuilder();
        if (day < 10)
            sb.append(0);
        sb.append(day);
        sb.append('.');
        if (month < 9)
            sb.append(0);
        sb.append(month + 1);
        sb.append('.');
        sb.append(year);

        time.setText(sb.toString());
    }

    public void dialogShower(View view){
        DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {
                year = i;
                month = i1;
                day = i2;

                updateTime();
            }
        }, year, month, day);
        dpd.vibrate(false);
        dpd.show(getFragmentManager(),"dpd");
    }

    public void saveAndClose(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if( !name.getText().toString().equals("")) {
            try{
                incomeD = Double.valueOf(this.income.getText().toString());
            }catch(Exception e){
                incomeD = 0;
            }

            try{
                outcomeD = Double.valueOf(this.outcome.getText().toString());
            }catch(Exception e){
                outcomeD = 0;
            }
        }
        super.onBackPressed();
    }

    protected void init(){
        dataSource = new DataSource(this);
        dataSource.open();

        time = (TextView) findViewById(R.id.time);
        name = (EditText) findViewById(R.id.name);
        income = (EditText) findViewById(R.id.income);
        outcome = (EditText) findViewById(R.id.outcome);
    }
}
