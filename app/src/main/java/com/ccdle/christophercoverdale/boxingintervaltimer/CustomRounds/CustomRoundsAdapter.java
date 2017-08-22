package com.ccdle.christophercoverdale.boxingintervaltimer.CustomRounds;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ccdle.christophercoverdale.boxingintervaltimer.R;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.CustomRoundType;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.TimeValuesHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.media.CamcorderProfile.get;


/**
 * Created by christophercoverdale on 15/08/2017.
 */

public class CustomRoundsAdapter extends RecyclerView.Adapter<CustomRoundsAdapter.ViewHolder>
{
    private ArrayList<CustomRoundType> customRoundsList;

    public ArrayList<CustomRoundType> getCustomRoundsList()
    {
        return this.customRoundsList;
    }


    public CustomRoundsAdapter()
    {
        this.customRoundsList = new ArrayList<>();
        this.initDefaultRounds();
    }

    private void initDefaultRounds()
    {
        this.customRoundsList.add(new CustomRoundType("01", "30", "work", (customRoundsList.size()/2) + 1));
        this.customRoundsList.add(new CustomRoundType("00", "30", "rest", (customRoundsList.size()/2) + 1));
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_round_row, parent, false);
        return new ViewHolder(view, new CustomMinutesListener(), new CustomSecondsListener(), new CustomMinutesFocusChangedListener(), new CustomSecondsFocusChangedListener());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        CustomRoundType roundType = this.customRoundsList.get(this.getItemViewType(position));

        holder.customMinutesListener.updatePosition(this.getItemViewType(position));
        holder.customSecondsListener.updatePosition(this.getItemViewType(position));

        holder.customMinsFocusChanged.updatePosition(this.getItemViewType(position));
        holder.customSecsFocusChanged.updatePosition(this.getItemViewType(position));

        holder.customRoundsNum.setText(String.valueOf(roundType.getPosition()) + ".");
        holder.customRoundsType.setText(this.formatRoundType(roundType.getRoundType()));
        holder.customMinutes.setText(roundType.getCustomMinutes());
        holder.customSeconds.setText(roundType.getCustomSeconds());

    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
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

        private CustomMinutesListener customMinutesListener;
        private CustomSecondsListener customSecondsListener;

        private CustomMinutesFocusChangedListener customMinsFocusChanged;
        private CustomSecondsFocusChangedListener customSecsFocusChanged;

        public ViewHolder(View itemView, CustomMinutesListener customMinutesListener, CustomSecondsListener customSecondsListener, CustomMinutesFocusChangedListener customMinsFocusChanged, CustomSecondsFocusChangedListener customSecsFocusChanged)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.customMinutesListener = customMinutesListener;
            this.customSecondsListener = customSecondsListener;

            this.customMinutes.addTextChangedListener(this.customMinutesListener);
            this.customSeconds.addTextChangedListener(this.customSecondsListener);

            this.customMinsFocusChanged = customMinsFocusChanged;
            this.customSecsFocusChanged = customSecsFocusChanged;

            this.customMinutes.setOnFocusChangeListener(this.customMinsFocusChanged);
            this.customSeconds.setOnFocusChangeListener(this.customSecsFocusChanged);
        }

    }

    private class CustomMinutesListener implements TextWatcher
    {
        private int position;

        public void updatePosition(int position)
        {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {
            CustomRoundType roundType = customRoundsList.get(position);
            roundType.setCustomMinutes(charSequence.toString());

        }

        @Override
        public void afterTextChanged(Editable editable)
        {

        }
    }

    private class CustomMinutesFocusChangedListener implements View.OnFocusChangeListener
    {
        private int position;

        public void updatePosition(int position)
        {
            this.position = position;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (!hasFocus)
            {
                CustomRoundType roundType = customRoundsList.get(position);

                if (!roundType.getCustomMinutes().equals(""))
                {
                    int minutes = TimeValuesHelper.convertStringToInt(roundType.getCustomMinutes());

                    if (minutes > 60)
                    {
                        roundType.setCustomMinutes("60");
                        roundType.setCustomSeconds("00");
                        notifyItemChanged(position);
                    }
                }
            }
        }
    }


    private class CustomSecondsListener implements TextWatcher
    {
        private int position;

        public void updatePosition(int position)
        {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {
            CustomRoundType roundType = customRoundsList.get(position);
            roundType.setCustomSeconds(charSequence.toString());

        }

        @Override
        public void afterTextChanged(Editable editable)
        {

        }
    }

    private class CustomSecondsFocusChangedListener implements View.OnFocusChangeListener
    {
        private int position;

        public void updatePosition(int position)
        {
            this.position = position;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (!hasFocus)
            {
                CustomRoundType roundType = customRoundsList.get(position);

                if (!roundType.getCustomSeconds().equals(""))
                {
                    int seconds = TimeValuesHelper.convertStringToInt(roundType.getCustomSeconds());

                    if (seconds > 60)
                    {
                        roundType.setCustomSeconds("59");
                        notifyItemChanged(position);
                    }
                }
            }
        }
    }
}
