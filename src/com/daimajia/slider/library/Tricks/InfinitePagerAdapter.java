package com.daimajia.slider.library.Tricks;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderAdapter;

/**
 * A PagerAdapter that wraps around another PagerAdapter to handle paging wrap-around.
 * Thanks to: https://github.com/antonyt/InfiniteViewPager
 */
public class InfinitePagerAdapter extends PagerAdapter {

    private static final String TAG = "InfinitePagerAdapter";
    private static final boolean DEBUG = false;

    private SliderAdapter adapter;
    //20141208 設置isinfinite參數,給播放頁設定是否要循環播放用
    private boolean isInfinite = true;
    public boolean isInfinite() {
		return isInfinite;
	}
	public void setInfinite(boolean isInfinite) {
		this.isInfinite = isInfinite;
//		notifyDataSetChanged();
	}
	public InfinitePagerAdapter(SliderAdapter adapter) {
//        this.adapter = adapter;
        this(adapter,true);
    }
    public InfinitePagerAdapter(SliderAdapter adapter,boolean isInfinite) {
        this.adapter = adapter;
        this.isInfinite = isInfinite;
    }
    public SliderAdapter getRealAdapter(){
        return this.adapter;
    }

    @Override
    public int getCount() {
        // warning: scrolling to very high values (1,000,000+) results in
        // strange drawing behaviour
    	debug("getCount");
    	if(isInfinite)
    		return Integer.MAX_VALUE;
    	else
    		return getRealCount();
    }

    /**
     * @return the {@link #getCount()} result of the wrapped adapter
     */
    public int getRealCount() {
    	debug("getRealCount:"+adapter.getCount());
        return adapter.getCount();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if(getRealCount() == 0){
            return null;
        }
        int virtualPosition = position % getRealCount();
        if(!isInfinite){
        	virtualPosition = position;
        }
        debug("instantiateItem: real position: " + position);
        debug("instantiateItem: virtual position: " + virtualPosition);

        // only expose virtual position to the inner adapter
        return adapter.instantiateItem(container, virtualPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(getRealCount() == 0){
            return;
        }
        int virtualPosition = position % getRealCount();
        debug("destroyItem: real position: " + position);
        debug("destroyItem: virtual position: " + virtualPosition);

        // only expose virtual position to the inner adapter
        adapter.destroyItem(container, virtualPosition, object);
    }

    /*
     * Delegate rest of methods directly to the inner adapter.
     */

    @Override
    public void finishUpdate(ViewGroup container) {
        adapter.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return adapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable bundle, ClassLoader classLoader) {
        adapter.restoreState(bundle, classLoader);
    }

    @Override
    public Parcelable saveState() {
        return adapter.saveState();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        adapter.startUpdate(container);
    }

    /*
     * End delegation
     */

    private void debug(String message) {
        if (DEBUG) {
            Log.e(TAG, message);
        }
    }
}