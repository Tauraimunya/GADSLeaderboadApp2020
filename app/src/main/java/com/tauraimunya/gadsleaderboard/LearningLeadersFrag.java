package com.tauraimunya.gadsleaderboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tauraimunya.gadsleaderboard.UTILS.LearnerHoursRecyclerAdapter;
import com.tauraimunya.gadsleaderboard.model.Learner;

import java.util.List;


public class LearningLeadersFrag extends Fragment {

    private static final String TAG = LearningLeadersFrag.class.getSimpleName();

    private LearnerHourViewModel mLearnerViewModel;

    private RecyclerView mRecyclerView;
    private LearnerHoursRecyclerAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLearnerViewModel = ViewModelProviders.of(this).get(LearnerHourViewModel.class);
        mLearnerViewModel.fetchData();

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_learning_leaders, container, false);
        mRecyclerView = root.findViewById(R.id.list_items);

        Observer<List<Learner>> hoursDataObserver = this::updateData;
        Observer<String> errorObserver = this::setError;

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new LearnerHoursRecyclerAdapter(this.getContext());
        mLearnerViewModel.getLearnerHoursData().observe(getViewLifecycleOwner(), hoursDataObserver);
        mLearnerViewModel.getErrorUpdates().observe(getViewLifecycleOwner(), errorObserver);
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }

    public void updateData(List<Learner> data) {
        mAdapter.setItems(data);
    }


    public void setError(String msg) {
        showErrorToast(msg);
    }

    private void showErrorToast(String error) {
        Toast.makeText(this.getContext(), "Error:" + error, Toast.LENGTH_LONG).show();
    }

}