package cn.net.yto.ui.menu;



public class MenuItem {
	public int mDrawableResId;
	public int mTextResId;
	public MenuAction mAction;

	public MenuItem(int imgResId, int textResId, MenuAction action) {
		this.mDrawableResId = imgResId;
		this.mTextResId = textResId;
		this.mAction = action;
	}

	public void doAction() {
		if (mAction != null) {
			mAction.action();
		}
	}
}
