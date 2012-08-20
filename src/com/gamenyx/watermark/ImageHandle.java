package com.gamenyx.watermark;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ImageHandle {

    public static Bitmap addWaterMark(Bitmap source, Bitmap watermark)
    {
    	Bitmap bmOverlay = Bitmap.createBitmap(source.getWidth(), source.getHeight(), source.getConfig());
    	Canvas canvas = new Canvas(bmOverlay);
    	canvas.drawBitmap(source, 0, 0, null);
    	canvas.drawBitmap(watermark, 0, 0, null);
    	return bmOverlay;
    }

    public static boolean overWriteImage(String path, Bitmap image){
        try {
        	FileOutputStream out;	
			out = new FileOutputStream(new File(path));
			image.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    } 

public void naoFazNada(){}   
	String s = "nada manoel";
}
