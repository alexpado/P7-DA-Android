package fr.alexpado.go4lunch.interfaces;

public interface TaskCallback<D> {

    void onSuccess(D data);

    default void onException(Exception ex) {
        
    }

}
