package com.example.multithreadding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView mListView;
	private static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView)findViewById(R.id.lv_main);
		new NewsAsyncTask().execute(URL);
	}
	//将Url对应的JSON格式数据转化为我们所封装的NewsBean
	private  List<NewsBean> getJsonData(String Url) {
		List<NewsBean> newsBeanList = new ArrayList<>();			
			try {
				String jsonString = readStream(new java.net.URL(Url).openStream());
				JSONObject jsonObject;
				NewsBean newsBean;
				try {
					jsonObject = new JSONObject(jsonString);
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for(int i = 0;i < jsonArray.length(); i++)
					{
						jsonObject = jsonArray.getJSONObject(i);
						newsBean = new NewsBean();
						newsBean.newsIconURL = jsonObject.getString("picSmall");
						newsBean.newsTitle = jsonObject.getString("name");
						newsBean.newsContent = jsonObject.getString("description");
						newsBeanList.add(newsBean);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return newsBeanList;
		
		
	}
	
	//通过is解析网页返回的数据
	private String readStream(InputStream is)
	{
		InputStreamReader isr;
		String result = "";
		try {
			String line = "";
			isr = new InputStreamReader(is , "utf-8");
			BufferedReader br = new BufferedReader(isr);
			while((line = br.readLine()) != null)
			{
				result += line;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	//实现网络的异步访问
	class NewsAsyncTask extends AsyncTask<String, Void, List<NewsBean>>
	{

		@Override
		protected List<NewsBean> doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			return getJsonData(params[0]);
		}
		@Override
		protected void onPostExecute(List<NewsBean> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			NewsAdapter adapter = new NewsAdapter(MainActivity.this, result);
			mListView.setAdapter(adapter);
		}
	}
}
