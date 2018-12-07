package id.ac.uii.fit.project.Services;

import com.google.firebase.auth.FirebaseUser;

public class AuthService {

    protected static FirebaseUser user;

    public AuthService() {

    }

    public static void setUser(FirebaseUser user) {
        AuthService.user = user;
    }

    public static FirebaseUser getUser() {
        return AuthService.user;
    }

    public static boolean check() {
        return AuthService.user != null;
    }

    public static void logout() {
        AuthService.user = null;
    }
}
