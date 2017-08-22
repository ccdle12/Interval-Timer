package com.ccdle.christophercoverdale.boxingintervaltimer.CustomRounds;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by christophercoverdale on 14/08/2017.
 */

public interface CustomRoundsInterface
{
    void initCustomRoundsAdapter();
    void initRecyclerViewLayout();

    CustomRoundsAdapter getCustomRoundsAdapter();
    RecyclerView.LayoutManager getRecyclerViewLayoutManager();

    void addNewRow();
    void updateParentFabFlag();
    boolean isParentFabFlag();

    void showAddNewFabAnim(FloatingActionButton fab);
    void hideAddNewFabAnim(FloatingActionButton fab);

    void showDeleteFabAnim(FloatingActionButton fab);
    void hideDeleteFabAnim(FloatingActionButton fab);

    void showBackToDashboardFabAnim(FloatingActionButton fab);
    void hideBackToDashboardFabAnim(FloatingActionButton fab);

    void showRunTimer(FloatingActionButton fab);
    void hideRunTimer(FloatingActionButton fab);

    void deleteRow();
    void backToDashboard();

    void launchTimer();

    void getView(View customRoundsView);

    interface CustomRoundsCallback
    {
        void setInterface(CustomRoundsInterface customRoundsInterface);
    }
}
