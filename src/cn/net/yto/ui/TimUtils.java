package cn.net.yto.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.os.Environment;

public class TimUtils {
	/**
	* Dump the database file to external storage
	*/
    static void dumpDatabase(String packageName) throws IOException {
        File dbDirectory = new File("/data/data/" + packageName + "/databases/");
        if (dbDirectory.exists()) {
        	File[] dbFiles = dbDirectory.listFiles();
	        for(int i = 0; i < dbFiles.length; i++) {
	            FileInputStream fis = new FileInputStream(dbFiles[i]);
	            String outFileName = Environment.getExternalStorageDirectory() + "/" + dbFiles[i].getName();
	            OutputStream output = new FileOutputStream(outFileName);
	            byte[] buffer = new byte[1024];
	            int length;
	            while ((length = fis.read(buffer)) > 0) {
	                output.write(buffer, 0, length);
	            }
	            output.flush();
	            output.close();
	            fis.close();
        	}
        }
    }
}