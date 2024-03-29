package cn.net.yto.ui.menu;



public class MenuItem {
    public int mDrawableResId;
    public int mTextResId = -1;
    public String mTextLabel = null;
    public MenuAction mAction;
    public int mUnhandleMsg;

    public MenuItem(int imgResId, int textResId, MenuAction action) {
        this.mDrawableResId = imgResId;
        this.mTextResId = textResId;
        this.mAction = action;
    }

    public MenuItem(int imgResId, String label, MenuAction action) {
        this.mDrawableResId = imgResId;
        this.mTextLabel = label;
        this.mAction = action;
    }

	public void doAction() {
		if (mAction != null) {
			mAction.action();
		}
	}
}
