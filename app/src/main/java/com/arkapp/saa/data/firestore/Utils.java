package com.arkapp.saa.data.firestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Created by Abdul Rehman on 24-07-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
public class Utils {

    public static boolean isValidTask(Task<QuerySnapshot> task) {
        return task != null &&
                task.isSuccessful() &&
                task.getResult() != null &&
                !task.getResult().isEmpty();
    }

    public static boolean isValidDoc(Task<DocumentSnapshot> task) {
        return task != null &&
                task.isSuccessful() &&
                task.getResult() != null;
    }

    public static boolean isValidButEmpty(Task<QuerySnapshot> task) {
        return task != null && task.isSuccessful();
    }
}
