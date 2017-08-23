package adapter;


import com.example.aa.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterAppGrid extends BaseAdapter {
    
	private class Holder
	{
		ImageView ivIcon;
		TextView tvName;
	} 
	
	private Integer[] mImageInteger = {
			
			R.drawable.jilu,
			R.drawable.chaxun,
			R.drawable.tongji,
			R.drawable.zhangben,
			R.drawable.leibie,
			R.drawable.renyuan,
			
			
	};
	
	private String[] mImageString = new String[6];
	
	private Context mContext;
	
	public AdapterAppGrid(Context Context )
	{
		mContext = Context;
	mImageString[0] = Context.getString(R.string.jiluxiaofei);
	mImageString[1] = Context.getString(R.string.chaxunxiaofei);
	mImageString[2] = Context.getString(R.string.tongjiguanli);
	mImageString[3] = Context.getString(R.string.zhangbenguanli);
	mImageString[4] = Context.getString(R.string.leibietongji);
	mImageString[5] = Context.getString(R.string.renyuanguanli);
	
   
    }



	@Override
	public int getCount() {
		return mImageString.length;
	}

	@Override
	public Object getItem(int position) {
		return mImageString[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder _Holder;
		
		if (convertView == null) {
			LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
			convertView = _LayoutInflater.inflate(R.layout.main_body_item, null);
			_Holder = new Holder();
			
			_Holder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
			_Holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			convertView.setTag(_Holder);
		}
		else{
			_Holder = (Holder) convertView.getTag();
		}
		
		_Holder.ivIcon.setImageResource(mImageInteger[position]);
		
		LinearLayout.LayoutParams _laoyutParams = new LinearLayout.LayoutParams(60,60);
		_Holder.ivIcon.setLayoutParams(_laoyutParams);
		_Holder.ivIcon.setScaleType(ScaleType.FIT_XY);
		
		_Holder.tvName.setText(mImageString[position]);
		return convertView;
	}

}
