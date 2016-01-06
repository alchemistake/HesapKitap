package com.alchemistake.hesapkitap.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.alchemistake.hesapkitap.app.R;
import com.alchemistake.hesapkitap.app.adapters.MovementAdapter;
import com.alchemistake.hesapkitap.app.database.DataSource;
import com.alchemistake.hesapkitap.app.database.Movement;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    RecyclerView recyclerView;
    MovementAdapter adapter;
    FloatingActionButton fab;
    TextView net, income, outcome;

    DataSource dataSource;
    List<Movement> dataSet;

    int counter, currentMonth, currentYear;

    DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        df = new DecimalFormat("#.##");

        counter = -1;

        Calendar c = Calendar.getInstance();

        currentMonth = c.get(Calendar.MONTH);
        currentYear = c.get(Calendar.YEAR);

        net = (TextView) findViewById(R.id.net);
        income = (TextView) findViewById(R.id.income);
        outcome = (TextView) findViewById(R.id.outcome);

        dataSource = new DataSource(this);
        dataSource.open();

        dataSet = dataSource.getAllMovement();

        setToolBar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovementAdapter(dataSet, this);
        recyclerView.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView, null, new RecyclerView.OnScrollListener() {
            final private long DELAY = 1500;
            final private Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    fab.show();
                }
            };

            public void hide() {
                fab.hide();
                fab.removeCallbacks(runnable);
                fab.postDelayed(runnable, DELAY);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                hide();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                hide();
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
    protected void onResume() {
        super.onResume();

        updateDataSet();

        setToolBar();
    }

    public void add(View view) {
        Intent i = new Intent(this, AddActivity.class);
        startActivity(i);
    }


    public void setToolBar() {
        double income = 0, outcome = 0;

        for (Movement movement : dataSet) {
            income += movement.getIncome();
            outcome += movement.getOutcome();
        }

        this.income.setText(df.format(income));
        this.outcome.setText(df.format(outcome));
        net.setText(df.format(income - outcome));
    }

    public void updateDataSet() {

        StringBuilder stringBuilder = new StringBuilder();

        if (counter >= 0) {
            int wantedMonth, wantedYear = currentYear, cache = currentMonth - counter;

            while (cache < 0) {
                cache += 12;
                wantedYear--;
            }

            wantedMonth = cache;

            dataSet = dataSource.getMonthlyMovements(wantedMonth, wantedYear);

            switch (wantedMonth) {
                case Calendar.JANUARY:
                    stringBuilder.append("Ocak");
                    break;
                case Calendar.FEBRUARY:
                    stringBuilder.append("Şubat");
                    break;
                case Calendar.MARCH:
                    stringBuilder.append("Mart");
                    break;
                case Calendar.APRIL:
                    stringBuilder.append("Nisan");
                    break;
                case Calendar.MAY:
                    stringBuilder.append("Mayıs");
                    break;
                case Calendar.JUNE:
                    stringBuilder.append("Haziran");
                    break;
                case Calendar.JULY:
                    stringBuilder.append("Temmuz");
                    break;
                case Calendar.AUGUST:
                    stringBuilder.append("Ağustos");
                    break;
                case Calendar.SEPTEMBER:
                    stringBuilder.append("Eylül");
                    break;
                case Calendar.OCTOBER:
                    stringBuilder.append("Ekim");
                    break;
                case Calendar.NOVEMBER:
                    stringBuilder.append("Kasım");
                    break;
                case Calendar.DECEMBER:
                    stringBuilder.append("Aralık");
                    break;
            }

            stringBuilder.append(" - ");
            stringBuilder.append(wantedYear);
        } else {

            if (counter < -1)
                counter = -1;

            dataSet = dataSource.getAllMovement();

            stringBuilder.append("Tüm İşlemler");
        }

        adapter.updateDataSet(dataSet);

        setTitle(stringBuilder);

        setToolBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_forward:
                counter++;
                updateDataSet();
                return true;
            case R.id.action_backward:
                counter--;
                updateDataSet();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
