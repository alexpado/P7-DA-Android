package fr.alexpado.go4lunch.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GalleryViewModel() {

        this.mText = new MutableLiveData<>();
        this.mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {

        return this.mText;
    }

}