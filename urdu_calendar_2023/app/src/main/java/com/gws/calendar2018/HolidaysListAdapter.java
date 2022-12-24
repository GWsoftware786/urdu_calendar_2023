package com.gws.calendar2018;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gws.calendar2018.models.Holidays;

import java.util.List;

/**
 * Created by Maheboob on 1/20/2018.
 */

public class HolidaysListAdapter extends RecyclerView.Adapter<HolidaysListAdapter.MyViewHolder> {
    private List<Holidays> holidaysList;


    public HolidaysListAdapter(List<Holidays> holidaysList) {
        this.holidaysList = holidaysList;
    }
    @Override
    public HolidaysListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holiday, parent, false);

        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView holidayDate,  holidayName;
        public LinearLayout holidayLayout;
        public MyViewHolder(View view) {
            super(view);
            holidayName = (TextView) view.findViewById(R.id.title);
            holidayDate = (TextView) view.findViewById(R.id.genre);
            holidayLayout = (LinearLayout) view.findViewById(R.id.holiday_details_layout);
        }
    }

    @Override
    public void onBindViewHolder(HolidaysListAdapter.MyViewHolder holder, int position) {
        Holidays holidays = holidaysList.get(position);
        holder.holidayName.setText(holidays.getHolidayName());
        holder.holidayDate.setText(holidays.getHolidayDate());
        if(position%2==0){
            holder.holidayLayout.setBackgroundColor(Color.parseColor("#CDE3FB"));
        }
    }

    @Override
    public int getItemCount() {
        return holidaysList.size();
    }
}
