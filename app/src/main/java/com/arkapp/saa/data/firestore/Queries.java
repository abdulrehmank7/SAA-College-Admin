package com.arkapp.saa.data.firestore;

import com.arkapp.saa.data.models.UserData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static com.arkapp.saa.data.firestore.CONSTANTS.COLLECTION_USERS;
import static com.arkapp.saa.data.firestore.CONSTANTS.EMAIL;
import static com.arkapp.saa.data.firestore.CONSTANTS.USERNAME;

/**
 * Created by Abdul Rehman on 24-07-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
public class Queries {

    public final FirebaseFirestore firestoreRef = FirebaseFirestore.getInstance();

    public Task<DocumentReference> addUser(UserData data) {
        return firestoreRef
                .collection(COLLECTION_USERS)
                .add(data);
    }

    public Task<QuerySnapshot> checkUsername(String username) {
        return firestoreRef
                .collection(COLLECTION_USERS)
                .whereEqualTo(USERNAME, username)
                .get();
    }

    public Task<QuerySnapshot> checkEmail(String email) {
        return firestoreRef
                .collection(COLLECTION_USERS)
                .whereEqualTo(EMAIL, email)
                .get();
    }

}
