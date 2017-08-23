package com.mobidever.adapter;

import java.util.List;

import com.mobidever.adapter.base.AdapterBase;
import com.mobidever.business.BusinessUser;
import com.mobidever.controls.SlideMenuItem;
import com.mobidever.model.ModelUser;

import com.mobidever.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterUser extends AdapterBase {
	
	private class Holder
	{
		ImageView ivUserIcon;
		TextView tvUserName;
	}
	
	public AdapterUser(Context pContext) {
		super(pContext, null);
		BusinessUser _BusinessUser = new BusinessUser(pContext);
		List _List = _BusinessUser.GetNotHideUser();
		SetList(_List);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Holder _Holder;
		
		if (convertView == null) {
			convertView = GetLayoutInflater().inflate(R.layout.user_list_item, null);
			_Holder = new Holder();
			_Holder.ivUserIcon = (ImageView) convertView.findViewById(R.id.ivUserIcon);
			_Holder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
			convertView.setTag(_Holder);
		}
		else {
			_Holder = (Holder) convertView.getTag();
		}
		
		ModelUser _Info = (ModelUser) GetList().get(position);
		
		_Holder.ivUserIcon.setImageResource(R.drawable.user_small_icon);
		_Holder.tvUserName.setText(_Info.getUserName());
		
		return convertView;
	}

}
