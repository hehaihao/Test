package com.xm6leefun.common.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.ml.scan.HmsBuildBitmapOption;
import com.huawei.hms.ml.scan.HmsScan;
import com.xm6leefun.common.R;
import com.xm6leefun.common.widget.toast.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目：ChatfengIM
 * 文件描述：保存图片到手机相册
 * 作者：ljj
 * 创建时间：2019/8/28
 */

public class SavePhoto {
    //存调用该类的活动
    Context context;

    public SavePhoto(Context context) {
        this.context = context;
    }

    /**
     * 生成二维码
     * @param code
     * @param i
     * @return
     */
    public static Bitmap createQrCodeImg(String code,int i) {
        int type = HmsScan.QRCODE_SCAN_TYPE;
        HmsBuildBitmapOption options = new HmsBuildBitmapOption.Creator().setBitmapBackgroundColor(Color.WHITE).setBitmapColor(Color.BLACK).setBitmapMargin(1).create();
        try {
            //如果未设置HmsBuildBitmapOption对象，生成二维码参数options置null
            Bitmap qrBitmap = ScanUtil.buildBitmap(code, type, i, i, options);
            return qrBitmap;
        } catch (WriterException e) {
            Log.w("buildBitmap", e);
            return null;
        }
    }

    //保存文件的方法：
    public void SaveBitmapFromView(View view) {
        int w = view.getWidth();
        int h = view.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        view.draw(c);
        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight());
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        addBitmapToAlbum(bmp, format.format(new Date()) + ".JPEG");
    }

    /*
     * 保存文件，文件名为当前日期
     */
    public String saveBitmap(Bitmap bitmap, String bitName) {
        String fileName;
        File file;
        if (Build.BRAND.equals("Xiaomi")) { // 小米手机
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + bitName;
        } else {  // Meizu 、Oppo
//                Log.v("qwe","002");
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/" + bitName;
        }
        file = new File(fileName);

        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush();
                out.close();
                // 插入图库
                MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), bitName, null);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
        // 提示保存成功
        ToastUtil.showCenterToast(context.getResources().getString(R.string.qrcode_save_successfully));
        // 发送广播，通知刷新图库的显示
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));
        return fileName;
    }

    /*
     * 保存文件，文件名为当前日期
     */
    public void addBitmapToAlbum(Bitmap bitmap, String displayName) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
        } else {
            values.put(MediaStore.MediaColumns.DATA, Environment.getExternalStorageDirectory().getPath()+"/"+Environment.DIRECTORY_DCIM+"/"+displayName);
        }
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (uri != null) {
            try {
                OutputStream outputStream = contentResolver.openOutputStream(uri);
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                    outputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            // 提示保存成功
            ToastUtil.showCenterToast(context.getResources().getString(R.string.qrcode_save_successfully));
        }
    }
}
