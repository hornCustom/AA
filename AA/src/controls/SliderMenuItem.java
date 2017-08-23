package controls;

public class SliderMenuItem {

	private int mItemID;
	private String mTitle;
	
	public SliderMenuItem(int pItemID,String pTitle)
	{
		mItemID = pItemID;
		mTitle = pTitle;
		
	}
	
	public int getmItemID() {
		return mItemID;
	}
	public void setmItemID(int pItemID) {
		this.mItemID = pItemID;
	}
	public String getTitle() {
		return mTitle;
	}
	public void setmTitle(String pTitle) {
		this.mTitle = pTitle;
	}

	
}
