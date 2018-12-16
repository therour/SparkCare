package id.ac.uii.fit.project.services;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.ac.uii.fit.project.models.Gejala;
import id.ac.uii.fit.project.models.Penyakit;

public class DatabaseService {

    public void sync() {
        DatabaseReference dbRefGejala = FirebaseDatabase.getInstance().getReference("gejala");
        DatabaseReference dbRefPenyakit = FirebaseDatabase.getInstance().getReference("penyakit");
        dbRefGejala.addValueEventListener(new GejalaEventListener());
        dbRefPenyakit.addValueEventListener(new PenyakitEventListener());
    }

    public class GejalaEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot gejalaSnapshots) {
            if (Gejala.getCollection().size() != gejalaSnapshots.getChildrenCount()) {
                Gejala.getCollection().clear();
            }
            for (DataSnapshot gejalaSnapshot : gejalaSnapshots.getChildren()) {
                Gejala.getCollection().add(Gejala.parse(gejalaSnapshot));
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

    public class PenyakitEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot penyakitSnapshots) {
            if (Penyakit.getCollection().size() != penyakitSnapshots.getChildrenCount()) {
                Penyakit.getCollection().clear();
            }
            for (DataSnapshot penyakitSnapshot : penyakitSnapshots.getChildren()) {
                Penyakit.put(Penyakit.parse(penyakitSnapshot));
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
