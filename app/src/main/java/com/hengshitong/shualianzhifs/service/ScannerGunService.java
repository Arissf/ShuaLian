package com.hengshitong.shualianzhifs.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

public class ScannerGunService extends AccessibilityService {

    private static OnKeyEvent onKeyEvent;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        if(onKeyEvent!=null){
            //这里通过回调的方式将事件传出去统一处理
            //返回true事件就会拦截不会继续传递


            return onKeyEvent.onKeyEvent(event);
        }
        return super.onKeyEvent(event);
    }
    /**
     * 设置监听
     * @param onKeyEvent
     */
    public static void setOnKeyEvent(OnKeyEvent onKeyEvent){
        ScannerGunService.onKeyEvent=onKeyEvent;
    }
    public interface OnKeyEvent{
        boolean onKeyEvent(KeyEvent event);
    }


    //获取扫描内容
    private char getInputCode(KeyEvent event) {

        int keyCode = event.getKeyCode();

        char aChar;


        aChar = (char) event.getUnicodeChar();


        return aChar;

    }





}
