package com.tauraimunya.gadsleaderboard;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tauraimunya.gadsleaderboard.model.Learner;
import com.tauraimunya.gadsleaderboard.services.ImplementLearnerRepo;
import com.tauraimunya.gadsleaderboard.services.LearnerRepo;

import java.util.List;

public class LearnerHourViewModel extends ViewModel {

    private static final String TAG = LearnerHourViewModel.class.getSimpleName();
    private LearnerRepo mLearnerRepository;

    public LearnerHourViewModel() {
        mLearnerRepository = ImplementLearnerRepo.create();
    }

    public LiveData<List<Learner>> getLearnerHoursData() {
        return mLearnerRepository.getLearnerHoursData();
    }


    public LiveData<String> getErrorUpdates() {
        return mLearnerRepository.getErrorStream();
    }

    public void fetchData() {
        mLearnerRepository.fetchData();
    }

    @Override
    protected void onCleared() {
        Log.d(TAG, "onCleared() called");
        super.onCleared();
    }
}
