package adapter;

import java.util.List;

import com.example.aa.R;

import controls.SliderMenuItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AdapterSlideMenu extends AdapterBase {
	
	private class Holder
	{
      
	  TextView tvMenuName;	
	}
	public AdapterSlideMenu(Context pContext, List pList) {
		super(pContext,pList);
	}

	public View getView(int position,View convertView, ViewGroup parent){
		Holder _Holder;
		
		if(convertView == null){
		   convertView = GetLayoutInflater().inflate(R.layout.slidemenu_list_item,null);
		   _Holder =new Holder();
		   _Holder.tvMenuName = (TextView)convertView.findViewById(R.id.tvMenuName);
		   convertView.setTag(_Holder);
		}
		else {
			_Holder = (Holder) convertView.getTag();
		}
		
		SliderMenuItem _Item = (SliderMenuItem) GetList().get(position);
		
		_Holder.tvMenuName.setText(_Item.getTitle());
		
		return convertView;
		
	
	}

}
