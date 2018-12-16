package id.ac.uii.fit.project.models;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Penyakit {

    private String kode;
    private String nama;
    private String deskripsi;
    private List<String> pengobatan_dini;
    private List<String> penyebab;

    protected static Map<String, Penyakit> collection = new HashMap<>();

    public Penyakit(String kode, String nama, String deskripsi) {
        this.kode = kode;
        this.nama = nama;
        this.deskripsi = deskripsi;
    }

    public Penyakit(String kode, String nama, String deskripsi, List<String> pengobatan_dini, List<String> penyebab) {
        this(kode, nama, deskripsi);
        this.pengobatan_dini = pengobatan_dini;
        this.penyebab = penyebab;
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
        List<String> listPenyebab = new ArrayList<>();
        for (DataSnapshot sebabSnapshot : dataSnapshot.child("penyebab").getChildren()) {
            listPenyebab.add(sebabSnapshot.getValue().toString());
        }
        List<String> listPengobatanDini = new ArrayList<>();
        for (DataSnapshot pengobatanSnapshot : dataSnapshot.child("pengobatan_dini").getChildren()) {
            listPengobatanDini.add(pengobatanSnapshot.getValue().toString());
        }
        return new Penyakit(
                dataSnapshot.getKey(),
                dataSnapshot.child("nama").getValue().toString(),
                dataSnapshot.child("deskripsi").getValue().toString(),
                listPengobatanDini,
                listPenyebab
        );
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public String getDeskripsi() { return deskripsi; }

    public void setPengobatanDini(List<String> pengobatan_dini) { this.pengobatan_dini = pengobatan_dini; }

    public void setPenyebab(List<String> penyebab) {
        this.penyebab = penyebab;
    }
}
