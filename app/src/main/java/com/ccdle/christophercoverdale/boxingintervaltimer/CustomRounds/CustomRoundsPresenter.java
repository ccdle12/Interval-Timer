package com.ccdle.christophercoverdale.boxingintervaltimer.CustomRounds;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.CustomRoundsPresenterInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.Dashboard;
import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.TimerDisplayPresenterInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.R;
import com.ccdle.christophercoverdale.boxingintervaltimer.Settings.SettingsSingleton;
import com.ccdle.christophercoverdale.boxingintervaltimer.TimerDisplay.TimerDisplayPresenter;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.CustomRoundType;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.PackageModel;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.RoundType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

import static android.content.ContentValues.TAG;

/**
 * Created by christophercoverdale on 14/08/2017.
 */

public class CustomRoundsPresenter implements CustomRoundsPresenterInterface, CustomRoundsInterface
{
    private PackageModel packageModel;
    private CustomRounds customRounds;
    private CustomRoundsInterface.CustomRoundsCallback customRoundsCallback;

    private CustomRoundsAdapter customRoundsAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    private boolean parentFABClicked;
    private Dashboard dashboard;

    private TimerDisplayPresenterInterface timerDisplayPresenterInterface;

    private View customRoundsView;

    public CustomRoundsPresenter(CustomRounds customRounds, Dashboard dashboard, TimerDisplayPresenter timerDisplayPresenter)
    {
        this.customRounds = customRounds;
        this.customRoundsCallback = this.customRounds;
        this.customRoundsCallback.setInterface(this);

        this.dashboard = dashboard;
        this.timerDisplayPresenterInterface = timerDisplayPresenter;

        this.parentFABClicked = false;
    }

    @Override
    public void launchCustomRounds()
    {
        this.replaceFragmentInActivityWithCustomRounds();
    }

    @Override
    public void sendPackageModel(PackageModel packageModel)
    {
        this.packageModel = packageModel;
    }



