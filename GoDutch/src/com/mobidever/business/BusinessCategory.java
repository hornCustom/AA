package com.mobidever.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mobidever.business.base.BusinessBase;
import com.mobidever.database.sqlitedal.SQLiteDALCategory;
import com.mobidever.model.ModelCategory;
import com.mobidever.model.ModelCategoryTotal;

import com.mobidever.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

public class BusinessCategory extends BusinessBase {
	
	private SQLiteDALCategory mSqLiteDALCategory;
	private final String  TYPE_FLAG = " And TypeFlag= '" + GetString(R.string.PayoutTypeFlag) + "'";
	
	public BusinessCategory(Context p_Context)
	{
		super(p_Context);
		mSqLiteDALCategory = new SQLiteDALCategory(p_Context);
	}
	
	public Boolean InsertCategory(ModelCategory p_Info)
	{
		mSqLiteDALCategory.BeginTransaction();
		try {
			Boolean _Result = mSqLiteDALCategory.InsertCategory(p_Info);
			Boolean _Result2 = true;

			ModelCategory _ParentModelCategory = GetModelCategoryByCategoryID(p_Info.GetParentID());
			String _Path;
			if(_ParentModelCategory != null)
			{
				_Path = _ParentModelCategory.GetPath() + p_Info.GetCategoryID() + ".";
			}
			else {
				_Path = p_Info.GetCategoryID() + ".";
			}
			
			p_Info.SetPath(_Path);
			_Result2 = UpdateCategoryInsertTypeByCategoryID(p_Info);
			
			if(_Result && _Result2)
			{
				mSqLiteDALCategory.SetTransactionSuccessful();
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			mSqLiteDALCategory.EndTransaction();
		}
	}
	
	public Boolean DeleteCategoryByCategoryID(int p_CategoryID)
	{
		String _Condition = " CategoryID = " + p_CategoryID;
		Boolean _Result = mSqLiteDALCategory.DeleteCategory(_Condition);
		return _Result;
	}
	
	public Boolean DeleteCategoryByPath(String p_Path) throws Exception
	{
		
		return true;
	}
	
	public Boolean UpdateCategoryInsertTypeByCategoryID(ModelCategory p_Info)
	{
		String _Condition = " CategoryID = " + p_Info.GetCategoryID();
		Boolean _Result = mSqLiteDALCategory.UpdateCategory(_Condition, p_Info);		
		
		if(_Result)
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean UpdateCategoryByCategoryID(ModelCategory p_Info)
	{
		mSqLiteDALCategory.BeginTransaction();
		try {
			String _Condition = " CategoryID = " + p_Info.GetCategoryID();
			Boolean _Result = mSqLiteDALCategory.UpdateCategory(_Condition, p_Info);
			Boolean _Result2 = true;
			

			ModelCategory _ParentModelCategory = GetModelCategoryByCategoryID(p_Info.GetParentID());
			String _Path;
			if(_ParentModelCategory != null)
			{
				_Path = _ParentModelCategory.GetPath() + p_Info.GetCategoryID() + ".";
			}
			else {
				_Path = p_Info.GetCategoryID() + ".";
			}
			
			p_Info.SetPath(_Path);
			_Result2 = UpdateCategoryInsertTypeByCategoryID(p_Info);
			
			if(_Result && _Result2)
			{
				mSqLiteDALCategory.SetTransactionSuccessful();
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			mSqLiteDALCategory.EndTransaction();
		}
	}
	
	public Boolean HideCategoryByByPath(String p_Path)
	{
		String _Condition = " Path Like '" + p_Path + "%'";
		ContentValues _ContentValues = new ContentValues();
		_ContentValues.put("State",0);
		Boolean _Result = mSqLiteDALCategory.UpdateCategory(_Condition, _ContentValues);

		if(_Result)
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	public List<ModelCategory> GetCategory(String p_Condition)
	{
		return mSqLiteDALCategory.GetCategory(p_Condition);
	}
	
	public List<ModelCategory> GetNotHideCategory()
	{
		return mSqLiteDALCategory.GetCategory(TYPE_FLAG + " And State = 1");
	}
	
	public int GetNotHideCount()
	{
		return mSqLiteDALCategory.GetCount(TYPE_FLAG + " And State = 1");
	}
	
	public int GetNotHideCountByParentID(int p_CategoryID)
	{
		return mSqLiteDALCategory.GetCount(TYPE_FLAG + " And ParentID = " + p_CategoryID + " And State = 1");
	}
	
	public List<ModelCategory> GetNotHideRootCategory()
	{
		return mSqLiteDALCategory.GetCategory(TYPE_FLAG + " And ParentID = 0 And State = 1");
	}
	
	public List<ModelCategory> GetNotHideCategoryListByParentID(int p_ParentID)
	{
		return mSqLiteDALCategory.GetCategory(TYPE_FLAG + " And ParentID = " + p_ParentID + " And State = 1");
	}
	
	public ModelCategory GetModelCategoryByParentID(int p_ParentID)
	{
		List _List = mSqLiteDALCategory.GetCategory(TYPE_FLAG + " And ParentID = " + p_ParentID);
		if(_List.size() == 1)
		{
			return (ModelCategory)_List.get(0);
		}
		else
		{
			return null;
		}
	}
	
	public ModelCategory GetModelCategoryByCategoryID(int p_CategoryID)
	{
		List _List = mSqLiteDALCategory.GetCategory(TYPE_FLAG + " And CategoryID = " + p_CategoryID);
		if(_List.size() == 1)
		{
			return (ModelCategory)_List.get(0);
		}
		else
		{
			return null;
		}
	}
	
	public ArrayAdapter GetRootCategoryArrayAdapter()
	{
		List _List = GetNotHideRootCategory();
		_List.add(0,"--��ѡ��--");
		ArrayAdapter _ArrayAdapter = new ArrayAdapter(GetContext(), android.R.layout.simple_spinner_item, _List);
		_ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return _ArrayAdapter;
	}
	
	public ArrayAdapter GetAllCategoryArrayAdapter()
	{
	
		
		return null;
	}
	
	public List<ModelCategoryTotal> CategoryTotalByRootCategory()
	{
		String _Condition = TYPE_FLAG + " And ParentID = 0 And State = 1";
		List<ModelCategoryTotal> _ModelCategoryTotalList = CategoryTotal(_Condition);
		
		return _ModelCategoryTotalList;
	}
	
	public List<ModelCategoryTotal> CategoryTotalByParentID(int p_ParentID)
	{
		String _Condition = TYPE_FLAG + " And ParentID = " + p_ParentID;
		List<ModelCategoryTotal> _ModelCategoryTotalList = CategoryTotal(_Condition);
		
		return _ModelCategoryTotalList;
	}
	
	public List<ModelCategoryTotal> CategoryTotal(String p_Condition)
	{
		String _Condition = p_Condition;
		Cursor _Cursor = mSqLiteDALCategory.ExecSql("Select Count(PayoutID) As Count, Sum(Amount) As SumAmount, CategoryName From v_Payout Where 1=1 " + _Condition + " Group By CategoryName");
		List<ModelCategoryTotal> _ModelCategoryTotalList = new ArrayList<ModelCategoryTotal>();
		while (_Cursor.moveToNext()) {
			ModelCategoryTotal _ModelCategoryTotal = new ModelCategoryTotal();
			_ModelCategoryTotal.Count = _Cursor.getString(_Cursor.getColumnIndex("Count"));
			_ModelCategoryTotal.SumAmount = _Cursor.getString(_Cursor.getColumnIndex("SumAmount"));
			_ModelCategoryTotal.CategoryName = _Cursor.getString(_Cursor.getColumnIndex("CategoryName"));
			_ModelCategoryTotalList.add(_ModelCategoryTotal);
		}
		
		return _ModelCategoryTotalList;
	}
}
