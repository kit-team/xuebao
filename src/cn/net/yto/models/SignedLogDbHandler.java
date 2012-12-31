//package cn.net.yto.models;
//
//import android.content.AsyncQueryHandler;
//import android.content.ContentResolver;
//import android.database.Cursor;
//import android.net.Uri;
//
//public class SignedLogDbHandler extends AsyncQueryHandler {
//    
//    public interface DbHandlerListener {
//        void onDeleteComplete(int token, Object cookie, int result);
//
//        void onInsertComplete(int token, Object cookie, Uri uri);
//
//        void onQueryComplete(int token, Object cookie, Cursor cursor);
//
//        void onUpdateComplete(int token, Object cookie, int result);
//    }
//
//    public SignedLogDbHandler(ContentResolver cr) {
//        super(cr);
//    }
//
//    @Override
//    public void startQuery(int token, Object cookie, Uri uri, String[] projection,
//            String selection, String[] selectionArgs, String orderBy) {
//        super.startQuery(token, cookie, uri, projection, selection, selectionArgs, orderBy);
//    }
//
//    @Override
//    protected void onDeleteComplete(int token, Object cookie, int result) {
//        super.onDeleteComplete(token, cookie, result);
//        if (cookie instanceof DbHandlerListener){
//            ((DbHandlerListener)cookie).onDeleteComplete(token, cookie, result);
//        }
//    }
//
//    @Override
//    protected void onInsertComplete(int token, Object cookie, Uri uri) {
//        super.onInsertComplete(token, cookie, uri);
//        if (cookie instanceof DbHandlerListener){
//            ((DbHandlerListener)cookie).onInsertComplete(token, cookie, uri);
//        }
//    }
//
//    @Override
//    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
//        super.onQueryComplete(token, cookie, cursor);
//        if (cookie instanceof DbHandlerListener){
//            ((DbHandlerListener)cookie).onQueryComplete(token, cookie, cursor);
//        }
//    }
//
//    @Override
//    protected void onUpdateComplete(int token, Object cookie, int result) {
//        super.onUpdateComplete(token, cookie, result);
//        if (cookie instanceof DbHandlerListener) {
//            ((DbHandlerListener) cookie).onUpdateComplete(token, cookie, result);
//        }
//    }
//}
