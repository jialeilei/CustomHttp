package com.http.lei.customhttp.file;

import android.content.Context;
import android.os.Environment;
import com.http.lei.customhttp.utils.Md5Encrypt;
import java.io.File;
import java.io.IOException;

/**
 * Created by lei on 2017/4/26.
 * 文件管理类
 */
public class FileStorageManager {

    private Context mContext;

    private static final FileStorageManager sManager = new FileStorageManager();

    public static FileStorageManager getInstance(){
        return sManager;
    }

    private FileStorageManager(){

    }

    public void init(Context context){
        this.mContext = context;
    }

    public File getFileByName(String url){
        File parent;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//是否有储存卡
            parent = mContext.getExternalCacheDir();
        }else {
            parent = mContext.getCacheDir();
        }

        String fileName = Md5Encrypt.generateCode(url);//进行了加密
        File file = new File(parent,fileName);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
