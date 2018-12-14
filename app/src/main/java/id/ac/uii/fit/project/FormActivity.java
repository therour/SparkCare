package id.ac.uii.fit.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormActivity extends Activity {

    private EditText namalengkapEdittext, umurEdittext, beratbadanEdittext, emailEdittext, teleponEdittext;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //ngebind views dengan id nya
        bindViews();
    }

    private void bindViews() {
        namalengkapEdittext = (EditText) findViewById(R.id.namalengkap_edittext);
        umurEdittext = (EditText) findViewById(R.id.umur_edittext);
        beratbadanEdittext = (EditText) findViewById(R.id.beratbadan_editttext);
        emailEdittext = (EditText) findViewById(R.id.email_edittext);
        teleponEdittext = (EditText) findViewById(R.id.telepon_edittext);
        registerButton = (Button) findViewById(R.id.register_button);
    }

    private void showToastWithFormValues() {
        //Get edittexts values
        String namalengkap = namalengkapEdittext.getText().toString();
        String email = emailEdittext.getText().toString();

        //Check all fields
        if (!namalengkap.equals("") && !email.equals("") && !email.equals("")) {

        }
    }
}
