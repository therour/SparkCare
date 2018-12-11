package id.ac.uii.fit.project.models;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@IgnoreExtraProperties
public class Gejala {

    public String nama;
    public String kode;

    public Gejala(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;
    }

    @Exclude
    public static List<Gejala> getAll() {
        List<Gejala> listGejala = new ArrayList<>();
        final TaskCompletionSource<DataSnapshot> tcs = new TaskCompletionSource<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("gejala");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });

        Task<DataSnapshot> t = tcs.getTask();
        try {
            Tasks.await(t);
        } catch (ExecutionException | InterruptedException e) {
            t = Tasks.forException(e);
        }

        if (! t.isSuccessful()) {
            return listGejala;
        }
        DataSnapshot snapshot = t.getResult();
        for (DataSnapshot gejalaSnapshot : snapshot.getChildren()) {
            listGejala.add(parse(gejalaSnapshot));
        }
        return listGejala;
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
