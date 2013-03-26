package com.activity;

import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

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
				R.id.ps14, R.id.ps15, R.id.record, R.id.stop, R.id.playrecord, R.id.stopplayrecord}, null);
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
private void stopPlay(){
	mPlayer.stop();
	mPlayer.release();
	mPlayer = null;
}
	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(filename);
			mPlayer.setLooping(true);
		
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e("my_log", "prepare() failed");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ps0:
			mSound.playSound(0);
			break;
		case R.id.ps1:
			mSound.playSound(1);
			break;
		case R.id.ps2:
			mSound.playSound(2);
			break;
		case R.id.ps3:
			mSound.playSound(3);
			break;
		case R.id.ps4:
			mSound.playSound(4);
			break;
		case R.id.ps5:
			mSound.playSound(5);
			break;
		case R.id.ps6:
			mSound.playSound(6);
			break;
		case R.id.ps7:
			mSound.playSound(7);
			break;
		case R.id.ps8:
			mSound.playSound(8);
			break;
		case R.id.ps9:
			mSound.playSound(9);
			break;
		case R.id.ps10:
			mSound.playSound(10);
			break;
		case R.id.ps11:
			mSound.playSound(11);
			break;
		case R.id.ps12:
			mSound.playSound(12);
			break;
		case R.id.ps13:
			mSound.playSound(13);
			break;
		case R.id.ps14:
			mSound.playSound(14);
			break;
		case R.id.ps15:
			mSound.playSound(15);
			break;
		case R.id.record:
			startRecord();
			break;
		case R.id.stop:
			stopRecord();
			break;
		case R.id.playrecord:
			startPlaying();
			break;
		case R.id.stopplayrecord:
			stopPlay();
			break;
		}

	}

}
