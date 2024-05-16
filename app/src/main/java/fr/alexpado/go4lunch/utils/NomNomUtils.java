package fr.alexpado.go4lunch.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static <T> void checkKeys(T obj, BiPredicate<T, String> predicate, String... keys) {

        String missingKeys = Stream.of(keys)
                                   .filter(key -> !predicate.test(obj, key))
                                   .collect(Collectors.joining(", "));

        if (!missingKeys.isEmpty()) {
            throw new IllegalArgumentException(String.format(
                    "The provided map does not contains all required keys. Missing keys: %s",
                    missingKeys
            ));
        }
    }

    public static void checkKeys(Map<String, ?> map, String... keys) {
        checkKeys(map, Map::containsKey, keys);
    }

}
