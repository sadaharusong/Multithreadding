package com.example.multithreadding;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class ImageLoader {
	private ImageView mimageView;
	private String mUrl;
	private Handler mhandler = new Handler()
	{
		public void handleMessage(Message msg) 
		{
			super.handleMessage(msg);
			if (mimageView.getTag().equals(mUrl)) {
				mimageView.setImageBitmap((Bitmap)msg.obj);
			}
			
		}
	};
	
	public void showImageByThread(ImageView imageView,final String url)
	{
		mimageView = imageView;
		mUrl = url;
		new Thread()
		{
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				Bitmap bitmap = getBitmapFromURL(url);
				Message message = Message.obtain();
				message.obj = bitmap;
				mhandler.sendMessage(message);
			}
		}.start();
	}
	
	public Bitmap getBitmapFromURL (String urlString)
	{
		Bitmap bitmap ;
		
		InputStream is = null;
		
			
			try 
			{
				URL url = new URL(urlString);
				HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
				is = new BufferedInputStream(connection.getInputStream());
				bitmap = BitmapFactory.decodeStream(is);
				connection.disconnect();
				return bitmap;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return null;
	}
	
	public void showImageByAsyncTask(ImageView imageView,String url)
	{
		new NewsAsyncTask(imageView).execute(url);
	}
	
	private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap>
	{
		public ImageView mImageView;
		
		public NewsAsyncTask(ImageView imageView)
		{
			mImageView = imageView;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			return getBitmapFromURL(params[0]);
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mImageView.setImageBitmap(result);
		}
	}
	
}
