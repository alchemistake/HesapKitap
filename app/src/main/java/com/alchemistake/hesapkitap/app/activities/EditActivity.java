package com.alchemistake.hesapkitap.app.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.alchemistake.hesapkitap.app.R;
import com.alchemistake.hesapkitap.app.database.DataSource;
import com.alchemistake.hesapkitap.app.database.Helper;
import com.alchemistake.hesapkitap.app.database.Movement;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class EditActivity extends ChangeActivity {

    int id;
    Movement movement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();

        id = getIntent().getExtras().getInt(Helper.COLUMN_ID);

        movement = dataSource.getMovement(id);

        year = movement.getYear();
        month = movement.getMonth();
        day = movement.getDay();

        name.setText(movement.getName());
        income.setText(String.valueOf(movement.getIncome()));
        outcome.setText(String.valueOf(movement.getOutcome()));

        updateTime();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_discard) {
            discard();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveAndClose(View view) {
        onBackPressed();
    }

    public void dismissAndClose(View view) {
        discard();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dataSource.updateMovement(movement.getId(), day, month, year, name.getText().toString(), incomeD, outcomeD);
    }

    public void discard() {
        dataSource.deleteMovement(movement);
        super.onBackPressed();
    }

    public void swap(View view){
        Editable temp = income.getText();
        income.setText(outcome.getText());
        outcome.setText(temp);
    }
}
