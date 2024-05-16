package fr.alexpado.go4lunch.data;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import fr.alexpado.go4lunch.interfaces.TaskCallback;

/**
 * Class allowing easy interaction with {@link FirebaseFirestore} for a specific entity.
 *
 * @param <T>
 *         Type of the entity for this repository.
 */
public abstract class FirestoreRepository<T extends FirestoreEntity> {

    private final String            collectionName;
    private final FirebaseFirestore store;

    /**
     * Create a new repository for the given collection name.
     *
     * @param collectionName
     *         Collection name that will be used by this repository.
     */
    public FirestoreRepository(String collectionName) {

        this.collectionName = collectionName;
        this.store          = FirebaseFirestore.getInstance();
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
    public abstract T createFrom(String id, DocumentSnapshot snapshot);

    /**
     * Retrieve a {@link CollectionReference} that will (and should) always be the target of this
     * repository.
     *
     * @return A {@link CollectionReference}.
     */
    public final CollectionReference collectionReference() {

        return this.store.collection(this.collectionName);
    }

    /**
     * Save the provided entity on Firestore.
     *
     * @param entity
     *         The entity to save.
     */
    public void save(T entity) {

        this.save(entity, nothing -> {});
    }

    /**
     * Save the provided entity on Firestore.
     *
     * @param entity
     *         The entity to save.
     * @param callback
     *         Callback that will be used on success or failure.
     */
    public void save(T entity, TaskCallback<Void> callback) {

        this.collectionReference()
            .document(entity.getId())
            .set(entity.getDocumentFields())
            .addOnSuccessListener(callback::onSuccess)
            .addOnFailureListener(callback::onException);
    }

    /**
     * Tries to find a document with the provided ID on Firestore.
     *
     * @param id
     *         The ID of the document to find
     * @param callback
     *         Callback that will be used on success or failure.
     */
    public void findById(String id, TaskCallback<T> callback) {

        this.collectionReference()
            .document(id)
            .get()
            .addOnCompleteListener(task -> callback.onSuccess(
                    this.createFrom(id, task.getResult())
            ))
            .addOnFailureListener(callback::onException);
    }

    /**
     * Retrieve every document in the collection targeted by this repository.
     *
     * @param callback
     *         Callback that will be used on success or failure.
     */
    public void findAll(TaskCallback<List<T>> callback) {

        this.collectionReference()
            .get()
            .addOnCompleteListener(task -> {
                List<T> users = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String id = document.getId();
                    users.add(this.createFrom(id, document));
                }
                callback.onSuccess(users);
            })
            .addOnFailureListener(callback::onException);
    }

}
