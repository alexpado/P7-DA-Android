package fr.alexpado.go4lunch.data;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

/**
 * Abstract class allowing to easily manage entity that can be stored to / loaded from Firestore.
 */
public abstract class FirestoreEntity {

    private final String id;

    /**
     * Create a new instance of this {@link FirestoreEntity} with the provided ID value.
     *
     * @param id
     *         An ID value identifying this entity.
     */
    public FirestoreEntity(String id) {

        this.id = id;
    }

    /**
     * Retrieve this {@link FirestoreEntity}'s ID.
     *
     * @return An ID.
     */
    public String getId() {

        return this.id;
    }

    /**
     * Set all fields of this {@link FirestoreEntity} based on the provided {@link DocumentSnapshot}.
     *
     * @param data
     *         DocumentSnapshot containing field values.
     */
    public abstract void setDocumentFields(DocumentSnapshot data);

    /**
     * Get all fields of this {@link FirestoreEntity} as a map.
     *
     * @return Map containing field values.
     */
    public abstract Map<String, Object> getDocumentFields();

}
