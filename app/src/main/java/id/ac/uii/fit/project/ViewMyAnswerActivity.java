package id.ac.uii.fit.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import id.ac.uii.fit.project.adapters.ListViewAnswerAdapter;

public class ViewMyAnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_answer);

        ListView listViewAnswer = (ListView) findViewById(R.id.listViewAnswer);
        ListViewAnswerAdapter listViewAdapter = new ListViewAnswerAdapter(this, DiagnosaActivity.answer);
        listViewAnswer.setAdapter(listViewAdapter);
    }

    public void actionBackToQuestion(View view) {
        finish();
    }
}
