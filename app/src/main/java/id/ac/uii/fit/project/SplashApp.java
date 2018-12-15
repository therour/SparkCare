package id.ac.uii.fit.project;

import android.app.Application;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.ac.uii.fit.project.models.Gejala;
import id.ac.uii.fit.project.models.Penyakit;
import id.ac.uii.fit.project.models.Solusi;


public class SplashApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        syncGejala();
        syncPenyakit();
        SystemClock.sleep(3000);
    }

    private void syncGejala() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("gejala");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Gejala.getCollection().clear();
                for (DataSnapshot gejalaSnapshot: dataSnapshot.getChildren()) {
                    Gejala gejala = Gejala.parse(gejalaSnapshot);
                    Gejala.getCollection().add(gejala);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void syncSolusi() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("solusi");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Solusi.getCollection().clear();
                for (DataSnapshot solusiSnapshot : dataSnapshot.getChildren()) {
                    Solusi solusi = Solusi.parse(solusiSnapshot);
                    Solusi.put(solusi);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void syncPenyakit() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("penyakit");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                syncSolusi();
                Penyakit.getCollection().clear();
                for (DataSnapshot penyakitSnapshot : dataSnapshot.getChildren()) {
                    Penyakit penyakit = Penyakit.parse(penyakitSnapshot);
                    Penyakit.put(penyakit);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
