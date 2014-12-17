package com.daimajia.slider.library.Tricks;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

/**
 * A {@link ViewPager} that allows pseudo-infinite paging with a wrap-around effect. Should be used with an {@link
 * InfinitePagerAdapter}.
 */
public class InfiniteViewPager extends ViewPagerEx {
	private boolean DEBUG = true;
    public InfiniteViewPager(Context context) {
        super(context);
    }

    public InfiniteViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        // offset first element so that we can scroll to the left
        DEBUG("setAdapter");
        setCurrentItem(0);
    }

    @Override
    public void setCurrentItem(int item) {
        // offset the current item to ensure there is space to scroll
    	if (getAdapter() instanceof InfinitePagerAdapter) {
            InfinitePagerAdapter infAdapter = (InfinitePagerAdapter) getAdapter();
            if(infAdapter.isInfinite())
            	item = getOffsetAmount() + (item % getAdapter().getCount());
    	}
    	DEBUG("setcurrentitem,item:"+item+",offset:"+getOffsetAmount()+"adapter count:"+getAdapter().getCount());
    	super.setCurrentItem(item);
    	getAdapter().notifyDataSetChanged();
    }
    public void previousItem(){
    	DEBUG("previousItem");
    	super.setCurrentItem(getCurrentItem() - 1);
    }
    public void nextItem(){
    	DEBUG("nextItem");
    	super.setCurrentItem(getCurrentItem() + 1);
    }
    
    public int getOffsetAmount() {
        if (getAdapter() instanceof InfinitePagerAdapter) {
            InfinitePagerAdapter infAdapter = (InfinitePagerAdapter) getAdapter();
            // allow for 100 back cycles from the beginning
            // should be enough to create an illusion of infinity
            // warning: scrolling to very high values (1,000,000+) results in
            // strange drawing behaviour
           
            if(infAdapter.isInfinite()){
            	 int RealCountOffset = infAdapter.getRealCount() * 100;
            	 return RealCountOffset;
            }else
            	return 0;
        } else {
            return 0;
        }
    }
    private void DEBUG(String message){
	    if(DEBUG){
	    	Log.e(InfiniteViewPager.class.getSimpleName(),message);
	    }
    }
}