package com.heaven.vlcdemo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.Locale;

/**
 * Created by yunshitu on 2017/9/5.
 */

public class OSUtils {
    public static boolean checkPermission(@NonNull Context context, @NonNull String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkGrantResults(@NonNull int... grantResults) {
        for (int grant : grantResults) {
            if (grant != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static String getDeviceID(Context context) {
        /* ******************** 从网上抄的 ******************* */
        String m_szDevIDShort = "35" + "/" + //we make this look like a valid IMEI

                Build.BOARD.length() % 10 + "/" +
                Build.BRAND.length() % 10 + "/" +
                Build.CPU_ABI.length() % 10 + "/" +
                Build.DEVICE.length() % 10 + "/" +
                Build.DISPLAY.length() % 10 + "/" +
                Build.HOST.length() % 10 + "/" +
                Build.ID.length() % 10 + "/" +
                Build.MANUFACTURER.length() % 10 + "/" +
                Build.MODEL.length() % 10 + "/" +
                Build.PRODUCT.length() % 10 + "/" +
                Build.TAGS.length() % 10 + "/" +
                Build.TYPE.length() % 10 + "/" +
                Build.USER.length() % 10 + "/"; //13 digits
        return m_szDevIDShort;
    }

    public synchronized static File getTempDir(Context context, String tempDir) {
        File dir = new File(context.getExternalCacheDir(), tempDir);
        if (!dir.isDirectory() && !dir.mkdirs()) {
            return null;
        }
        return dir;
    }

    public synchronized static File getExtDir(Context context, String storeDir) {
        File dir = context.getExternalFilesDir(storeDir);
        return dir;
    }

    public static void removeFiles(File file) {
        if (file.isFile()) {
            file.delete();
        }
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                removeFiles(f);
            }
            file.delete();
        }
    }

    public static long filesSize(File root) {
        if (root.isFile()) {
            return root.length();
        }
        long cnt = 0;
        if (root.isDirectory()) {
            for (File f : root.listFiles()) {
                cnt += filesSize(f);
            }
        }
        return cnt;
    }

    public static String sizeToString(long size, int digit) {
        if (size < 0) {
            return "-" + sizeToString(-size, digit);
        }
        final String[] units = {"B", "KB", "MB", "GB", "TB"};
        long s = 1 << 10;
        String unit = "";
        for (int i = 0; i < units.length; ++i) {
            if (size < s) {
                unit = units[i];
                s >>= 10;
                break;
            }
            s <<= 10;
        }
        return String.format(Locale.CHINA, "%.2f%s", size * 1.0 / s, unit);
    }
}
