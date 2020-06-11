package com.heaven.vlcdemo;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class FileUtil {

    private static final String TAG = FileUtil.class.getSimpleName();
    private static String PATH_DIR = Environment.getExternalStorageDirectory().getPath()+"/MyPic";


    public static void saveBitmap(Bitmap bitmap){
        File file = new File(PATH_DIR);
        if (!file.exists()){
            file.mkdir();
        }
        String fileName = UUID.randomUUID().toString()+".jpg";
        try {
            File picFile = new File(PATH_DIR+File.separator+fileName);
            if (picFile.exists()){
                picFile.delete();
            }
            FileOutputStream out = new FileOutputStream(PATH_DIR+File.separator+fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
            bitmap.recycle();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e(TAG,"FileNotFoundException exception ： "+e.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e(TAG,"IOException exception ： "+e.toString());
        }

    }
}
