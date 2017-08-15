package com.ccdle.christophercoverdale.boxingintervaltimer.CustomRounds;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ccdle.christophercoverdale.boxingintervaltimer.R;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.PackageModel;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.RoundType;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.TimeValuesHelper;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by christophercoverdale on 15/08/2017.
 */

public class CustomRoundsAdapter extends RecyclerView.Adapter<CustomRoundsAdapter.ViewHolder>
{

    private Context context;
    private List<RoundType> customRoundsList;
    private int roundNumber;

    public CustomRoundsAdapter(Context context)
    {
        this.context = context;
        this.customRoundsList = new LinkedList<RoundType>();

        this.initRoundNum();
        this.initDefaultRounds();
    }

    private void initRoundNum()
    {
        this.roundNumber = 1;
    }
    private void initDefaultRounds()
    {
        this.customRoundsList.add(new RoundType(90000, "work"));
        this.customRoundsList.add(new RoundType(30000, "rest"));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_round_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        RoundType roundType = this.customRoundsList.get(position);
        Long timeInMilliSecs = roundType.getRoundTime();

        holder.customRoundsNum.setText(String.valueOf(customRoundsList.size()/2) + ".");
        holder.customRoundsType.setText(this.formatRoundType(roundType.getRoundType()));
        holder.customMinutes.setText(this.convertToMinsDisplay(timeInMilliSecs));
        holder.customSeconds.setText(this.convertToSecsDisplay(timeInMilliSecs));

    }

    private String formatRoundType(String roundType)
    {
        String roundTypeVal = "";

        if (roundType.equals("work"))
            roundTypeVal = "Work";

        if (roundType.equals("rest"))
            roundTypeVal = "Rest";

        return roundTypeVal;
    }

    private String convertToMinsDisplay(long timeInMilliSecs)
    {
        int minutes = TimeValuesHelper.formatRemainingMinutes(timeInMilliSecs);
        String minutesDisplay = TimeValuesHelper.formatMinutesToString(minutes);

        return minutesDisplay;
    }

    private String convertToSecsDisplay(long timeInMilliSecs)
    {
        int seconds = TimeValuesHelper.formatRemainingSeconds(timeInMilliSecs);
        String secondsDisplay = TimeValuesHelper.formatSecondsToString(seconds);

        return secondsDisplay;
    }


    @Override
    public int getItemCount()
    {
        return this.customRoundsList.size();
    }

    public void add()
    {
        this.initDefaultRounds();
        this.notifyItemChanged((this.customRoundsList.size()-1));
    }

    public void delete()
    {
        this.customRoundsList.remove(this.customRoundsList.size()-1);
        this.customRoundsList.remove(this.customRoundsList.size()-1);
        this.notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.custom_round_number) TextView customRoundsNum;
        @BindView(R.id.custom_round_type) TextView customRoundsType;
        @BindView(R.id.custom_dash) TextView customDash;
        @BindView(R.id.custom_minutes) EditText customMinutes;
        @BindView(R.id.custom_colon) TextView customColon;
        @BindView(R.id.custom_seconds) EditText customSeconds;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
