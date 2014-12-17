package com.daimajia.slider.library.SliderTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.R;
/**
 * 播放頁-專輯圖滑動用view
 * */
public class AlbumSliderView extends BaseSliderView{
	private boolean isCDTagvisible = false;
	public AlbumSliderView(Context context) {
		super(context);
	}
	public AlbumSliderView isCDTagVisiable(boolean isvisible){
		isCDTagvisible = isvisible;
		return this;
	}
	@Override
	public View getView() {
		View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_album,null);
		ImageView cdtag = (ImageView)v.findViewById(R.id.albumImgTag);
		if(isCDTagvisible){
			cdtag.setVisibility(View.VISIBLE);
		}else
			cdtag.setVisibility(View.GONE);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        bindEventAndShow(v, target);
        return v;
	}

}
