package id.ac.uii.fit.project;

import android.os.Bundle;
import android.widget.TextView;

import id.ac.uii.fit.project.interfaces.AuthMiddleware;
import id.ac.uii.fit.project.services.AuthService;

public class HomeActivity extends BaseActivity implements AuthMiddleware {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final TextView helloUser = (TextView) findViewById(R.id.helloUser);
        helloUser.setText("Hello " + AuthService.getUser().getDisplayName() + " !");
    }
}

