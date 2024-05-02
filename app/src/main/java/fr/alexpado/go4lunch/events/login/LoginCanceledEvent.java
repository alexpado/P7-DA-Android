package fr.alexpado.go4lunch.events.login;

import com.firebase.ui.auth.IdpResponse;

import fr.alexpado.go4lunch.events.EventHandler;
import fr.alexpado.go4lunch.events.EventListener;

public class LoginCanceledEvent {

    public static final EventHandler<LoginCanceledEvent, EventListener<LoginCanceledEvent>> HANDLERS = new EventHandler<>(
            "LoginCanceled");


    private final IdpResponse response;

    public LoginCanceledEvent(IdpResponse response) {

        this.response = response;
    }

    public IdpResponse getResponse() {

        return this.response;
    }

}
