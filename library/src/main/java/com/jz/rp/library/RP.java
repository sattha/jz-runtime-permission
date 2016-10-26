package com.jz.rp.library;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sattha.p on 4/21/2016.
 */
public class RP {

    public static boolean isPermissionGranted(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isPermissionGranted(Context context, String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.checkSelfPermission(permission) ==
                    PackageManager.PERMISSION_GRANTED;
        } else {
            // for an older version app will FC if we don't put permission in AndroidManifest.xml
            return true;
        }
    }

    public static boolean isPermissionGranted(Activity activity, String[] permissions) {
        List<String> permissionList = new ArrayList<>(Arrays.asList(permissions));
        List<String> permissionNeeded = new ArrayList<>();
        for (String permission : permissionList) {
            if (!isPermissionGranted(activity, permission)) {
                permissionNeeded.add(permission);
            }
        }
        return permissionNeeded.isEmpty();
    }

    public static boolean isPermissionGranted(Context context, String[] permissions) {
        List<String> permissionList = new ArrayList<>(Arrays.asList(permissions));
        List<String> permissionNeeded = new ArrayList<>();
        for (String permission : permissionList) {
            if (!isPermissionGranted(context, permission)) {
                permissionNeeded.add(permission);
            }
        }
        return permissionNeeded.isEmpty();
    }

    public static boolean requestPermission(Activity activity, String permission,
                                            int requestCode) {
        if (!isPermissionGranted(activity, permission)) {
            if (Build.VERSION.SDK_INT >= 23) {
                activity.requestPermissions(new String[] {permission}, requestCode);
                return false;
            } else {
                throw new SecurityException("Did you put permission in XML?");
            }
        }
        return true;
    }

    public static boolean requestPermission(Fragment fragment, String permission,
                                            int requestCode) {
        if (!isPermissionGranted(fragment.getActivity(), permission)) {
            if (Build.VERSION.SDK_INT >= 23) {
                fragment.requestPermissions(new String[] {permission}, requestCode);
                return false;
            } else {
                throw new SecurityException("Did you put permission in XML?");
            }
        }
        return true;
    }

    public static boolean requestPermission(Activity activity, String[] permissions,
                                            int requestCode) {
        List<String> permissionList = new ArrayList<>(Arrays.asList(permissions));
        List<String> permissionNeeded = new ArrayList<>();
        for (String permission : permissionList) {
            if (!isPermissionGranted(activity, permission)) {
                permissionNeeded.add(permission);
            }
        }

        if (permissionList.size() > 0) {
            if (permissionNeeded.size() > 0) {
                if (Build.VERSION.SDK_INT >= 23) {
                    activity.requestPermissions(permissions, requestCode);
                    return false;
                } else {
                    throw new SecurityException("Did you put permission in XML?");
                }
            }
        }

        return true;
    }

    public static boolean requestPermission(Fragment fragment, String[] permissions,
                                            int requestCode) {
        List<String> permissionList = new ArrayList<>(Arrays.asList(permissions));
        List<String> permissionNeeded = new ArrayList<>();
        for (String permission : permissionList) {
            if (!isPermissionGranted(fragment.getActivity(), permission)) {
                permissionNeeded.add(permission);
            }
        }

        if (permissionList.size() > 0) {
            if (permissionNeeded.size() > 0) {
                if (Build.VERSION.SDK_INT >= 23) {
                    fragment.requestPermissions(permissions, requestCode);
                    return false;
                } else {
                    throw new SecurityException("Did you put permission in XML?");
                }
            }
        }

        return true;
    }

    public static String[] getListOfPermission() {

        return new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.SYSTEM_ALERT_WINDOW
        };
    }

    public static RPResult validateOnRequestPermissionsResult(Activity activity,
                                                              @NonNull String[] permissions,
                                                              @NonNull int[] grantResults) {
        ArrayList<RPDetail> rpDetails = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                rpDetails.add(new RPDetail(permissions[i], true, false));
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])){
                    rpDetails.add(new RPDetail(permissions[i], false, true));
                } else {
                    rpDetails.add(new RPDetail(permissions[i], false, false));
                }
            }
        }
        return new RPResult(rpDetails);
    }

    public static void openAppDetailsActivity(final Activity activity) {
        if (activity == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + activity.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        activity.startActivity(i);
    }

    @TargetApi(23)
    public static void openOverlayPermissionSetting(final Activity activity, int requestCode) {
        if (activity == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + activity.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

        activity.startActivityForResult(i, requestCode);
    }

    @TargetApi(23)
    public static void openOverlayPermissionSetting(final Fragment fragment, int requestCode) {
        if (fragment == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + fragment.getActivity().getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

        fragment.startActivityForResult(i, requestCode);
    }

}
