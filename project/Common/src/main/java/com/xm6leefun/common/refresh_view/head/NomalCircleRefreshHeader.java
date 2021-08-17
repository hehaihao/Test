package com.xm6leefun.common.refresh_view.head;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xm6leefun.common.refresh_view.widget.CircleView;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import androidx.annotation.NonNull;

public class NomalCircleRefreshHeader extends RelativeLayout implements RefreshHeader {
	private final static String TAG = NomalCircleRefreshHeader.class.getSimpleName();

	private String mPullDownRefreshText = "下拉刷新";
	private String mReleaseRefreshText = "松手刷新";
	private String mRefreshingText = "正在加载...";
	private String mRefreshCompleteText = "刷新完成";

	private CircleView mCircleView;
	private TextView mDescText;
	private ObjectAnimator mRotateAnim;

	public NomalCircleRefreshHeader(Context context) {
		this(context, null);
	}

	public NomalCircleRefreshHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews();
		initAnim();
	}

	private void initViews() {
		int size = (int) (20 * getResources().getDisplayMetrics().density);
		int padding  = (int) (15 * getResources().getDisplayMetrics().density);
		mCircleView = new CircleView(getContext());

		LayoutParams params = new LayoutParams(0, 0);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		View centerView = new View(getContext());
		centerView.setId(View.generateViewId());
		centerView.setLayoutParams(params);

		mDescText = new TextView(getContext());
		mDescText.setTextSize(14);
		mDescText.setTextColor(Color.parseColor("#FFACACAC"));
		TextPaint textPaint = mDescText.getPaint();
		float descWidth = textPaint.measureText(mPullDownRefreshText);
		LayoutParams descParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		descParams.addRule(RelativeLayout.RIGHT_OF, centerView.getId());
		descParams.leftMargin = (int) (15 * getResources().getDisplayMetrics().density - descWidth / 2);
		mDescText.setLayoutParams(descParams);

		LayoutParams circleParams = new LayoutParams(size, size);
		circleParams.addRule(RelativeLayout.LEFT_OF, centerView.getId());
		circleParams.rightMargin = (int) (descWidth / 2 - 5 * getResources().getDisplayMetrics().density);
		mCircleView.setLayoutParams(circleParams);

		addView(centerView);
		addView(mCircleView);
		addView(mDescText);

		setPadding(0, padding, 0, padding);
		setBackgroundColor(Color.WHITE);
	}

	private void initAnim() {
		mRotateAnim = ObjectAnimator.ofFloat(mCircleView, "rotation", 0, 360)
				.setDuration(500);
		mRotateAnim.setRepeatMode(ValueAnimator.RESTART);
		mRotateAnim.setRepeatCount(ValueAnimator.INFINITE);
	}

	@NonNull
	@Override
	public View getView() {
		return this;
	}

	@NonNull
	@Override
	public SpinnerStyle getSpinnerStyle() {
		return SpinnerStyle.Translate;
	}

	@Override
	public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
		if (isDragging) {
			float angle = percent * 360;
			angle = 0.95f * (angle > 360 ? 360 : angle);
			mCircleView.setSweepAngle(angle);
		}
	}

	@Override
	public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {
		if (mRotateAnim != null) {
			mRotateAnim.start();
		}
	}

	@Override
	public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
		if (mRotateAnim != null) {
			mRotateAnim.cancel();
		}
		mCircleView.showArrowSign(false);
		mDescText.setText(mRefreshCompleteText);
		return 500;
	}

	@Override
	public boolean isSupportHorizontalDrag() {
		return false;
	}

	@Override
	public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
		switch (newState) {
			case None:
			case PullDownToRefresh:
				mCircleView.showArrowSign(true);
				mDescText.setText(mPullDownRefreshText);
				mCircleView.animate().rotation(0f);
				break;
			case Refreshing:
			case RefreshReleased:
				mCircleView.showArrowSign(false);
				mDescText.setText(mRefreshingText);
				break;
			case ReleaseToRefresh:
				mCircleView.showArrowSign(false);
				mDescText.setText(mReleaseRefreshText);
				break;
		}
	}

	@Override
	public void setPrimaryColors(int... colors) {

	}

	@Override
	public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

	}

	@Override
	public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {

	}

	@Override
	public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

	}

	public void setPullDownRefreshText(String pullDownRefreshText) {
		this.mPullDownRefreshText = pullDownRefreshText;
	}

	public void setReleaseRefreshText(String releaseRefreshText) {
		this.mReleaseRefreshText = releaseRefreshText;
	}


	public void setRefreshingText(String refreshingText) {
		this.mRefreshingText = refreshingText;
	}

	public void setRefreshCompleteText(String refreshCompleteText) {
		this.mRefreshCompleteText = refreshCompleteText;
	}
}
