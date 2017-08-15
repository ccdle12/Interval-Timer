package com.ccdle.christophercoverdale.boxingintervaltimer.CustomRounds;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccdle.christophercoverdale.boxingintervaltimer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by christophercoverdale on 14/08/2017.
 */

public class CustomRounds extends Fragment implements CustomRoundsInterface.CustomRoundsCallback
{
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.parent_fab) FloatingActionButton parentFAB;

    @BindView(R.id.add_new_row) FloatingActionButton addNewRowFAB;
    @BindView(R.id.back_to_dashboard_from_custom_rounds) FloatingActionButton backToDashboardFAB;
    @BindView(R.id.delete_row) FloatingActionButton deleteRowFAB;

    @BindView(R.id.run_timer) FloatingActionButton runTimerFAB;

    private CustomRoundsInterface customRoundsInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.custom_rounds, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        this.customRoundsInterface.initCustomRoundsAdapter();
        this.customRoundsInterface.initRecyclerViewLayout();

        this.recyclerView.setAdapter(this.customRoundsInterface.getCustomRoundsAdapter());
        this.recyclerView.setLayoutManager(this.customRoundsInterface.getRecyclerViewLayoutManager());
    }

    @Override
    public void setInterface(CustomRoundsInterface customRoundsInterface)
    {
        this.customRoundsInterface = customRoundsInterface;
    }


    @OnClick(R.id.parent_fab) void userClicksOnParentFab()
    {
        this.customRoundsInterface.updateParentFabFlag();

        if (this.customRoundsInterface.isParentFabFlag()) {
            this.showFabAnims();
        } else {
            this.hideFabAnims();
        }
    }

    private void showFabAnims()
    {
        this.customRoundsInterface.showRunTimer(this.runTimerFAB) ;
        this.customRoundsInterface.showBackToDashboardFabAnim(this.backToDashboardFAB);
        this.customRoundsInterface.showDeleteFabAnim(this.deleteRowFAB);
        this.customRoundsInterface.showAddNewFabAnim(this.addNewRowFAB);
    }

    private void hideFabAnims()
    {
        this.customRoundsInterface.hideRunTimer(this.runTimerFAB);
        this.customRoundsInterface.hideBackToDashboardFabAnim(this.backToDashboardFAB);
        this.customRoundsInterface.hideDeleteFabAnim(this.deleteRowFAB);
        this.customRoundsInterface.hideAddNewFabAnim(this.addNewRowFAB);
    }


    @OnClick(R.id.add_new_row) void addNewRow()
    {
        this.customRoundsInterface.addNewRow();
    }

    @OnClick(R.id.delete_row) void deleteRow()
    {
        this.customRoundsInterface.deleteRow();
    }
}
