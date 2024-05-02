package fr.alexpado.go4lunch.events;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.HashSet;

public class EventHandler<K, T extends EventListener<K>> {

    private final String        friendlyName;
    private final Collection<T> listeners = new HashSet<>();

    public EventHandler(String friendlyName) {this.friendlyName = friendlyName;}

    public boolean subscribe(T listener) {

        Log.d("EVH", String.format("Registering '%s' as listener in '%s'", listener, this));
        return this.listeners.add(listener);
    }

    public boolean unsubscribe(T listener) {

        Log.d("EVH", String.format("Unregistering '%s' as listener in '%s'", listener, this));
        return this.listeners.remove(listener);
    }

    public void dispatch(K event) {

        Log.d(
                "EVH",
                String.format(
                        "Dispatching '%s' to %s listener(s) in '%s'",
                        event,
                        this.listeners.size(),
                        this
                )
        );
        for (T listener : this.listeners) {
            listener.onEvent(event);
        }
    }

    @NonNull
    @Override
    public String toString() {

        return String.format("EventHandler@%s", this.friendlyName);
    }

}
