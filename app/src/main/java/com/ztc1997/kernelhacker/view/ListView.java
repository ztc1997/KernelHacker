/*
 * Copyright (c) 2014 Android Alliance, LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ztc1997.kernelhacker.view;

import android.content.Context;
import android.util.AttributeSet;

public class ListView extends android.widget.ListView {

    private OnScrollListener mLegacyOnScrollListener;
    private final MulticastOnScrollListener mMulticastOnScrollListener = new MulticastOnScrollListener();

    public ListView(Context context) {
        this(context, null);
    }

    public ListView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.listViewStyle);
    }

    public ListView(Context context, AttributeSet attrs, int defStyle) {
        super(new ContextWrapperEdgeEffect(context), attrs, defStyle);
    }

    public void setOnScrollListener(OnScrollListener listener) {
        if (listener != null) {
            checkPrecondition(mLegacyOnScrollListener == null);
            mLegacyOnScrollListener = listener;
            addOnScrollListener(mLegacyOnScrollListener);
        } else if (mLegacyOnScrollListener != null) {
            removeOnScrollListener(mLegacyOnScrollListener);
            mLegacyOnScrollListener = null;
        }
    }

    public void setEdgeEffectColor(int edgeEffectColor) {
        ((ContextWrapperEdgeEffect) getContext()).setEdgeEffectColor(edgeEffectColor);
    }

    public void addOnScrollListener(OnScrollListener listener) {
        mMulticastOnScrollListener.add(listener);
    }

    public void clearOnScrollListeners() {
        mMulticastOnScrollListener.clear();
    }

    public void removeOnScrollListener(OnScrollListener listener) {
        mMulticastOnScrollListener.remove(listener);
    }

    void checkPrecondition(boolean state) {
        if (!state) {
            throw new IllegalStateException();
        }
    }
}