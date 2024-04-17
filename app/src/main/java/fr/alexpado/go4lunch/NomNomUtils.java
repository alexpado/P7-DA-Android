package fr.alexpado.go4lunch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * Go4Lunch utility class
 */
public final class NomNomUtils {

    private NomNomUtils() {}

    public static boolean isLoggedIn() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        return Objects.nonNull(user);
    }

}
