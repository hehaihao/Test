package com.xm6leefun.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.xm6leefun.common.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/23
 */
public class PermissionUtils {

    private static final String TAG = PermissionUtils.class.getSimpleName();
    public static final int CODE_RECORD_AUDIO = 0;
    public static final int CODE_GET_ACCOUNTS = 1;
    public static final int CODE_READ_PHONE_STATE = 2;
    public static final int CODE_CALL_PHONE = 3;
    public static final int CODE_CAMERA = 4;
    public static final int CODE_ACCESS_FINE_LOCATION = 5;
    public static final int CODE_ACCESS_COARSE_LOCATION = 6;
    public static final int CODE_READ_EXTERNAL_STORAGE = 7;
    public static final int CODE_WRITE_EXTERNAL_STORAGE = 8;
    public static final int CODE_MULTI_PERMISSION = 100;

    public static final int REQUEST_CODE_SETTING = 100;
    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static final String[] requestPermissions = {
            PERMISSION_RECORD_AUDIO,
            PERMISSION_GET_ACCOUNTS,
            PERMISSION_READ_PHONE_STATE,
            PERMISSION_CALL_PHONE,
            PERMISSION_CAMERA,
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE
    };

    private Context mContext;
    private Activity mActivity;

    public interface PermissionGrant {
        void onPermissionGranted(int requestCode);
    }

    public PermissionUtils(Activity activity, Context context) {
        mContext = context.getApplicationContext();
        mActivity = activity;
    }

    /**
     * Requests permission.
     *
     * @param requestCode request code, e.g. if you need request CAMERA permission,parameters is PermissionUtils.CODE_CAMERA
     */
    public void requestPermission(final int requestCode, PermissionGrant permissionGrant) {
        if (mActivity == null) {
            return;
        }

        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            return;
        }

        final String requestPermission = requestPermissions[requestCode];

        //?????????6.0??????????????????ActivityCompat.checkSelfPermission()???????????????PERMISSION_GRANTED???
        // ???????????????????????????????????????????????????ActivityCompat.checkSelfPermission(),?????????????????????(java.lang.RuntimeException: Unknown exception code: 1 msg null)???
        // ???????????????try{}catch(){},????????????????????????????????????????????????23?????????????????????
        // ????????????try{}catch(){}??????????????????????????????????????????
//        if (Build.VERSION.SDK_INT < 23) {
//            return;
//        }

        int checkSelfPermission;
        try {
            checkSelfPermission = ActivityCompat.checkSelfPermission(mActivity, requestPermission);
        } catch (RuntimeException e) {
            Toast.makeText(mActivity, "please open this permission", Toast.LENGTH_SHORT)
                    .show();

            return;
        }

        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, requestPermission)) {
                shouldShowRationale(requestCode, requestPermission);

            } else {

                ActivityCompat.requestPermissions(mActivity, new String[]{requestPermission}, requestCode);
            }

        } else {

            Toast.makeText(mActivity, "opened:" + requestPermissions[requestCode], Toast.LENGTH_SHORT).show();
            permissionGrant.onPermissionGranted(requestCode);
        }
    }

    private void requestMultiResult(String[] permissions, int[] grantResults, PermissionGrant permissionGrant) {

        if (mActivity == null) {
            return;
        }


        Map<String, Integer> perms = new HashMap<>();

        ArrayList<String> notGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            perms.put(permissions[i], grantResults[i]);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGranted.add(permissions[i]);
            }
        }

        if (notGranted.size() == 0) {
            Toast.makeText(mActivity, "all permission success" + notGranted, Toast.LENGTH_SHORT)
                    .show();
            permissionGrant.onPermissionGranted(CODE_MULTI_PERMISSION);
        } else {
            openSettingActivity("those permission need granted!");
        }

    }


    /**
     * ????????????????????????
     */
    public void requestMultiPermissions(PermissionGrant grant) {

        final List<String> permissionsList = getNoGrantedPermission(mActivity, false);
        final List<String> shouldRationalePermissionsList = getNoGrantedPermission(mActivity, true);

        if (permissionsList == null || shouldRationalePermissionsList == null) {
            return;
        }


        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(mActivity, permissionsList.toArray(new String[permissionsList.size()]),
                    CODE_MULTI_PERMISSION);


        } else if (shouldRationalePermissionsList.size() > 0) {
            showMessageOKCancel(mActivity, "should open those permission",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(mActivity, shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]),
                                    CODE_MULTI_PERMISSION);

                        }
                    });
        } else {
            grant.onPermissionGranted(CODE_MULTI_PERMISSION);
        }

    }


    private void shouldShowRationale(final int requestCode, final String requestPermission) {
        String[] permissionsHint = mActivity.getResources().getStringArray(R.array.permissions);
        showMessageOKCancel(mActivity, "Rationale: " + permissionsHint[requestCode], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions((Activity) mActivity,
                        new String[]{requestPermission},
                        requestCode);

            }
        });
    }

    private void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }

    /**
     * @param requestCode  Need consistent with requestPermission
     * @param permissions
     * @param grantResults
     */
    public void requestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults, PermissionGrant permissionGrant) {

        if (mActivity == null) {
            return;
        }


        if (requestCode == CODE_MULTI_PERMISSION) {
            requestMultiResult(permissions, grantResults, permissionGrant);
            return;
        }

        if (requestCode < 0 || requestCode >= requestPermissions.length) {

            Toast.makeText(mActivity, "illegal requestCode:" + requestCode, Toast.LENGTH_SHORT).show();
            return;
        }



        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            permissionGrant.onPermissionGranted(requestCode);

        } else {

            String[] permissionsHint = mActivity.getResources().getStringArray(R.array.permissions);
            openSettingActivity("Result" + permissionsHint[requestCode]);
        }

    }

    private void openSettingActivity(String message) {

        showMessageOKCancel(mActivity, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

                Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
                intent.setData(uri);
                mActivity.startActivity(intent);
            }
        });
    }


    /**
     * @param activity
     * @param isShouldRationale true: return no granted and shouldShowRequestPermissionRationale permissions, false:return no granted and !shouldShowRequestPermissionRationale
     * @return
     */
    public ArrayList<String> getNoGrantedPermission(Activity activity, boolean isShouldRationale) {

        ArrayList<String> permissions = new ArrayList<>();

        for (int i = 0; i < requestPermissions.length; i++) {
            String requestPermission = requestPermissions[i];


            int checkSelfPermission = -1;
            try {
                checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
            } catch (RuntimeException e) {
                Toast.makeText(activity, "please open those permission", Toast.LENGTH_SHORT)
                        .show();

                return null;
            }

            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {

                    if (isShouldRationale) {
                        permissions.add(requestPermission);
                    }

                } else {

                    if (!isShouldRationale) {
                        permissions.add(requestPermission);
                    }

                }

            }
        }

        return permissions;
    }

    // ??????????????????
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // ????????????????????????
    public boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    public void requestPermissions(String[] PERMISSIONS, int requestCode, String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {  //????????????????????????
            ActivityCompat.requestPermissions(mActivity, PERMISSIONS, requestCode);
        } else { //??????????????????
            //??????????????????????????????????????????????????????????????????????????????????????????
            ActivityCompat.requestPermissions(mActivity, PERMISSIONS, requestCode);
        }
    }
}
