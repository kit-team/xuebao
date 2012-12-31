package cn.net.yto.ui;

import android.os.Bundle;
import android.view.View;

/**
 * ViewPage's item view interface
 * @author LL
 *
 */
public interface ViewPageItem {
	
	void onCreate(Bundle savedInstanceState);

    void onStart();
    
    void onRestart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
    
    boolean onScanned(String barcode);
    
    /**
     * Get view which add to ViewPager
     */
    View getItemView();
    
    /**
     * This method will be invoked when this page selected
     */
    void onPageSelected();

}
