package id.ac.uii.fit.project.models;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Penyakit {

    private String kode;
    private String nama;
    private Solusi solusi;

    protected static Map<String, Penyakit> collection = new HashMap<>();

    public Penyakit(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;
    }

    public Penyakit(String kode, String nama, Solusi solusi) {
        this(kode, nama);
        this.solusi = solusi;
    }

    public static Map<String, Penyakit> getCollection() { return Penyakit.collection; }

    public static void put(Penyakit penyakit) {
        Penyakit.collection.put(penyakit.getKode(), penyakit);
    }

    public static Penyakit get(String kode) {
        return Penyakit.collection.get(kode);
    }

    public static List<Penyakit> getAll() {
        return new ArrayList<>(collection.values());
    }

    public static Penyakit parse(DataSnapshot dataSnapshot) {
        String kodeSolusi = dataSnapshot.child("solusi").getValue().toString();
        return new Penyakit(
                dataSnapshot.getKey(),
                dataSnapshot.child("nama").getValue().toString(),
                Solusi.get(kodeSolusi)
        );
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public Solusi getSolusi() {
        return solusi;
    }

    public void setSolusi(Solusi solusi) {
        this.solusi = solusi;
    }
}
