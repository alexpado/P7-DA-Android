package fr.alexpado.go4lunch.events.login;

import com.firebase.ui.auth.IdpResponse;

import fr.alexpado.go4lunch.events.EventHandler;
import fr.alexpado.go4lunch.events.EventListener;

public class LoginFailureEvent {

    public static final EventHandler<LoginFailureEvent, EventListener<LoginFailureEvent>> HANDLERS = new EventHandler<>(
            "LoginFailure");

    private final IdpResponse response;

    public LoginFailureEvent(IdpResponse response) {

        this.response = response;
    }

    public IdpResponse getResponse() {

        return this.response;
    }

}
