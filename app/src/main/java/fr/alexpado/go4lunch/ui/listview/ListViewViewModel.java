package fr.alexpado.go4lunch.ui.listview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListViewViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ListViewViewModel() {

        this.mText = new MutableLiveData<>();
        this.mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {

        return this.mText;
    }

}