    private void replaceFragmentInActivityWithCustomRounds()
    {
        Activity activityRef = this.packageModel.getActivity();

        activityRef.getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, this.customRounds)
                .commit();
    }


    @Override
    public void initCustomRoundsAdapter()
    {
        this.customRoundsAdapter = new CustomRoundsAdapter();
    }

    @Override
    public void initRecyclerViewLayout()
    {
        this.recyclerViewLayoutManager = new LinearLayoutManager(this.packageModel.getActivity().getApplicationContext());
    }

    @Override
    public CustomRoundsAdapter getCustomRoundsAdapter()
    {
        return this.customRoundsAdapter;
    }

    @Override
    public RecyclerView.LayoutManager getRecyclerViewLayoutManager()
    {
        return this.recyclerViewLayoutManager;
    }

    @Override
    public void addNewRow()
    {
        this.customRoundsAdapter.add();
    }

    @Override
    public void updateParentFabFlag()
    {
        this.parentFABClicked = !this.parentFABClicked;
    }

    @Override
    public boolean isParentFabFlag()
    {
        return this.parentFABClicked;
    }

    @Override
    public void showAddNewFabAnim(FloatingActionButton fab)
    {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin += (int) (fab.getWidth() * 1.8);
        layoutParams.bottomMargin += (int) (fab.getHeight() * 2.8);

        Animation showAddNewRowFAB = AnimationUtils.loadAnimation(this.packageModel.getActivity().getApplicationContext(), R.anim.show_add_new_row_anim);

        fab.setLayoutParams(layoutParams);
        fab.startAnimation(showAddNewRowFAB);
        fab.setVisibility(View.VISIBLE);   
    }

    @Override
    public void hideAddNewFabAnim(FloatingActionButton fab)
    {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab.getWidth() * 1.8);
        layoutParams.bottomMargin -= (int) (fab.getHeight() * 2.8);

        Animation hideAddNewRowFAB = AnimationUtils.loadAnimation(this.packageModel.getActivity().getApplicationContext(), R.anim.hide_add_new_row_anim);

        fab.setLayoutParams(layoutParams);
        fab.startAnimation(hideAddNewRowFAB);
        fab.setVisibility(View.INVISIBLE);    
    }

    @Override
    public void showDeleteFabAnim(FloatingActionButton fab)
    {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin += (int) (fab.getWidth() * 2.0);
        layoutParams.bottomMargin += (int) (fab.getHeight() * 1.5);

        Animation showAddNewRowFAB = AnimationUtils.loadAnimation(this.packageModel.getActivity().getApplicationContext(), R.anim.show_delete_row_anim);

        fab.setLayoutParams(layoutParams);
        fab.startAnimation(showAddNewRowFAB);
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDeleteFabAnim(FloatingActionButton fab)
    {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab.getWidth() * 2.0);
        layoutParams.bottomMargin -= (int) (fab.getHeight() * 1.5);

        Animation hideAddNewRowFAB = AnimationUtils.loadAnimation(this.packageModel.getActivity().getApplicationContext(), R.anim.hide_delete_row_anim);

        fab.setLayoutParams(layoutParams);
        fab.startAnimation(hideAddNewRowFAB);
        fab.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showBackToDashboardFabAnim(FloatingActionButton fab)
    {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin += (int) (fab.getWidth() * 1.5);
        layoutParams.bottomMargin += (int) (fab.getHeight() * 0.25);

        Animation showBackToDashboardFabAnim = AnimationUtils.loadAnimation(this.packageModel.getActivity().getApplicationContext(), R.anim.show_back_to_dashboard);

        fab.setLayoutParams(layoutParams);
        fab.startAnimation(showBackToDashboardFabAnim);
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBackToDashboardFabAnim(FloatingActionButton fab)
    {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab.getWidth() * 1.5);
        layoutParams.bottomMargin -= (int) (fab.getHeight() * 0.25);

        Animation hideBackToDashboardFabAnim = AnimationUtils.loadAnimation(this.packageModel.getActivity().getApplicationContext(), R.anim.hide_back_to_dashboard);

        fab.setLayoutParams(layoutParams);
        fab.startAnimation(hideBackToDashboardFabAnim);
        fab.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRunTimer(FloatingActionButton fab)
    {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin += (int) (fab.getWidth() * 0.5);
        layoutParams.bottomMargin += (int) (fab.getHeight() * 3.2);

        Animation showBackToDashboardFabAnim = AnimationUtils.loadAnimation(this.packageModel.getActivity().getApplicationContext(), R.anim.show_run_timer_anim);

        fab.setLayoutParams(layoutParams);
        fab.startAnimation(showBackToDashboardFabAnim);
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRunTimer(FloatingActionButton fab)
    {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab.getWidth() * 0.5);
        layoutParams.bottomMargin -= (int) (fab.getHeight() * 3.2);

        Animation hideBackToDashboardFabAnim = AnimationUtils.loadAnimation(this.packageModel.getActivity().getApplicationContext(), R.anim.hide_run_timer_anim);

        fab.setLayoutParams(layoutParams);
        fab.startAnimation(hideBackToDashboardFabAnim);
        fab.setVisibility(View.INVISIBLE);
    }

    @Override
    public void deleteRow()
    {
        this.customRoundsAdapter.delete();
    }

    @Override
    public void backToDashboard()
    {
        this.resetFlags();
        this.goBackToDashboard();
    }

    private void resetFlags()
    {
        this.parentFABClicked = false;
    }

    private void goBackToDashboard()
    {
        this.hideSoftKeyboard();

        Activity activityRef = this.packageModel.getActivity();

        activityRef.getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, this.dashboard)
                .commit();
    }


    @Override
    public void launchTimer()
    {
        this.hideSoftKeyboard();
        this.resetFlags();
        this.customRoundsAdapter.notifyDataSetChanged();
        this.initTimerDisplay();
    }

    @Override
    public void getView(View customRoundsView)
    {
        this.customRoundsView = customRoundsView;
    }


    private void initTimerDisplay()
    {
        LinkedBlockingDeque queue = this.convertListToBlockingDeque(this.customRoundsAdapter.getCustomRoundsList());

        this.timerDisplayPresenterInterface.sendPackageModel(this.packageModel);
        this.timerDisplayPresenterInterface.sendQueue(queue);
        this.timerDisplayPresenterInterface.launchTimerDisplay();
    }

    private LinkedBlockingDeque convertListToBlockingDeque(ArrayList arrayList)
    {
        LinkedBlockingDeque queue = new LinkedBlockingDeque();

        for (int i = 0; i < arrayList.size(); i++)
        {
            CustomRoundType customRoundType = (CustomRoundType) arrayList.get(i);
            customRoundType.setTime();

            queue.add(customRoundType);
        }

        return queue;
    }

    private void hideSoftKeyboard()
    {
        InputMethodManager keyboard = (InputMethodManager) this.packageModel.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(this.customRoundsView.getWindowToken(), 0);
    }
}
