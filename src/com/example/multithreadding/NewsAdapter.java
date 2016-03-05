package com.example.multithreadding;

import java.util.List;

import android.R.layout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter{

	private List<NewsBean> mList;
	private LayoutInflater mInflater;
	
	public NewsAdapter(Context context,List<NewsBean> data)
	{
		mList = data;
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_layout,null);
			viewHolder.ivIcon = (ImageView)convertView.findViewById(R.id.iv_icon);
			viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
			viewHolder.tvconten = (TextView)convertView.findViewById(R.id.tv_content);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.ivIcon.setImageResource(R.drawable.ic_launcher);
		String url = mList.get(position).newsIconURL;
		viewHolder.ivIcon.setTag(url);
		//new ImageLoader().showImageByThread(viewHolder.ivIcon, url);
		
		new ImageLoader().showImageByAsyncTask(viewHolder.ivIcon, url);
		viewHolder.tvTitle.setText(mList.get(position).newsTitle);
		viewHolder.tvconten.setText(mList.get(position).newsContent);
		
		return convertView;
	}
	
	class ViewHolder
	{
		public TextView tvTitle;
		public TextView tvconten;
		public ImageView ivIcon;
	}
}
