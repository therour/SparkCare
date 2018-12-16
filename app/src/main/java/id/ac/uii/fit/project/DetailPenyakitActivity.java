package id.ac.uii.fit.project;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import id.ac.uii.fit.project.models.Penyakit;

public class DetailPenyakitActivity extends BaseActivity {

    private static Penyakit penyakit;

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_detail);
        final TextView namaPenyakit = (TextView) findViewById(R.id.namaPenyakit);
        final TextView namaPenyakit2 = (TextView) findViewById(R.id.namaPenyakit2);
        final TextView penyebab = (TextView) findViewById(R.id.penyebab);
        final TextView pengobatan = (TextView) findViewById(R.id.pengobatan);
        final TextView deskripsi = (TextView) findViewById(R.id.deskripsi);

        namaPenyakit.setText(penyakit.getNama());
        namaPenyakit2.setText(penyakit.getNama());
        deskripsi.setText(penyakit.getDeskripsi());
        StringBuilder sbPenyebab = new StringBuilder();
        penyebab.setText(
                TextUtils.join("\n", penyakit.getPenyebab())
        );
        pengobatan.setText(
                TextUtils.join("\n", penyakit.getPengobatanDini())
        );

    }

    public static void setPenyakit(Penyakit penyakit) {
        DetailPenyakitActivity.penyakit = penyakit;
    }
}
