package com.example.project2_tvbox_ui_2;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class Swiper implements View.OnTouchListener  {
    public GestureDetector gestureDetector;
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }
    public Swiper(Context context)
    {
        gestureDetector = new GestureDetector(context,new GestureLister());
    }
    private final class GestureLister extends GestureDetector.SimpleOnGestureListener
    {

        public static final int SWIPE_THRESOLD=100;
        public static final int SWIPE_VELOCITY_THRESOLD=100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            boolean result= false;
            float yD= e2.getY()-e1.getY();
            float xD= e2.getX()-e1.getX();
            if(Math.abs(xD)>Math.abs(yD))
            {
                if(Math.abs(xD)>SWIPE_THRESOLD && Math.abs(velocityX)>SWIPE_VELOCITY_THRESOLD)
                {
                    if(xD>0)
                    {
                        onSwipeRight();
                    }
                    else
                    {
                        onSwipeLeft();
                    }
                    result= true;
                }
            }
            else if(Math.abs(yD)>SWIPE_THRESOLD && Math.abs(velocityY)>SWIPE_VELOCITY_THRESOLD)

            {
                if (yD>0)
                {
                    onSwipeBottom();
                }
                else
                {
                    onSwipeTop();
                }
                result= true;
            }
            return result;
        }
    }
    void onSwipeLeft(){}
    void onSwipeRight(){}
    void onSwipeTop(){}
    void onSwipeBottom(){}
}




