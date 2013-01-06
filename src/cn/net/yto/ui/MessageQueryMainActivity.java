package cn.net.yto.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.net.yto.R;
import cn.net.yto.ui.menu.ListMenuItemAdapter;
import cn.net.yto.ui.menu.MenuAction;
import cn.net.yto.ui.menu.MenuItem;

public class MessageQueryMainActivity extends Activity {
    private static final int ITEM_VIEW_HELP = 0;
    private static final int ITEM_DISPATCH_SCOPE_QUERY = 1;
    private static final int ITEM_CONTRABND_CHECK = 2;
    private static final int ITEM_STANDARD_PRICE = 3;
    private static final int ITEM_CUSTOMER_VERIFY = 4;
    private static final int ITEM_EXPRESS_TRACK = 6;
    private static final int ITEM_BULLETIN_INFO_SEARCH = 7;
    private static final int ITEM_RECEIVE_DELIVER_STATISTIC = 8;
    private static final int ITEM_CUSTOMER_BLACK_LIST = 5;
    private static final int ITEM_ESC = 9;

    private static final int[] MENU_IDS = {
        ITEM_VIEW_HELP,
        ITEM_DISPATCH_SCOPE_QUERY,
        ITEM_CONTRABND_CHECK,
        ITEM_STANDARD_PRICE,
        ITEM_CUSTOMER_VERIFY,
        ITEM_EXPRESS_TRACK,
        ITEM_BULLETIN_INFO_SEARCH,
        ITEM_RECEIVE_DELIVER_STATISTIC,
        ITEM_CUSTOMER_BLACK_LIST,
        ITEM_ESC
    };

    private final static int[] ICONS = {
        R.drawable.sign_batch,
        R.drawable.sign_batch,
        R.drawable.sign_batch,
        R.drawable.sign_batch,
        R.drawable.sign_batch,
        R.drawable.sign_batch,
        R.drawable.sign_batch,
        R.drawable.sign_batch,
        R.drawable.delete_sign_record,
        R.drawable.back
    };

    private String[] mTaskLabels;

    private ListView mListView = null;

    private ListMenuItemAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        setContentView(R.layout.message_query_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.dispatch_main_title);

        mTaskLabels = getResources().getStringArray(R.array.message_query_main);
        initViews();
    }

    private void initViews() {
        ArrayList<MenuItem> menuItemList = new ArrayList<MenuItem>();
        for (int i = 0; i < ICONS.length; i++) {
            MenuItem menuItem = new MenuItem(ICONS[i], mTaskLabels[i], new MenuActionListener(
                    MENU_IDS[i]));
            menuItemList.add(menuItem);
        }

        mListView = (ListView) findViewById(R.id.list);
        mAdapter = new ListMenuItemAdapter(this, menuItemList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    private void launchSignPrompt() {
        final Intent intent = new Intent(this, SignPromptActivity.class);
        startActivity(intent);
    }

    private final OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            mAdapter.getItem(position).doAction();
        }
    };

    private class MenuActionListener implements MenuAction {
        private int mMenuId = -1;

        public MenuActionListener(int id) {
            mMenuId = id;
        }

        @Override
        public void action() {
            switch (mMenuId) {
            case ITEM_EXPRESS_TRACK:
                return;
            case ITEM_BULLETIN_INFO_SEARCH:
                return;
            case ITEM_RECEIVE_DELIVER_STATISTIC:
                return;
            case ITEM_ESC:
                finish();
                return;
            }
        }

    }
}
