package id.ac.uii.fit.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import id.ac.uii.fit.project.interfaces.AuthMiddleware;
import id.ac.uii.fit.project.services.AuthService;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this instanceof AuthMiddleware) {
            if (! AuthService.check()) {
                relogin();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (this instanceof AuthMiddleware) {
            inflater.inflate(R.menu.menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                AuthService.logout();
                relogin();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void relogin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            AuthService.setUser(user);
        }
        else  {
            Intent loginIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }

    protected void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
