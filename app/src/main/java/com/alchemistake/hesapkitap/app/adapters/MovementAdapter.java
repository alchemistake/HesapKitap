package com.alchemistake.hesapkitap.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.alchemistake.hesapkitap.app.R;
import com.alchemistake.hesapkitap.app.activities.EditActivity;
import com.alchemistake.hesapkitap.app.database.Helper;
import com.alchemistake.hesapkitap.app.database.Movement;

import java.util.List;

/**
 * Created by Alchemistake on 25/08/15.
 */
public class MovementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Movement> dataSet;
    Context context;

    public MovementAdapter(List<Movement> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_movement, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final View itemView = holder.itemView;
        final int id = getId(position);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra(Helper.COLUMN_ID,id);
                context.startActivity(intent);
            }
        });

        TextView name = (TextView) itemView.findViewById(R.id.name),
                outcome = (TextView) itemView.findViewById(R.id.outcome),
                income = (TextView) itemView.findViewById(R.id.income);

        Movement currentMovement = dataSet.get(position);

        name.setText(currentMovement.getName());
        income.setText(String.valueOf(currentMovement.getIncome()));
        outcome.setText(String.valueOf(currentMovement.getOutcome()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void updateDataSet(List<Movement> dataSet){
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    public int getId(int position){
        return dataSet.get(position).getId();
    }
}
