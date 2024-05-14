package fr.alexpado.go4lunch.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MapViewModel() {

        this.mText = new MutableLiveData<>();
        this.mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {

        return this.mText;
    }

}