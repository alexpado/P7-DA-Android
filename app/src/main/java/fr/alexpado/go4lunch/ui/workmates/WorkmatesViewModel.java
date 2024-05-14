package fr.alexpado.go4lunch.ui.workmates;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WorkmatesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WorkmatesViewModel() {

        this.mText = new MutableLiveData<>();
        this.mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {

        return this.mText;
    }

}