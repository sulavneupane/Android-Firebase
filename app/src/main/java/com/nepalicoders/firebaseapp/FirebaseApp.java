package com.nepalicoders.firebaseapp;

import android.app.Application;
import android.os.StrictMode;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Sulav on 8/4/17.
 */

public class FirebaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // Solve FileUriExposedException while trying to save files to external storage captured by camera
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }
}
