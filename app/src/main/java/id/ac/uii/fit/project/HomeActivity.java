package id.ac.uii.fit.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
    }

    public void actionPenyakit(View view) {
        Intent intent = new Intent(this, DiagnosaActivity.class);
        startActivity(intent);
    }

    public void actionDiagnosa(View view) {
        Intent intent = new Intent(this, DiagnosaActivity.class);
        startActivity(intent);
    }

    public void actionAboutUs(View view) {
        Intent intent = new Intent(this, AboutUsActivity.class);
        startActivity(intent);
    }
}
