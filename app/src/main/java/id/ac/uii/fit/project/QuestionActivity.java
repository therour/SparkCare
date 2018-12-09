package id.ac.uii.fit.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.ac.uii.fit.project.models.Gejala;
import id.ac.uii.fit.project.services.DatabaseService;

public class QuestionActivity extends BaseActivity {

    private DatabaseReference db;
    private List<Gejala> listGejala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);

        listGejala = new ArrayList();
        final TextView questionText = (TextView) findViewById(R.id.question_text);
        db = DatabaseService.gejala();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listGejala.clear();
                for (DataSnapshot gejalaSnapshot: dataSnapshot.getChildren()) {
                    Gejala gejala = new Gejala(gejalaSnapshot.getKey(), gejalaSnapshot.child("nama").getValue().toString());
                    System.out.println(gejala.kode +": " + gejala.nama);
                    listGejala.add(gejala);
                }
                questionText.setText(listGejala.get(1).nama);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
