package fr.alexpado.go4lunch.events;

public interface EventListener<T> {

    void onEvent(T event);

}
