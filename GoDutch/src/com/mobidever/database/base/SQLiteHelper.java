package com.mobidever.database.base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.mobidever.utility.Reflection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	private static SQLiteDateBaseConfig SQLITE_DATEBASE_CONFIG;
	private static SQLiteHelper INSTANCE;
	private Context mContext;
	private Reflection mReflection;

	public interface SQLiteDataTable {
		public void OnCreate(SQLiteDatabase p_DataBase);

		public void OnUpgrade(SQLiteDatabase p_DataBase);
	}

	private SQLiteHelper(Context pContext) {
		super(pContext, SQLITE_DATEBASE_CONFIG.GetDataBaseName(), null,
				SQLITE_DATEBASE_CONFIG.GetVersion());
		mContext = pContext;
	}

	public static SQLiteHelper GetInstance(Context pContext) {
		if (INSTANCE == null) {
			SQLITE_DATEBASE_CONFIG = SQLiteDateBaseConfig.GetInstance(pContext);
			INSTANCE = new SQLiteHelper(pContext);
		}

		return INSTANCE;
	}

	@Override
	public void onCreate(SQLiteDatabase pDataBase) {
		ArrayList<String> _ArrayList = SQLITE_DATEBASE_CONFIG.GetTables();
		mReflection = new Reflection();

		for (int i = 0; i < _ArrayList.size(); i++) {
			try {
				((SQLiteDataTable) mReflection.newInstance(_ArrayList.get(i),
						new Object[] { mContext },
						new Class[] { Context.class })).OnCreate(pDataBase);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
