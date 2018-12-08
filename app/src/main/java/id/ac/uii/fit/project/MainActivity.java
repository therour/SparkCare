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

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnMasuk = (Button) findViewById(R.id.btnMasuk);
        btnMasuk.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if (AuthService.getUser() != null) {
                    next();
                } else if (auth.getCurrentUser() != null) {
                    AuthService.setUser(auth.getCurrentUser());
                    next();
                } else {
                    btnMasuk.setVisibility(View.VISIBLE);
                }
            }
        }, 2500);
    }

    public void actionMasuk(View view) {
        signIn(false);
    }

    public void next() {
        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    public void signIn(Boolean smartLockEnabled) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(smartLockEnabled)
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthService.setUser(user);
                next();
            } else {
                Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_LONG).show();
            }
        }
    }
}
