package id.ac.uii.fit.project.models;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solusi {

    private String kode;
    private String nama;
    private String deskripsi;
    private List<String> pengobatanDini;
    private List<String> penyebab;
    protected static Map<String, Solusi> collection = new HashMap<>();

    public Solusi(String kode, String nama, String deskripsi) {
        this.kode = kode;
        this.nama = nama;
        this.deskripsi = deskripsi;
    }

    public Solusi(String kode, String nama, String deskripsi, List<String> pengobatanDini, List<String> penyebab) {
        this(kode, nama, deskripsi);
        this.pengobatanDini = pengobatanDini;
        this.penyebab = penyebab;
    }

    public static Map<String, Solusi> getCollection() { return Solusi.collection; }

    public static void put(Solusi solusi) { Solusi.collection.put(solusi.getKode(), solusi); }

    public static Solusi get(String kode) { return Solusi.collection.get(kode); }

    public static Solusi parse(DataSnapshot dataSnapshot) {
        Solusi solusi = new Solusi(
                dataSnapshot.getKey(),
                dataSnapshot.child("nama").getValue().toString(),
                dataSnapshot.child("deskripsi").getValue().toString()
        );

        if (dataSnapshot.child("pengobatan_dini").exists()) {
            List<String> pengobatanDini = new ArrayList<>();
            for (DataSnapshot pengobatanSnapshot : dataSnapshot.child("pengobatan_dini").getChildren()) {
                pengobatanDini.add(pengobatanSnapshot.getValue().toString());
            }
            solusi.setPengobatanDini(pengobatanDini);
        }
        if (dataSnapshot.child("penyebab").exists()) {
            List<String> penyebab = new ArrayList<>();
            for (DataSnapshot penyebabSnapshot : dataSnapshot.child("penyebab").getChildren()) {
                penyebab.add(penyebabSnapshot.getValue().toString());
            }
            solusi.setPenyebab(penyebab);
        }
        return solusi;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<String> getPengobatanDini() {
        return pengobatanDini;
    }

    public void setPengobatanDini(List<String> pengobatanDini) {
        this.pengobatanDini = pengobatanDini;
    }

    public List<String> getPenyebab() {
        return penyebab;
    }

    public void setPenyebab(List<String> penyebab) {
        this.penyebab = penyebab;
    }

    public String getDeskripsi() {

        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
