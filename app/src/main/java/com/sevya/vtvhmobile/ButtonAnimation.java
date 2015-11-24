package com.sevya.vtvhmobile;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;


public class ButtonAnimation {

    public static void animation(View view)
    {
        AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
        animation1.setDuration(1000);
        animation1.setStartOffset(50);
        animation1.setFillAfter(true);
        view.startAnimation(animation1);
    }

}
