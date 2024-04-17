package fr.alexpado.go4lunch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.FirebaseUiException;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

import fr.alexpado.go4lunch.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    /**
     * Property containing the firebase activity launcher, used in {@link #initiateSignIn()}.
     */
    private final ActivityResultLauncher<Intent> firebaseAuth = this.registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onAuthenticationResult
    );

    /**
     * Called after an authentication try from the user, whether it succeeded or not.
     *
     * @param authResult
     *         The {@link FirebaseAuthUIAuthenticationResult} containing information about the
     *         authentication try that happened.
     */
    private void onAuthenticationResult(FirebaseAuthUIAuthenticationResult authResult) {

        Log.d("AUTH", String.format("Received auth result: [Code: %s] - %s", authResult.getResultCode(), authResult.getIdpResponse()));

        switch (authResult.getResultCode()) {
            case RESULT_OK:
                MainActivity.start(this);
                break;
            case RESULT_CANCELED:
                this.binding.textView2.setText("Authentication cancelled by user");
                this.binding.textView2.setVisibility(View.VISIBLE);
                break;
            default:
                FirebaseUiException ex = authResult.getIdpResponse().getError();
                this.binding.textView2.setText(ex.getMessage());
                this.binding.textView2.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Start the login process using Firebase.
     */
    private void initiateSignIn() {

        this.binding.textView2.setVisibility(View.GONE);
        List<AuthUI.IdpConfig> authProviders = Collections.singletonList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        Intent intent = AuthUI.getInstance()
                              .createSignInIntentBuilder()
                              .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                              .setAvailableProviders(authProviders)
                              .setLogo(R.mipmap.ic_launcher)
                              .build();

        this.firebaseAuth.launch(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (NomNomUtils.isLoggedIn()) {
            MainActivity.start(this);
            return;
        }

        EdgeToEdge.enable(this);

        this.binding = ActivityLoginBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.binding.loginButton.setOnClickListener(view -> this.initiateSignIn());
        this.binding.textView2.setVisibility(View.GONE);
    }

}