package com.mobidever.adapter;

import java.util.List;

import com.mobidever.adapter.base.AdapterBase;
import com.mobidever.business.BusinessAccountBook;
import com.mobidever.model.ModelAccountBook;

import com.mobidever.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterAccountBookSelect extends AdapterBase {

	private class Holder
	{
		ImageView Icon;
		TextView Name;
	}
	
	public AdapterAccountBookSelect(Context p_Context)
	{
		this(p_Context,null);
		BusinessAccountBook _BusinessAccountBook = new BusinessAccountBook(p_Context);
		List _List = _BusinessAccountBook.GetAccountBook("");
		SetList(_List);
	}
	
	public AdapterAccountBookSelect(Context p_Context, List p_List) {
		super(p_Context, p_List);
	}

	public View getView(int p_Position, View p_ConvertView, ViewGroup p_Parent) {
		Holder _Holder;
		
		if (p_ConvertView == null) {
			p_ConvertView = GetLayoutInflater().inflate(R.layout.account_book_select_list_item, null);
			_Holder = new Holder();
			_Holder.Icon = (ImageView)p_ConvertView.findViewById(R.id.AccountBookIcon);
			_Holder.Name = (TextView)p_ConvertView.findViewById(R.id.AccountBookName);
			p_ConvertView.setTag(_Holder);
		}
		else {
			_Holder = (Holder) p_ConvertView.getTag();
		}
		
		ModelAccountBook _ModelAccountBook = (ModelAccountBook)getItem(p_Position);
		_Holder.Icon.setImageResource(R.drawable.account_book_small_icon);
		_Holder.Name.setText(_ModelAccountBook.GetAccountBookName());
		
		return p_ConvertView;
	}
	
}
