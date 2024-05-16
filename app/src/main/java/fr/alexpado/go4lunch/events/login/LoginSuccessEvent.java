package fr.alexpado.go4lunch.events.login;

import com.firebase.ui.auth.IdpResponse;

import fr.alexpado.go4lunch.data.entities.User;
import fr.alexpado.go4lunch.events.EventHandler;
import fr.alexpado.go4lunch.events.EventListener;

public class LoginSuccessEvent {

    public static final EventHandler<LoginSuccessEvent, EventListener<LoginSuccessEvent>> HANDLERS = new EventHandler<>(
            "LoginSuccess");

    private final IdpResponse  response;
    private final User user;

    public LoginSuccessEvent(IdpResponse response, User user) {

        this.response = response;
        this.user     = user;
    }

    public IdpResponse getResponse() {

        return this.response;
    }

    public User getUser() {

        return this.user;
    }

}
