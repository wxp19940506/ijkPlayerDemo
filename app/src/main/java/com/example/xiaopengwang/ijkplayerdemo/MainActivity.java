package com.example.xiaopengwang.ijkplayerdemo;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.widget.media.AndroidMediaController;
import tv.danmaku.ijk.media.widget.media.IjkVideoView;

public class MainActivity extends AppCompatActivity {
    IjkVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.fullscreen);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOrientation(getResources().getConfiguration().orientation);
            }
        });
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        videoView = (IjkVideoView) findViewById(R.id.ijkPlayer);
        AndroidMediaController controller = new AndroidMediaController(this, false);
        videoView.setMediaController(controller);
//        String url = "http://audio.live.cntv.dnion.com:8000/cache/14_/seg2/index.m3u8?AUTH=aGmPvZ5pBmmtgaICWjPd39x9M0FK3s9tgljS3S1cl4fU6CQ2KaEG0yWh8m+KkvfODOyy1Af36NC+mPprkeWcTA==";
        String url = "http://ipad.akamai.com/Video_Content/npr/cherryblossoms_hdv_bug/all.m3u8";
//         String url = "http://o6wf52jln.bkt.clouddn.com/演员.mp3";
//         String url = "http://27.223.89.130:58098/hls-live/livepkgr/_definst_/liveevent/livestream.m3u8";
//         String url = "http://cctv3.live.cntv.dnion.com/live/cctv3hls_/index.m3u8?ptype=1&amode=1&AUTH=cntv000120170323JIbA4jtsrjMJmN3qvx9lp1DIRiEnTYJlgWom9Wuo/atY5c+GF9N7pc7AqFaG7iCxWFBxvnVsxa0dwezR6TrpzA==";
        videoView.setVideoURI(Uri.parse(url));

        videoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                Log.e("error","Error0");
                Toast.makeText(MainActivity.this,"onError",Toast.LENGTH_LONG).show();
                return false;
            }
        });
        videoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                Toast.makeText(MainActivity.this,"OnPrepared",Toast.LENGTH_LONG).show();
                Log.e("error","OnPrepared");

                iMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                        Toast.makeText(MainActivity.this,"onError:"+i+"_"+i1,Toast.LENGTH_LONG).show();
                        Log.e("error","Error：");
                        return false;
                    }
                });
            }
        });
        videoView.setBufferingUpdateListener(new IMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
                Toast.makeText(MainActivity.this,"BufferingUpdate",Toast.LENGTH_LONG).show();
                Log.e("error","BufferingUpdate");
            }
        });
        videoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                Toast.makeText(MainActivity.this,"OnCompletion",Toast.LENGTH_LONG).show();
                Log.e("error","OnCompletion");

            }
        });
        videoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                Toast.makeText(MainActivity.this,"OnInfo",Toast.LENGTH_LONG).show();
                Log.e("error","OnInfo");

                return false;
            }
        });
        videoView.start();
    }
    private void setOrientation(int orientation) {
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }
}
