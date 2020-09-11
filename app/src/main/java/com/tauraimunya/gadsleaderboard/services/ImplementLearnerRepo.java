package com.tauraimunya.gadsleaderboard.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.tauraimunya.gadsleaderboard.model.Learner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImplementLearnerRepo implements LearnerRepo {

    private static final String TAG = ImplementLearnerRepo.class.getSimpleName();

    private ExecutorService mExecutor = Executors.newFixedThreadPool(5);

    private final MediatorLiveData<List<Learner>> mDataLearnerHoursMerger = new MediatorLiveData<>();
    private final MediatorLiveData<List<Learner>> mDataLearnerSkillIQMerger = new MediatorLiveData<>();
    MediatorLiveData<String> mErrorMerger = new MediatorLiveData<>();
    private final RemoteDataSource mRemoteDataSource;

    private ImplementLearnerRepo(RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;

        mDataLearnerHoursMerger.addSource(this.mRemoteDataSource.getDataStreamLeanerHours(), entities ->
                mExecutor.execute(() -> {
                    Log.d(TAG, "list Learner Hours");
                    List<Learner> list = entities;
                    mDataLearnerHoursMerger.postValue(list);
                })
        );
        mDataLearnerSkillIQMerger.addSource(this.mRemoteDataSource.getDataStreamLeanerSkillIQ(), entities ->
                mExecutor.execute(() -> {
                    Log.d(TAG, "list Learner Hours");
                    List<Learner> list = entities;
                    mDataLearnerSkillIQMerger.postValue(list);
                })
        );

        mErrorMerger.addSource(mRemoteDataSource.getErrorStream(), errorStr -> {
            mErrorMerger.setValue(errorStr);
        });
    }

    public static LearnerRepo create() {
        final RemoteDataSource source = new RemoteDataSource();
        return  new ImplementLearnerRepo(source);
    }

    @Override
    public LiveData<List<Learner>> getLearnerHoursData() {

        return mDataLearnerHoursMerger;
    }

    @Override
    public LiveData<List<Learner>> getLearnerSkillIQData() {
        return mDataLearnerSkillIQMerger;
    }

    @Override
    public LiveData<String> getErrorStream() {
        return mErrorMerger;
    }

    @Override
    public void fetchData() {
        mRemoteDataSource.fetchDataLearnerHours();
    }

    @Override
    public void fetchDataSkill() {
        mRemoteDataSource.fetchDataLearnerSkillIQ();
    }


}
