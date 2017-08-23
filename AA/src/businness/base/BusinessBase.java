package businness.base;

import android.content.Context;

public class BusinessBase {
	private Context mContext;

	protected BusinessBase(Context pContext) {
		 mContext = pContext;
	}
	protected String GetString(int pResID){
		return mContext.getString(pResID);
	}
	protected String GetString(int pResID,Object[] pObject){
		return mContext.getString(pResID, pObject);
	}
  
}
