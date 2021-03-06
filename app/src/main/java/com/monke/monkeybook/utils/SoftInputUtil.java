package com.monke.monkeybook.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.monke.monkeybook.widget.flowlayout.TagFlowLayout;

public class SoftInputUtil {

    //隐藏输入法
    public static void hideIMM(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void resetBoxPosition(Activity activity, View prentView, int viewId) {

        final View decorView = (activity).getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            try {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = getScreenHeight(activity);
                int heightDifference = screenHeight - rect.bottom;//计算软键盘占有的高度  = 屏幕高度 - 视图可见高度
                View view = prentView.findViewById(viewId);
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                layoutParams.setMargins(TagFlowLayout.dip2px(activity, 20), 0, TagFlowLayout.dip2px(activity, 20), heightDifference);//设置rlContent的marginBottom的值为软键盘占有的高度即可
                view.setLayoutParams(layoutParams);
                view.requestLayout();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static int getScreenHeight(Activity activity) {
        WindowManager manager = (activity).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
