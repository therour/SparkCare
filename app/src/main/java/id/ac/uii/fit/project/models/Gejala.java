package id.ac.uii.fit.project.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Gejala {

    public String nama;
    public String kode;

    public Gejala(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nama", nama);
        result.put("kode", kode);

        return result;
    }

}
