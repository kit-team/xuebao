package cn.net.yto.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.widget.Toast;
import cn.net.yto.R;
import cn.net.yto.net.ZltdHttpClient;

/**
 * 
 * @author fubo ProgressDialog �� Toast�ĸ�����
 */
public class DialogHelper {
	private static ProgressDialog sProgressDialog;
	private static Toast sToast;
	private static Timer sTimer;
	private static AlertDialog sAlertDialog;

	/**
	 * show ProgressDialog
	 * 
	 * @param context
	 * @param msgId
	 *            ProgressDialog��msg
	 */
	public static void showProgressDialog(Context context, int msgId) {
		String msg = context.getString(msgId);
		showProgressDialog(context, msg);
	}

	public static void showProgressDialog(Context context, int msgId,
			long maxWaitTime) {
		String msg = context.getString(msgId);
		showProgressDialog(context, msg, maxWaitTime);
	}

	/**
	 * show ProgressDialog
	 * 
	 * @param context
	 * @param msg
	 *            ProgressDialog��msg
	 */
	public static void showProgressDialog(Context context, String msg) {
		showProgressDialog(context, msg, 70 * 1024);
	}

	public static void showProgressDialog(Context context, String msg,
			long maxWaitTime) {
		closeProgressDialog();
		sProgressDialog = ProgressDialog.show(context,
				context.getString(R.string.alert_title), msg, false, false);
		sTimer = new Timer();
		sTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				closeProgressDialog();
			}
		}, maxWaitTime);
	}
	
	public static void setProgressMsg(String msg) {
		if (sProgressDialog != null) {
			sProgressDialog.setMessage(msg);
		}
	}

	public static void showAlertDialog(Activity activity, String msg,
			final DialogInterface.OnClickListener positiveListener,
			final DialogInterface.OnClickListener negativeListener) {
		closeAlertDialog();

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setIcon(R.drawable.query_dialog_icon);
		builder.setTitle(R.string.alert_title);
		builder.setMessage(msg);
		builder.setPositiveButton(R.string.login_ok, positiveListener);
		builder.setNegativeButton(R.string.login_no, negativeListener);
		sAlertDialog = builder.create();
		sAlertDialog.show();

		sAlertDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				boolean result = true;
				boolean isKeyUp = false;

				if (event != null && event.getAction() == KeyEvent.ACTION_UP) {
					isKeyUp = true;
				}

				switch (keyCode) {
				case 32: //F3
					if(isKeyUp && positiveListener != null){
						positiveListener.onClick(sAlertDialog, 0);
					}
					break;

				case 34: //F4
					if(isKeyUp && negativeListener != null){
						negativeListener.onClick(sAlertDialog, 0);
					}
					break;

				default:
					result = false;
					break;
				}
				return result;
			}
		});
	}

	public static void closeAlertDialog() {
		if (sAlertDialog != null) {
			sAlertDialog.dismiss();
			sAlertDialog = null;
		}
	}

	public static void closeProgressDialog() {
		if (sProgressDialog != null) {
			sProgressDialog.dismiss();
			sProgressDialog = null;
		}

		if (sTimer != null) {
			sTimer.cancel();
			sTimer = null;
		}
	}

	public static void showToast(Context context, int msgId) {
		colseToast();
		sToast = Toast.makeText(context, msgId, Toast.LENGTH_LONG);
		sToast.show();
	}

	public static void showToast(Context context, String msg) {
		colseToast();
		sToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		sToast.show();
	}

	public static void colseToast() {
		if (sToast != null) {
			sToast.cancel();
			sToast = null;
		}
	}

	public static boolean isProgressDialogShowing() {
		return sProgressDialog != null;
	}

	public static void showNetworkErrorTips(Context context,
			int networkResultType) {
		if (networkResultType == ZltdHttpClient.TYPE_ERROR_404) {
			showToast(context, R.string.http_404_tips);
		} else if (networkResultType == ZltdHttpClient.TYPE_ERROR_NETWORK_DEACTIVE) {
			showToast(context, R.string.http_no_active_network_tips);
			// } else if (networkResultType == HttpAsyncTask.TYPE_ERROR_OHTER) {
			// showToast(context, R.string.network_error);
			// }
		} else {
			showToast(context, R.string.network_error);
		}
	}
}
