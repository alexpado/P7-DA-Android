package fr.alexpado.go4lunch.events.login;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseUser;

import fr.alexpado.go4lunch.events.EventHandler;
import fr.alexpado.go4lunch.events.EventListener;

public class LogoutEvent {

    public static final EventHandler<LogoutEvent, EventListener<LogoutEvent>> HANDLERS = new EventHandler<>(
            "LogoutEvent");

    private final FirebaseUser user;

    public LogoutEvent(FirebaseUser user) {

        this.user = user;
    }

    public FirebaseUser getUser() {

        return this.user;
    }

}
