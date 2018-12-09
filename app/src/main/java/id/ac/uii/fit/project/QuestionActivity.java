package id.ac.uii.fit.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
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
    private List<Gejala> listGejala = new ArrayList<>();
    private boolean isLoading;
    private int currentQuestion = 0;
    public static List<Gejala> answer = new ArrayList<>();
    private TextView questionText;
    private LinearLayout answerLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        answer.clear();
        answerLinearLayout = findViewById(R.id.answerLinearLayout);
        questionText = (TextView) findViewById(R.id.question_text);

        showLoading(true);

        db = DatabaseService.gejala();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listGejala.clear();
                for (DataSnapshot gejalaSnapshot: dataSnapshot.getChildren()) {
                    Gejala gejala = Gejala.parse(gejalaSnapshot);
                    System.out.println(gejala.kode +": " + gejala.nama);
                    listGejala.add(gejala);
                }
                showLoading(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void actionYes(View view) {
        if (! isFinishedAction()) {
            showLoading(true);
            Gejala gejala = listGejala.get(currentQuestion);
            if (! answer.contains(gejala)) {
                answer.add(gejala);
            }
            showLoading(false);
        }
    }

    public void actionNo(View view) {
        if (! isFinishedAction()) {
            showLoading(true);
            showLoading(false);
        }
    }

    private void showLoading(Boolean isLoading) {
        disableAction(isLoading, "Loading...");
    }

    private void disableAction(Boolean hideYesOrNo, String text) {
        if (hideYesOrNo) {
            questionText.setText(text);
            answerLinearLayout.setVisibility(View.INVISIBLE);
        } else {
            questionText.setText(listGejala.get(currentQuestion).nama);
            answerLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private boolean isFinishedAction() {
        if (listGejala.size() <= ++currentQuestion) {
            disableAction(true, "SELESAI");
            finish();
            return true;
        } else {
            return false;
        }
    }
}
