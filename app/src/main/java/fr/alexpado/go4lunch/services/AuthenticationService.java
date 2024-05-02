package fr.alexpado.go4lunch.services;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

import fr.alexpado.go4lunch.R;
import fr.alexpado.go4lunch.events.login.LoginCanceledEvent;
import fr.alexpado.go4lunch.events.login.LoginFailureEvent;
import fr.alexpado.go4lunch.events.login.LoginSuccessEvent;
import fr.alexpado.go4lunch.events.login.LogoutEvent;

public class AuthenticationService {

    public void handleAuthenticationResult(FirebaseAuthUIAuthenticationResult result) {

        IdpResponse resp = result.getIdpResponse();
        switch (result.getResultCode()) {
            case RESULT_OK:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                LoginSuccessEvent.HANDLERS.dispatch(new LoginSuccessEvent(resp, user));
                break;
            case RESULT_CANCELED:
                LoginCanceledEvent.HANDLERS.dispatch(new LoginCanceledEvent(resp));
                break;
            default:
                LoginFailureEvent.HANDLERS.dispatch(new LoginFailureEvent(resp));
                break;
        }
    }

    public void logout(Context context) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthUI.getInstance().signOut(context);
        LogoutEvent.HANDLERS.dispatch(new LogoutEvent(user));
    }


}
