package com.xgc1986.parallaxPagerTransformer;

import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ParallaxPagerTransformer implements ViewPager.PageTransformer {
    private int mBorder;
    private List<ParallaxItem> mItems;

    public ParallaxPagerTransformer(List<ParallaxItem> items) {
        mItems = items;
    }

    @Override
    public void transformPage(View view, float position) {
        for (ParallaxItem item : mItems) {
            parallax(item, view, position);
        }
    }

    private void parallax(ParallaxItem item, View view, float position) {
        View parallaxView = view.findViewById(item.getId());

        if (parallaxView == null) {
            Log.w("ParallaxPager", "There is no view");
            return;
        }

        if (position > -1 && position < 1) {
            float width = parallaxView.getWidth();
            parallaxView.setTranslationX(-(position * width * item.getSpeed()));
            float sc = ((float) view.getWidth() - mBorder) / view.getWidth();
            if (position == 0) {
                view.setScaleX(1);
                view.setScaleY(1);
            } else {
                view.setScaleX(sc);
                view.setScaleY(sc);
            }
        }
    }

    public void setBorder(int border) {
        mBorder = border;
    }

    public static class Builder {
        private int border;
        List<ParallaxItem> items;

        public Builder() {
            items = new ArrayList<>();
        }

        public Builder addItem(ParallaxItem item) {
            items.add(item);
            return this;
        }

        public Builder addItem(@IdRes int id, float speed) {
            items.add(new ParallaxItem(id, speed));
            return this;
        }

        public Builder border(int border) {
            this.border = border;
            return this;
        }

        public ParallaxPagerTransformer build() {
            ParallaxPagerTransformer pt = new ParallaxPagerTransformer(items);
            pt.setBorder(border);
            return pt;
        }
    }
}