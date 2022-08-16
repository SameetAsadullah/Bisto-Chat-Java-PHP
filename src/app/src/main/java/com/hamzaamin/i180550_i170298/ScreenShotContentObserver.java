package com.hamzaamin.i180550_i170298;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class ScreenShotContentObserver extends ContentObserver {

    private Context context;
    private boolean isFromEdit = false;
    private String previousPath;

    public ScreenShotContentObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        try (Cursor cursor = context.getContentResolver().query(uri, new String[]{
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA
        }, null, null, null)) {
            if (cursor != null && cursor.moveToLast()) {
                int displayNameColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
                int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                String fileName = cursor.getString(displayNameColumnIndex);
                String path = cursor.getString(dataColumnIndex);
                if (new File(path).lastModified() >= System.currentTimeMillis() - 10000) {
                    if (isScreenshot(path) && !isFromEdit && !(previousPath != null && previousPath.equals(path))) {
                        onScreenShot(path, fileName);
                    }
                    previousPath = path;
                    isFromEdit = false;
                } else {
                    cursor.close();
                    return;
                }
            }
        } catch (Throwable t) {
            isFromEdit = true;
        }
        super.onChange(selfChange, uri);
    }

    private boolean isScreenshot(String path) {
        return path != null && path.toLowerCase().contains("screenshot");
    }

    protected abstract void onScreenShot(String path, String fileName) throws FileNotFoundException;

}