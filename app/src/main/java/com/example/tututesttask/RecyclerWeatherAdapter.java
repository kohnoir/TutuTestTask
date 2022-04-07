package com.example.tututesttask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerWeatherAdapter extends RecyclerView.Adapter<RecyclerWeatherAdapter.ViewHolder>implements Filterable {
    private final LayoutInflater inflater;
    protected List<WeatherItem> weatherItemAdapter;
    private OnItemClickListener<WeatherItem> onItemClickListener;
    private OnEntryClickListener mOnEntryClickListener;
    private final List<WeatherItem> originalList;
    private ViewHolder viewHolder;

    RecyclerWeatherAdapter(Context context, List<WeatherItem> note) {
        this.originalList = note;
        this.weatherItemAdapter = note;
        this.inflater = LayoutInflater.from(context);
    }
    public ViewHolder getViewHolder(){
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerWeatherAdapter.ViewHolder holder, final int position) {
        WeatherItem adapterItem = weatherItemAdapter.get(position);
        holder.headView.setText(adapterItem.getCountryWeather());
        holder.bodyView.setText(adapterItem.getWeatherConditions());
        holder.tempView.setText(adapterItem.getTempTime());
        holder.timeView.setText(adapterItem.getForecastDate());
    }

    @Override
    public int getItemCount() {
        return weatherItemAdapter.size();
    }

    public interface OnEntryClickListener {
        void onEntryClick(View view, int position);
    }

    public void setOnEntryClickListener(OnEntryClickListener onEntryClickListener) {
        mOnEntryClickListener = onEntryClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView headView, bodyView, timeView, tempView;

        ViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            headView = view.findViewById(R.id.text_heading);
            bodyView = view.findViewById(R.id.text_weather_conditions);
            timeView = view.findViewById(R.id.text_time);
            tempView = view.findViewById(R.id.text_temp);
            timeView.setPadding(40, 10, 40, 10);
            bodyView.setPadding(40, 10, 40, 10);
            headView.setPadding(40, 10, 40, 10);
            tempView.setPadding(40, 10, 40, 10);
        }

        @Override
        public void onClick(View v) {
            if (mOnEntryClickListener != null) {
                mOnEntryClickListener.onEntryClick(v, getLayoutPosition());
            }
        }

    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(itemView);
    }


    public void setOnItemClickListener(OnItemClickListener<WeatherItem> listener) {
        onItemClickListener = listener;
    }


    public interface OnItemClickListener<T> {
        void onItemClicked(int position, T item);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                weatherItemAdapter = (List<WeatherItem>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<WeatherItem> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = originalList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }
    //Поиск настроен на страну
    private List<WeatherItem> getFilteredResults(String constraint) {
        List<WeatherItem> results = new ArrayList<>();

        for (WeatherItem item : originalList) {
            if (item.getCountryWeather().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }
}