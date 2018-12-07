package id.ac.uii.fit.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import id.ac.uii.fit.project.Services.AuthService;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final TextView helloUser = (TextView) findViewById(R.id.helloUser);
        helloUser.setText("Hello " + AuthService.getUser().getDisplayName());
    }
}

