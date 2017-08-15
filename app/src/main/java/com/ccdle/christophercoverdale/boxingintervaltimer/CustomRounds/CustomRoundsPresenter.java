package com.ccdle.christophercoverdale.boxingintervaltimer.CustomRounds;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.CustomRoundsPresenterInterface;
import com.ccdle.christophercoverdale.boxingintervaltimer.R;
import com.ccdle.christophercoverdale.boxingintervaltimer.Utils.PackageModel;

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

    public CustomRoundsPresenter(CustomRounds customRounds)
    {
        this.customRounds = customRounds;
        this.customRoundsCallback = this.customRounds;

        this.customRoundsCallback.setInterface(this);

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
        this.customRoundsAdapter = new CustomRoundsAdapter(this.packageModel.getActivity().getApplicationContext());
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

}
