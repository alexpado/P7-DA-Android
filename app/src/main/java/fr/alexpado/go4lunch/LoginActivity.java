package fr.alexpado.go4lunch;

import static fr.alexpado.go4lunch.utils.LogUtils.debug;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import fr.alexpado.go4lunch.databinding.ActivityLoginBinding;
import fr.alexpado.go4lunch.events.login.LoginCanceledEvent;
import fr.alexpado.go4lunch.events.login.LoginFailureEvent;
import fr.alexpado.go4lunch.events.login.LoginSuccessEvent;
import fr.alexpado.go4lunch.services.AuthenticationService;
import fr.alexpado.go4lunch.utils.NomNomUtils;

public class LoginActivity extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> auth = this.registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::handleAuthenticationResult
    );

    private ActivityLoginBinding  binding;
    private AuthenticationService authenticationService;

    /**
     * Start the login process using Firebase.
     */
    private void initiateSignIn() {

        debug(this, "Starting login flow...");
        this.binding.textView2.setVisibility(View.GONE);
        List<AuthUI.IdpConfig> authProviders = Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build());

        Intent intent = AuthUI.getInstance()
                              .createSignInIntentBuilder()
                              .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                              .setAvailableProviders(authProviders)
                              .setLogo(R.mipmap.ic_launcher)
                              .build();

        this.auth.launch(intent);
    }

    /**
     * Called by Firebase when a result of a login attempt is available.
     *
     * @param result
     *         A {@link FirebaseAuthUIAuthenticationResult} containing info about the login
     *         attempt.
     */
    private void handleAuthenticationResult(FirebaseAuthUIAuthenticationResult result) {
        // Simply redirect this to the authentication service - it's its job
        debug(this, "Received login packet, sending to authentication service...");
        this.authenticationService.handleAuthenticationResult(result);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.authenticationService = BeanFactory.getInstance(AuthenticationService.class);

        EdgeToEdge.enable(this);

        this.binding = ActivityLoginBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.binding.loginButton.setOnClickListener(view -> this.initiateSignIn());
        this.binding.textView2.setVisibility(View.GONE);

        debug(this, "Activity is now created.");

        if (NomNomUtils.isLoggedIn()) {
            debug(this, "A session is already active. Redirecting to MainActivity...");
            MainActivity.start(this);
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        LoginFailureEvent.HANDLERS.subscribe(this::onLoginFailureEvent);
        LoginCanceledEvent.HANDLERS.subscribe(this::onLoginCanceledEvent);
        LoginSuccessEvent.HANDLERS.subscribe(this::onLoginSuccessEvent);
        debug(this, "Activity is now ready.");
    }

    /**
     * Called when a login attempt ends in failure.
     *
     * @param event
     *         The event containing additional info about the login attempt.
     */
    private void onLoginFailureEvent(LoginFailureEvent event) {

        debug(this, "Login failed.");
        String errorText = Optional.ofNullable(event.getResponse())
                                   .map(IdpResponse::getError)
                                   .map(Throwable::getMessage)
                                   .orElse("Unknown error");

        this.binding.textView2.setText(errorText);
        this.binding.textView2.setVisibility(View.VISIBLE);
    }

    /**
     * Called when a login attempt has been canceled by the user.
     *
     * @param event
     *         The event containing additional info about the login attempt.
     */
    private void onLoginCanceledEvent(LoginCanceledEvent event) {

        debug(this, "Login canceled by user.");
        this.binding.textView2.setText(R.string.login_cancelled_by_user);
        this.binding.textView2.setVisibility(View.VISIBLE);
    }

    /**
     * Called when a login attempt ends successfully and the user is logged in.
     *
     * @param event
     *         The event containing additional info about the user that has been logged in.
     */
    private void onLoginSuccessEvent(LoginSuccessEvent event) {

        debug(this, "Login successful. Opening MainActivity...");
        LoginFailureEvent.HANDLERS.unsubscribe(this::onLoginFailureEvent);
        LoginCanceledEvent.HANDLERS.unsubscribe(this::onLoginCanceledEvent);
        LoginSuccessEvent.HANDLERS.unsubscribe(this::onLoginSuccessEvent);
        MainActivity.start(this);
    }

}