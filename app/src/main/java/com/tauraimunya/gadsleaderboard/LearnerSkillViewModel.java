package com.tauraimunya.gadsleaderboard;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tauraimunya.gadsleaderboard.model.Learner;
import com.tauraimunya.gadsleaderboard.services.ImplementLearnerRepo;
import com.tauraimunya.gadsleaderboard.services.LearnerRepo;

import java.util.List;

public class LearnerSkillViewModel extends ViewModel {

    private static final String TAG = LearnerSkillViewModel.class.getSimpleName();
    private LearnerRepo mLearnerRepository;

    public LearnerSkillViewModel() {
        mLearnerRepository = ImplementLearnerRepo.create();
    }


    public LiveData<List<Learner>> getLearnerSkillIQData() {
        return mLearnerRepository.getLearnerSkillIQData();
    }

    public LiveData<String> getErrorUpdates() {
        return mLearnerRepository.getErrorStream();
    }

    public void fetchData() {
        mLearnerRepository.fetchDataSkill();
    }


    @Override
    protected void onCleared() {
        Log.d(TAG, "onCleared() called");
        super.onCleared();
    }

}
