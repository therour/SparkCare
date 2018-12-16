package id.ac.uii.fit.project.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Gejala {

    public String nama;
    public String kode;
    public int number;
    protected static List<Gejala> collection = new ArrayList<>();

    public Gejala(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;
        this.number = Integer.parseInt(kode.substring(1));
    }

    @Exclude
    public static List<Gejala> getCollection() {
        return collection;
    }

    @Exclude
    public static Gejala parse(DataSnapshot dataSnapshot) {
        return new Gejala(dataSnapshot.getKey(), dataSnapshot.child("nama").getValue().toString());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nama", nama);
        result.put("kode", kode);

        return result;
    }

}
