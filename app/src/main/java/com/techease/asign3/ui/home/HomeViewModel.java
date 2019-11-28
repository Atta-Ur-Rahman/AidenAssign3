package com.techease.asign3.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techease.asign3.genrelUtills.GenrelUtils;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(GenrelUtils.getCurrentDate());
    }

    public LiveData<String> getText() {
        return mText;
    }
}