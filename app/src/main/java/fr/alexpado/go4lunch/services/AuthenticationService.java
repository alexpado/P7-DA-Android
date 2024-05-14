package fr.alexpado.go4lunch.services;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import fr.alexpado.go4lunch.NomNomUtils;
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
                assert user != null;

                Log.d(
                        "AUTH-SVC",
                        String.format(
                                "Account logged in { %s @ %s [ %s ]}",
                                user.getUid(),
                                user.getEmail(),
                                user.getDisplayName()
                        )
                );

                Map<String, Object> map = NomNomUtils.toMap(user);
                FirebaseFirestore store = FirebaseFirestore.getInstance();

                store.collection("users")
                     .document(user.getUid())
                     .set(map)
                     .addOnSuccessListener(aVoid -> Log.d(
                             "AUTH-SVC",
                             "User saved: " + user.getUid()
                     ))
                     .addOnFailureListener(e -> Log.w(
                             "AUTH-SVC",
                             "Unable to save logged in user to firestore",
                             e
                     ));

                Log.d("AUTH-SVC", "Dispatching login event...");

                LoginSuccessEvent.HANDLERS.dispatch(new LoginSuccessEvent(resp, user));
                break;
            case RESULT_CANCELED:
                Log.d("AUTH-SVC", "User canceled authentication: Dispatching event...");
                LoginCanceledEvent.HANDLERS.dispatch(new LoginCanceledEvent(resp));
                break;
            default:
                assert resp != null;
                Log.w("AUTH-SVC", "Login failure: Dispatching event...", resp.getError());
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
