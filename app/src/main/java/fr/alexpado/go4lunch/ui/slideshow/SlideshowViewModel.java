package fr.alexpado.go4lunch.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SlideshowViewModel() {

        this.mText = new MutableLiveData<>();
        this.mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {

        return this.mText;
    }

}