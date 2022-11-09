package com.example.proyectoandres.ui.options;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class OptionsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public OptionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Options fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}