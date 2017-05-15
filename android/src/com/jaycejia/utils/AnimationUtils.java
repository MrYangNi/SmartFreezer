package com.jaycejia.utils;

import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

/**
 * Created by NiYang on 2017/5/15.
 */

public class AnimationUtils {

    /**
     * 按钮左右摇摆动画
     */
    public static void buttonAnimation(Button button) {
        TranslateAnimation translateAnimation = new TranslateAnimation(10,-10 , 0, 0);
        translateAnimation.setInterpolator(new OvershootInterpolator());
        translateAnimation.setDuration(30);
        translateAnimation.setRepeatCount(4);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        button.startAnimation(translateAnimation);
    }
}
