package com.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.dao.RecordSound;
import com.example.learn.R;
import com.logik.SoundMachine;

public class SoundActivity extends BaseActivity {

	private SoundMachine mSound;
	private MediaPlayer mPlayer;
	private String filename = Environment.getExternalStorageDirectory()
			+ "/Others/file.aac";
	private MediaRecorder mAudioRecorder;
	private AudioManager audioManager;
	private int oldAudioMode;
	private int[] BUTTONS = { R.id.ps0, R.id.ps1, R.id.ps2, R.id.ps3, R.id.ps4,
			R.id.ps5, R.id.ps6, R.id.ps7, R.id.ps8, R.id.ps9, R.id.ps10,
			R.id.ps11, R.id.ps12, R.id.ps13, R.id.ps14, R.id.ps15 };
	private boolean mRecordEnable;
	private List<RecordSound> mReclist = new ArrayList<RecordSound>();
	protected boolean mPlayRecord;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);
		mSound = new SoundMachine(this);
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		oldAudioMode = audioManager.getMode();
		mSound.addSound(0, R.raw.ccymbal);
		mSound.addSound(1, R.raw.cht);
		mSound.addSound(2, R.raw.cic);
		mSound.addSound(3, R.raw.clav);
		mSound.addSound(4, R.raw.cnga);
		mSound.addSound(5, R.raw.cowb);
		mSound.addSound(6, R.raw.cymb);
		mSound.addSound(7, R.raw.hat);
		mSound.addSound(8, R.raw.kick);
		mSound.addSound(9, R.raw.nar);
		mSound.addSound(10, R.raw.rim);
		mSound.addSound(11, R.raw.tik);
		mSound.addSound(12, R.raw.tom);
		mSound.addSound(13, R.raw.tomm);
		mSound.addSound(14, R.raw.tuum);
		mSound.addSound(15, R.raw.wood);
		registerOnClickListener(new int[] { R.id.ps0, R.id.ps1, R.id.ps2,
				R.id.ps3, R.id.ps4, R.id.ps5, R.id.ps6, R.id.ps7, R.id.ps8,
				R.id.ps9, R.id.ps10, R.id.ps11, R.id.ps12, R.id.ps13,
				R.id.ps14, R.id.ps15, R.id.record, R.id.stop, R.id.playrecord,
				R.id.stopplayrecord }, null);
		Player.start();
	}

	private void startRecord() {

		audioManager.setMicrophoneMute(true);
		audioManager.setMode(AudioManager.MODE_IN_CALL);

		mAudioRecorder = new MediaRecorder();
		mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		mAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
		mAudioRecorder.setOutputFile(filename);
		try {
			mAudioRecorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mAudioRecorder.start();
		audioManager.setMicrophoneMute(true);
	}

	private void stopRecord() {
		audioManager.setMode(oldAudioMode);
		audioManager.setMicrophoneMute(false);
		mAudioRecorder.stop();
		mAudioRecorder.release();
	}

	private void newRecord(boolean record) {
		long time = System.currentTimeMillis();
	}

	private void stopPlay() {
		mPlayer.stop();
		mPlayer.release();
		mPlayer = null;
	}

	private void startPlaying() {
		for (int i = 0; i < mReclist.size(); i++) {
			mSound.playSound(mReclist.get(i).sound);
		}
		// mPlayer = new MediaPlayer();
		// try {
		// mPlayer.setDataSource(filename);
		// mPlayer.setLooping(true);
		//
		// mPlayer.prepare();
		// mPlayer.start();
		// } catch (IOException e) {
		// Log.e("my_log", "prepare() failed");
		// }
	}

	Thread Player = new Thread(new Runnable() {

		@Override
		public void run() {
			while (mPlayRecord) {
				for (int i = 0; i < mReclist.size(); i++) {
					mSound.playSound(mReclist.get(i).sound);
					Log.d("my_log", "sleep");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Log.d("my_log", e.getMessage());
						e.printStackTrace();
					}
				}
			}

		}
	});

	@Override
	protected void onResume() {
		try {
			Player.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onResume();

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.record) {
			mRecordEnable = true;
			mReclist.clear();
			// startRecord();
		} else if (v.getId() == R.id.stop) {
			mRecordEnable = false;
			// stopRecord();
		} else if (v.getId() == R.id.playrecord) {
			startPlaying();
		} else if (v.getId() == R.id.stopplayrecord) {
			mPlayRecord = false;
			// stopPlay();
		} else {
			for (int i = 0; i < BUTTONS.length; i++) {
				if (v.getId() == BUTTONS[i]) {
					mSound.playSound(i);
					if (mRecordEnable) {
						mReclist.add(new RecordSound(i, System
								.currentTimeMillis()));
					}
					break;
				}
			}
		}
	}

}
