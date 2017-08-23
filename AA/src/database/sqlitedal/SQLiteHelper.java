package database.sqlitedal;

import java.util.ArrayList;

import utility.Reflection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
	
	private static SQLliteDataBaseConfig SQLITE_DATEBASE_CONFIG;
	private static SQLiteHelper INSTANCE;
	private Context mContext;
	private Reflection mReflection;
	
	public interface SQLiteDataTable{
		public void OnCreate(SQLiteDatabase p_DataBase);
		public void onUpgrade(SQLiteDatabase p_DataBase);
	}
	
	public SQLiteHelper(Context pContext){
		super(pContext,SQLITE_DATEBASE_CONFIG.GetDataBaseName(),null,SQLITE_DATEBASE_CONFIG.GetVersion());
		mContext = pContext;
	}
	
	public static SQLiteHelper GetInstance (Context pContext) {
		if (INSTANCE == null) {
			SQLITE_DATEBASE_CONFIG = SQLliteDataBaseConfig.GetInstance(pContext);
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
				SQLiteDataTable _SQLiteDataTable = (SQLiteDataTable) mReflection.newInstance(
						_ArrayList.get(i),
						new Object[]{mContext}, 
						new Class[]{Context.class});
				_SQLiteDataTable.OnCreate(pDataBase);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
