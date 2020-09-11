package com.tauraimunya.gadsleaderboard.services;

import androidx.lifecycle.LiveData;

public interface DataSource<T> {

    LiveData<T> getDataStreamLeanerHours();

    LiveData<T> getDataStreamLeanerSkillIQ();

    LiveData<String> getErrorStream();
}
