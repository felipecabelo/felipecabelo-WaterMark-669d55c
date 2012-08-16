package com.gamenyx.watermark;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

public class WaterMarkActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, 1);
		

	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				Uri selectedImage = imageReturnedIntent.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				String filePath = cursor.getString(cursor
						.getColumnIndex(filePathColumn[0]));
				cursor.close();

				Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
				Bitmap watermarkImage = BitmapFactory.decodeResource(
						getResources(), R.drawable.watermark);

				Bitmap result = ImageHandle.addWaterMark(yourSelectedImage,
						watermarkImage);
				ImageHandle.overWriteImage(filePath, result);

				//this.sendEmail(filePath);
				this.teste(filePath);
			}
		}
	}

//	private void sendEmail(String attach) {
//		final Intent emailIntent = new Intent(
//				android.content.Intent.ACTION_SEND);
//
//		emailIntent.setType("plain/text");
//
//		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {
//				"leofigueiroa@gmail.com", "alm.felipe@gmail.com" });
//
//		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
//				"Testando Assunto");
//
//		emailIntent
//				.putExtra(android.content.Intent.EXTRA_TEXT,
//						"Este é um teste de envio de e-mail, confira se existe um anexo.");
//
//		emailIntent.putExtra(Intent.EXTRA_STREAM,
//				Uri.fromFile(new File(attach)));
//
//		// emailIntent.putExtra(android.content.Intent.extra, value)
//
//		startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//	}

	private void teste(String path) {
		Socket socket = null;
		DataOutputStream dataOutputStream = null;
		try {
			socket = new Socket("192.168.4.1", 2004);
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			
			   	File f = new File(path);
			    byte [] buffer = new byte[(int)f.length()];
			    FileInputStream fis = new FileInputStream(f);
			    BufferedInputStream bis = new BufferedInputStream(fis);
			    bis.read(buffer,0,buffer.length);
			    dataOutputStream.write(buffer,0,buffer.length);
			    dataOutputStream.flush();
			
			
			//PrintWriter pw = new PrintWriter(dataOutputStream, true);
			//pw.println("Teste de android...");
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (dataOutputStream != null) {
				try {
					dataOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}