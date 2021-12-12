package com.example.racingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racingapp.Models.Record;
import com.example.racingapp.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Fragment fragment;
    private ArrayList<Record> records = new ArrayList<>();
    private RecordItemClickListener recordItemClockListener;


    public RecordAdapter(Fragment fragment, ArrayList<Record> records) {
        this.fragment = fragment;
        this.records = records;
    }

    public RecordAdapter setRecordItemClickListener(RecordItemClickListener recordItemClockListener) {
        this.recordItemClockListener = recordItemClockListener;
        return this;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from
                (parent.getContext()).inflate
                (R.layout.list_record_item, parent, false);

        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecordViewHolder recordViewHolder = (RecordViewHolder) holder;
        Record record = getItem(position);


        recordViewHolder.item_TV_date.setText("" + record.getDate());
        recordViewHolder.item_TV_score.setText("" + record.getScore());


    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    private Record getItem(int position) {
        return records.get(position);
    }

    public interface RecordItemClickListener {
        void recordItemClick(Record record, int position);

    }


    public class RecordViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView item_TV_score;
        private MaterialTextView item_TV_date;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            item_TV_date = itemView.findViewById(R.id.item_TV_date);
            item_TV_score = itemView.findViewById(R.id.item_TV_score);

            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recordItemClockListener.recordItemClick
                                    (getItem(getAdapterPosition()), getAdapterPosition());
                        }
                    });
        }
    }
}