package id.ac.uii.fit.project;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import id.ac.uii.fit.project.models.Gejala;

public class FormActivity extends BaseActivity {

    private EditText namalengkapEdittext, umurEdittext, beratbadanEdittext, emailEdittext, teleponEdittext;
    private Button registerButton;
    private final String MESSAGE_REQUIRED = "Wajib diisi";

    private static List<Gejala> gejala = new ArrayList<>();

    public static void setGejala(List<Gejala> gejala) {
        FormActivity.gejala = gejala;
    }

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

    public void actionSubmitForm(View view) {
        String nama = namalengkapEdittext.getText().toString();
        String umur = umurEdittext.getText().toString();
        String berat_badan = beratbadanEdittext.getText().toString();
        String email = emailEdittext.getText().toString();
        String telp = teleponEdittext.getText().toString();
        boolean error = false;
        if (TextUtils.isEmpty(nama)) {
            namalengkapEdittext.setError(MESSAGE_REQUIRED);
            error = true;
        }
        if (TextUtils.isEmpty(umur)) {
            umurEdittext.setError(MESSAGE_REQUIRED);
            error = true;
        }
        if (TextUtils.isEmpty(berat_badan)) {
            beratbadanEdittext.setError(MESSAGE_REQUIRED);
            error = true;
        }
        if (TextUtils.isEmpty(email)) {
            emailEdittext.setError(MESSAGE_REQUIRED);
            error = true;
        }
        if (TextUtils.isEmpty(telp)) {
            teleponEdittext.setError(MESSAGE_REQUIRED);
            error = true;
        }
        if (error) return;
        DatabaseReference mPasien = FirebaseDatabase.getInstance().getReference("pasien").push();
        Map<String, Object> pasien = new HashMap<>();
        pasien.put("nama", nama);
        pasien.put("umur", Long.valueOf(umur));
        pasien.put("berat_badan", Double.valueOf(berat_badan));
        pasien.put("email", email);
        pasien.put("telp", telp);
        String tanggal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta")).getTime());
        pasien.put("tanggal", tanggal);
        List<String> gejalaPasien = new ArrayList<>();
        for (Gejala gejala : FormActivity.gejala) {
            gejalaPasien.add(gejala.kode);
        }
        pasien.put("gejala", gejalaPasien);
        mPasien.updateChildren(pasien);
        showToast("Terima kasih");
        finish();
    }
}
