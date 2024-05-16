package fr.alexpado.go4lunch.services;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static fr.alexpado.go4lunch.utils.LogUtils.debug;
import static fr.alexpado.go4lunch.utils.LogUtils.warn;

import android.content.Context;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fr.alexpado.go4lunch.data.entities.User;
import fr.alexpado.go4lunch.data.repositories.UserRepository;
import fr.alexpado.go4lunch.events.login.LoginCanceledEvent;
import fr.alexpado.go4lunch.events.login.LoginFailureEvent;
import fr.alexpado.go4lunch.events.login.LoginSuccessEvent;
import fr.alexpado.go4lunch.events.login.LogoutEvent;

public class AuthenticationService {

    public void handleAuthenticationResult(FirebaseAuthUIAuthenticationResult result) {

        IdpResponse resp = result.getIdpResponse();
        switch (result.getResultCode()) {
            case RESULT_OK:
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                assert firebaseUser != null;

                debug(
                        this,
                        "Account logged in { %s @ %s [ %s ]}",
                        firebaseUser.getUid(),
                        firebaseUser.getEmail(),
                        firebaseUser.getDisplayName()
                );

                UserRepository repository = new UserRepository();
                User user = repository.saveLocalCopy(firebaseUser);

                debug(this, "Dispatching login event...");
                LoginSuccessEvent.HANDLERS.dispatch(new LoginSuccessEvent(resp, user));
                break;
            case RESULT_CANCELED:
                debug(this, "User canceled authentication: Dispatching event...");
                LoginCanceledEvent.HANDLERS.dispatch(new LoginCanceledEvent(resp));
                break;
            default:
                assert resp != null;
                warn(this, resp.getError(), "Login failure: Dispatching error...");
                LoginFailureEvent.HANDLERS.dispatch(new LoginFailureEvent(resp));
                break;
        }
    }

    public void logout(Context context) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthUI.getInstance().signOut(context);
        debug(this, "User '%s' is now logged out.", user.getUid());
        LogoutEvent.HANDLERS.dispatch(new LogoutEvent(user));
    }

}
