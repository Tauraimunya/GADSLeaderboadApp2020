package com.tauraimunya.gadsleaderboard.services;

import androidx.lifecycle.LiveData;

import com.tauraimunya.gadsleaderboard.model.Learner;

import java.util.List;

public interface LearnerRepo {

    LiveData<List<Learner>> getLearnerHoursData();

    LiveData<List<Learner>> getLearnerSkillIQData();

    LiveData<String> getErrorStream();

    void fetchData();

    void fetchDataSkill();
}
