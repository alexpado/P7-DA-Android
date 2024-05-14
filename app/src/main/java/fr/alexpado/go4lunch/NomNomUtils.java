package fr.alexpado.go4lunch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.HashMap;
import java.util.Map;
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

    public static Map<String, Object> toMap(UserInfo user) {

        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getUid());
        map.put("name", user.getDisplayName());
        map.put("email", user.getEmail());

        return map;
    }

}
