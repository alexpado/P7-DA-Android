package fr.alexpado.go4lunch.events.login;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseUser;

import fr.alexpado.go4lunch.events.EventHandler;
import fr.alexpado.go4lunch.events.EventListener;

public class LoginSuccessEvent {

    public static final EventHandler<LoginSuccessEvent, EventListener<LoginSuccessEvent>> HANDLERS = new EventHandler<>(
            "LoginSuccess");

    private final IdpResponse  response;
    private final FirebaseUser user;

    public LoginSuccessEvent(IdpResponse response, FirebaseUser user) {

        this.response = response;
        this.user     = user;
    }

    public IdpResponse getResponse() {

        return this.response;
    }

    public FirebaseUser getUser() {

        return this.user;
    }

}
