package id.ac.uii.fit.project;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import id.ac.uii.fit.project.adapters.ListViewPenyakitAdapter;
import id.ac.uii.fit.project.models.Penyakit;

public class PenyakitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyakit);

        EditText editTxtCariPenyakit = (EditText) findViewById(R.id.editTxtCariPenyakit);
        ListView listViewPenyakit = (ListView) findViewById(R.id.listViewPenyakit);
        final ListViewPenyakitAdapter listViewPenyakitAdapter = new ListViewPenyakitAdapter(this, Penyakit.getAll());
        listViewPenyakit.setAdapter(listViewPenyakitAdapter);
        editTxtCariPenyakit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listViewPenyakitAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
