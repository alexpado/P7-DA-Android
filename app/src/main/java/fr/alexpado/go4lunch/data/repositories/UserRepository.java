package fr.alexpado.go4lunch.data.repositories;

import static fr.alexpado.go4lunch.utils.LogUtils.debug;
import static fr.alexpado.go4lunch.utils.LogUtils.warn;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import fr.alexpado.go4lunch.data.FirestoreRepository;
import fr.alexpado.go4lunch.data.entities.User;
import fr.alexpado.go4lunch.interfaces.TaskCallback;

/**
 * Class allowing easy interaction with {@link FirebaseFirestore} to manage {@link User} entities.
 */
public class UserRepository extends FirestoreRepository<User> {

    private static final String COLLECTION = "users";

    public UserRepository() {

        super(COLLECTION);
    }

    /**
     * Create an entity of this repository's type from an ID and a {@link DocumentSnapshot}. This
     * allows for custom parsing / formatting instead of relying on
     * {@link DocumentSnapshot#toObject(Class)}.
     *
     * @param id
     *         The ID of the entity.
     * @param snapshot
     *         The {@link DocumentSnapshot} containing the entity's data.
     *
     * @return A new instance of this repository's entity type.
     */
    @Override
    public User createFrom(String id, DocumentSnapshot snapshot) {

        User user = new User(id);
        user.setDocumentFields(snapshot);
        return user;
    }

    /**
     * Convert and save a {@link FirebaseUser} to a {@link User}.
     *
     * @param firebaseUser
     *         The {@link FirebaseUser} to save.
     *
     * @return A {@link User} matching the {@link FirebaseUser}.
     */
    public User saveLocalCopy(FirebaseUser firebaseUser) {

        // User entity contains no preferences or custom data, we can safely overwrite.
        User user = new User(firebaseUser);
        this.save(user, new TaskCallback<Void>() {
            @Override
            public void onSuccess(Void data) {

                debug(UserRepository.this, "User '%s' saved.", user.getId());
            }

            @Override
            public void onException(Exception ex) {

                warn(UserRepository.this, ex, "Unable to save user.");
            }
        });
        return user;
    }

}
