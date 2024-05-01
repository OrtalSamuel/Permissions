package com.example.mypermissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashMap;

public class PermissionManagerImpl implements PermissionManager {
    private static PermissionManagerImpl instance;
    private Context context;
    private HashMap<Integer, Runnable> runAfterPermissionGranted;

    private PermissionManagerImpl(Context context) {
        this.context = context;
        runAfterPermissionGranted = new HashMap<>();
    }

    public static synchronized PermissionManagerImpl getInstance(Context context) {
        if (instance == null) {
            instance = new PermissionManagerImpl(context);
        }
        return instance;
    }

    @Override
    public boolean checkPermissionStatus(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestPermission(Activity activity, String permission, int requestCode, Runnable runAfterPermissionGranted) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            this.runAfterPermissionGranted.put(requestCode, runAfterPermissionGranted);
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void handlePermissionResponse(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (runAfterPermissionGranted.get(requestCode) != null)
                runAfterPermissionGranted.get(requestCode).run();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Delegate handling to the singleton instance
        getInstance(context).handlePermissionResponse(requestCode, permissions, grantResults);
    }
}
