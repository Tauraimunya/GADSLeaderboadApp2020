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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tauraimunya.gadsleaderboard.UTILS.LearnerSkillIQRecyclerAdapter;
import com.tauraimunya.gadsleaderboard.model.Learner;

import java.util.List;


    public class SkillLeadersFrag extends Fragment {

        private static final String TAG = SkillLeadersFrag.class.getSimpleName();


        private LearnerSkillViewModel mLearnerViewModel;

        private RecyclerView mRecyclerView;
        private LearnerSkillIQRecyclerAdapter mSkillIQAdapter;



        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mLearnerViewModel = ViewModelProviders.of(this).get(LearnerSkillViewModel.class);
            mLearnerViewModel.fetchData();


        }

        @Override
        public View onCreateView(
                @NonNull LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.tab_skill_leaders, container, false);
            mRecyclerView = root.findViewById(R.id.list_items2);


            Observer<List<Learner>> hoursDataObserver = this::updateData;
            Observer<String> errorObserver = this::setError;


            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);

            mSkillIQAdapter = new LearnerSkillIQRecyclerAdapter(this.getContext());
            mLearnerViewModel.getLearnerSkillIQData().observe(getViewLifecycleOwner(), hoursDataObserver);
            mLearnerViewModel.getErrorUpdates().observe(getViewLifecycleOwner(), errorObserver);
            mRecyclerView.setAdapter(mSkillIQAdapter);



            return root;
        }

        public void updateData(List<Learner> data) {
            mSkillIQAdapter.setItems(data);
        }


        public void setError(String msg) {
            showErrorToast(msg);
        }

        private void showErrorToast(String error) {
            Toast.makeText(this.getContext(), "Error:" + error, Toast.LENGTH_LONG).show();
        }

    }