package fr.alexpado.go4lunch.data.entities;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import fr.alexpado.go4lunch.data.FirestoreEntity;
import fr.alexpado.go4lunch.utils.NomNomUtils;

public class User extends FirestoreEntity {

    private String name;
    private String email;
    private String avatar;

    public User(String id) {

        super(id);
    }

    public User(UserInfo user) {

        super(user.getUid());
        this.name   = user.getDisplayName();
        this.email  = user.getEmail();
        this.avatar = Optional.ofNullable(user.getPhotoUrl()).map(Uri::toString).orElse(null);
    }

    @Override
    public void setDocumentFields(DocumentSnapshot data) {

        NomNomUtils.checkKeys(data, DocumentSnapshot::contains, "name", "email", "avatar");
        this.name   = data.get("name", String.class);
        this.email  = data.get("email", String.class);
        this.avatar = data.get("avatar", String.class);
    }

    @Override
    public Map<String, Object> getDocumentFields() {

        return new HashMap<String, Object>() {{
            this.put("name", User.this.getName());
            this.put("email", User.this.getEmail());
            this.put("avatar", User.this.getAvatar());
        }};
    }

    public String getName() {

        return this.name;
    }

    public String getEmail() {

        return this.email;
    }

    public String getAvatar() {

        return this.avatar;
    }

    @NonNull
    @Override
    public String toString() {

        return String.format(
                "User{id='%s', name='%s', email='%s', avatar='%s'}",
                this.getId(),
                this.getName(),
                this.getEmail(),
                this.getAvatar()
        );
    }

}
