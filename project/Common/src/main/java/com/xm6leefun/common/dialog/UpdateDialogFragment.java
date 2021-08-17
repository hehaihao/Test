package com.xm6leefun.common.dialog;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xm6leefun.common.R;
import com.xm6leefun.common.base.BaseDialogFragment;
import com.xm6leefun.common.utils.SizeUtils;
import com.xm6leefun.common.widget.WaveDrawable;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class UpdateDialogFragment extends BaseDialogFragment implements View.OnClickListener {

	public static String TAG = UpdateDialogFragment.class.getSimpleName();
	
	private static final String EXTRA_VERSION = "extra_version";
	private static final String EXTRA_CONTENT = "extra_content";
	private static final String EXTRA_DOWNLOAD_URL = "extra_download_url";
	private static final String EXTRA_FORCE = "extra_force";
	private TextView tv_content;
	private LinearLayout layout_bottom;
	private TextView cancel_tv;
	private TextView sure_tv;
	private TextView tv_versionName;
	private RelativeLayout rl_wave;
	private ImageView iv_wave;
	private TextView tv_update_number;
	private boolean force = false;
	private boolean isDownloadFinish = false;
	private WaveDrawable mWaveDrawable;
	private String content = "";
	private String version = "";
	private String dowloadUrl = "";

	public static UpdateDialogFragment newInstance(String content, String versionName, String dowloadUrl, boolean needForceUpdate) {
		UpdateDialogFragment dialog = new UpdateDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putString(EXTRA_CONTENT, content);
		bundle.putString(EXTRA_VERSION, versionName);
		bundle.putString(EXTRA_DOWNLOAD_URL, dowloadUrl);
		bundle.putBoolean(EXTRA_FORCE, needForceUpdate);
		dialog.setArguments(bundle);
		return dialog;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		if (dialog != null) {

			dialog.getWindow().setBackgroundDrawableResource(R.drawable.white_10dp_conner);
			DisplayMetrics dm = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
			dialog.getWindow().setLayout((int) (dm.widthPixels * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT);
		}
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View inflate = inflater.inflate(R.layout.dialog_update_version, container, false);
		tv_content =  inflate.findViewById(R.id.tv_content);
		tv_versionName =  inflate.findViewById(R.id.tv_versionName);
		layout_bottom =  inflate.findViewById(R.id.layout_bottom);
		cancel_tv =  inflate.findViewById(R.id.cancel_tv);
		sure_tv =  inflate.findViewById(R.id.sure_tv);


		iv_wave =  inflate.findViewById(R.id.iv_wave);
		rl_wave = inflate.findViewById(R.id.rl_wave);
		tv_update_number = inflate.findViewById(R.id.tv_update_number);

		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_wave.getLayoutParams();
		int waveWith = (int) (SizeUtils.dip2px(getActivity() ,40) * SizeUtils.dip2px(getActivity() ,40) /(SizeUtils.dip2px(getActivity(),360) - SizeUtils.dip2px(getActivity() ,44)));
		layoutParams.width = waveWith;
		iv_wave.setLayoutParams(layoutParams);
		mWaveDrawable = new WaveDrawable(getActivity(), R.mipmap.bg_update_wave);
		iv_wave.setImageDrawable(mWaveDrawable);
		mWaveDrawable.setWaveLength(200);
		mWaveDrawable.setWaveAmplitude(19);
		mWaveDrawable.setWaveSpeed(1);
		ObjectAnimator animator = ObjectAnimator.ofFloat(iv_wave, "rotation", 0f, 90f);
		animator.start();
		ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv_wave, "scaleY", 1f, SizeUtils.dip2px(getActivity() ,40) /waveWith);
		animator1.start();
		ObjectAnimator animator2 = ObjectAnimator.ofFloat(iv_wave, "scaleX", 1f, SizeUtils.dip2px(getActivity() ,40) /waveWith);
		animator2.start();

		dowloadUrl = getArguments().getString(EXTRA_DOWNLOAD_URL,"");
		content = getArguments().getString(EXTRA_CONTENT,"");
		version = getArguments().getString(EXTRA_VERSION,"");
		force = getArguments().getBoolean(EXTRA_FORCE, false);
		setCancelable(!force);
		tv_content.setText(content);
		tv_versionName.setText(getString(R.string.updata_version_name,version));

		cancel_tv.setOnClickListener(this);
		sure_tv.setOnClickListener(this);

		cancel_tv.setVisibility(force ? View.GONE : View.VISIBLE);
		return inflate;
	}
	
	private void hideBottom() {
		layout_bottom.setVisibility(View.GONE);
		rl_wave.setVisibility(View.VISIBLE);
	}


	private String getApkSavePath() {
		return Environment.getExternalStorageDirectory().getPath()+File.separator+Environment.DIRECTORY_DOWNLOADS;
	}
	
	private String getApkName() {
		int appLabelRes = getContext().getApplicationInfo().labelRes;
		return getContext().getString(appLabelRes) + "_new.apk";
	}
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.cancel_tv) {
			dismiss();
		} else if (id == R.id.sure_tv) {
			new RxPermissions(getActivity())
					.request(Manifest.permission.READ_EXTERNAL_STORAGE,
							Manifest.permission.WRITE_EXTERNAL_STORAGE)
					.subscribe(isSuccess -> {
						if (isSuccess) {
							if (isDownloadFinish) {
								openApk();
							} else {
								if (!TextUtils.isEmpty(dowloadUrl)) {
									if (dowloadUrl.contains("apk") || dowloadUrl.contains("APK")) {
										hideBottom();
										if (!force) {
											cancel_tv.setEnabled(false);
											setCancelable(false);
										}
										new DownloadTask().execute(dowloadUrl);
									} else {
										Intent intent = new Intent();
										intent.setAction("android.intent.action.VIEW");
										Uri content_url = Uri.parse(dowloadUrl);
										intent.setData(content_url);
										startActivity(intent);
										if (!force) {
											dismiss();
										}
									}
								}
							}
						}
					});
		}
	}
	
	private class DownloadTask extends AsyncTask<String, Integer, String> {
		
		@Override
		protected String doInBackground(String... params) {
			int count;
			try {
				URL url = new URL(params[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();
				int lenghtOfFile = conexion.getContentLength();

				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output;

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
					ContentValues values = new ContentValues();
					values.put(MediaStore.MediaColumns.DISPLAY_NAME, getApkName());
					values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
					ContentResolver contentResolver = getActivity().getContentResolver();
					Uri uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
					output = new BufferedOutputStream(contentResolver.openOutputStream(uri));
				} else {
					output = new FileOutputStream(getApkSavePath() + File.separator + getApkName());
				}
				
				byte data[] = new byte[1024];
				long total = 0;
				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress((int) ((total * 10000) / lenghtOfFile));
					output.write(data, 0, count);
				}
				
				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
				jumpToBrowser(getActivity(),dowloadUrl);
			}
			return "finish";
		}
		
		protected void onProgressUpdate(Integer... progress) {
			int p = progress[0];
			mWaveDrawable.setLevel(p);
			String s = String.format(getString(R.string.update_downloading_str),p/100) + "%";
			tv_update_number.setText(s);
		}
		
		protected void onPostExecute(String result) {
			openApk();
			sure_tv.setVisibility(View.VISIBLE);
			sure_tv.setText(R.string.update_install_str);
			sure_tv.setEnabled(true);
			layout_bottom.setVisibility(View.VISIBLE);
			rl_wave.setVisibility(View.GONE);
			isDownloadFinish = true;
			if (!force) {
				cancel_tv.setEnabled(true);
				setCancelable(true);
			}
		}
	}
	
	private void openApk() {
		try {
			//下载完成，安装apk
			Intent promptInstall = new Intent(Intent.ACTION_VIEW);
			promptInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			Uri contentUri = null;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				String packageName = getActivity().getApplicationContext().getPackageName();
				contentUri = FileProvider.getUriForFile(getActivity(), packageName + ".provider", new File((Uri.parse("file://" + getApkSavePath() + File.separator + getApkName()).getPath())));
				promptInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			} else {
				contentUri = Uri.parse("file://" + getApkSavePath() + File.separator + getApkName());
			}
			promptInstall.setDataAndType(contentUri, "application/vnd.android.package-archive");
			getActivity().startActivity(promptInstall);
		}catch (Exception e){
			e.printStackTrace();
			jumpToBrowser(getActivity(),dowloadUrl);
		}
	}

	/**
	 * 无法下载则跳转到浏览器进行下载
	 * @param dowloadUrl
	 */
	private void jumpToBrowser(Context context,String dowloadUrl){
		Intent intent = new Intent();
		intent.setData(Uri.parse(dowloadUrl));//dowloadUrl 就是你要打开的网址
		intent.setAction(Intent.ACTION_VIEW);
		context.startActivity(intent); //启动浏览器
	}

}
