package id.ac.uii.fit.project;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import id.ac.uii.fit.project.interfaces.AuthMiddleware;
import id.ac.uii.fit.project.services.AuthService;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnMulai = (Button) findViewById(R.id.btnMulai);
        btnMulai.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    btnMulai.setVisibility(View.VISIBLE);
            }
        }, 2500);
    }

    public void actionMulai(View view) {
        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
        startActivity(intent);
    }
}
