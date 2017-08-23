package com.mobidever.adapter.base;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AdapterBase extends BaseAdapter {

	private List mList;
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	
	public AdapterBase(Context pContext,List pList)
	{
		mContext = pContext;
		mList = pList;
		mLayoutInflater = LayoutInflater.from(mContext);
	}
	
	public LayoutInflater GetLayoutInflater()
	{
		return mLayoutInflater;
	}
	
	public Context GetContext()
	{
		return mContext;
	}
	
	public List GetList()
	{
		return mList;
	}
	
	public void SetList(List pList) {
		mList = pList;
	}
	
	public int getCount() {
		return mList.size();
	}

	public Object getItem(int pPosition) {
		return mList.get(pPosition);
	}

	public long getItemId(int pPosition) {
		return pPosition;
	}

}
