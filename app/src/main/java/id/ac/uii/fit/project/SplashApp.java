package id.ac.uii.fit.project;

import android.app.Application;
import android.os.SystemClock;

import com.google.firebase.database.FirebaseDatabase;


public class SplashApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        SystemClock.sleep(3000);
    }
}
