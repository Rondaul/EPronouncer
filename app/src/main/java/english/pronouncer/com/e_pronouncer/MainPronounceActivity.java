package english.pronouncer.com.e_pronouncer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.taishi.library.Indicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import english.pronouncer.com.e_pronouncer.adapters.TextViewPagerAdapter;

public class MainPronounceActivity extends AppCompatActivity {

    //ButterKnife View Injection
    @BindView(R.id.text_viewpager)
    ViewPager mTextSlideViewPager;
    @BindView(R.id.main_btn_previous)
    ImageButton mPreviousButton;
    @BindView(R.id.main_btn_next)
    ImageButton mNextButton;
    @BindView(R.id.main_tv_current_text_number)
    TextView mCurrentPositionTextView;
    @BindView(R.id.player)
    FloatingActionButton mPlayerActionButton;
    @BindView(R.id.audio_indicator)
    Indicator mAudioIndicator;
    @BindView(R.id.audio_seek_bar)
    SeekBar mSeekBar;

    private TextViewPagerAdapter mTextViewPagerAdapter;

    private int mCurrentTextPosition = 0;
    private int mCurrentPosition;
    private boolean isPressed = false;

    private MediaPlayer mMediaPlayer;
    private Runnable mRunnable;
    private Handler mHandler;

    @Override
    protected void onStart() {
        super.onStart();
        mPlayerActionButton.setImageResource(R.drawable.play);
        mAudioIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pronounce);
        ButterKnife.bind(this);

        mHandler = new Handler();

        mTextViewPagerAdapter = new TextViewPagerAdapter(this);
        mTextSlideViewPager.setAdapter(mTextViewPagerAdapter);

        mTextSlideViewPager.addOnPageChangeListener(mOnPageChangeListener);

        //MediaPlayer
        mMediaPlayer = MediaPlayer.create(this, R.raw.song);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBar.setMax(mMediaPlayer.getDuration());

            }
        });


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mMediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(mCurrentTextPosition > 0) {
                        mCurrentPosition = mCurrentTextPosition - 1;
                        mCurrentPositionTextView.setText(String.format("%d", mCurrentPosition + 1));
                        mTextSlideViewPager.setCurrentItem(mCurrentPosition);
                    }
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentTextPosition < 4) {
                    mCurrentPosition = mCurrentTextPosition + 1;
                    mCurrentPositionTextView.setText(String.format("%d", mCurrentPosition));
                    mTextSlideViewPager.setCurrentItem(mCurrentPosition);
                }
            }
        });

        mPlayerActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPressed) {
                    isPressed = false;
                } else {
                    isPressed = true;
                }
                    if(isPressed) {
                    mAudioIndicator.setVisibility(View.VISIBLE);
                        mPlayerActionButton.setImageResource(R.drawable.pause);
                        mMediaPlayer.start();
                        playProgress();
                    } else {
                        mAudioIndicator.setVisibility(View.INVISIBLE);
                        mPlayerActionButton.setImageResource(R.drawable.play);
                        mMediaPlayer.pause();
                    }

            }
        });
    }

    private void playProgress() {
        mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());

        if(mMediaPlayer.isPlaying()) {
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    playProgress();
                }
            };
            mHandler.postDelayed(mRunnable, 0);
        }
    }

    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    mCurrentPosition = position + 1;
                    mCurrentPositionTextView.setText(String.format("%d", mCurrentPosition));
                    mCurrentTextPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

    @Override
    protected void onStop() {
        super.onStop();
        mMediaPlayer.pause();
        isPressed = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mHandler.removeCallbacks(mRunnable);
    }
}
