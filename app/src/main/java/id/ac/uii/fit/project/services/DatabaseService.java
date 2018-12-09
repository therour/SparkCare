package id.ac.uii.fit.project.services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseService {

    private static DatabaseReference db;
    private static DatabaseReference dbGejala;
    private static DatabaseService dbService;

    private DatabaseService() {}

    public static DatabaseReference ref() {
        if (db != null) {
            return db;
        } else {
            db = FirebaseDatabase.getInstance().getReference();
            return db;
        }
    }

    public static DatabaseReference gejala() {
        if (dbGejala != null) {
            return dbGejala;
        } else {
            dbGejala = FirebaseDatabase.getInstance().getReference("gejala");
            return dbGejala;
        }
    }
}
