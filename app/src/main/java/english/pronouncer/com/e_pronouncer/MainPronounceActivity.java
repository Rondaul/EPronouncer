package english.pronouncer.com.e_pronouncer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.taishi.library.Indicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import english.pronouncer.com.e_pronouncer.adapters.TextViewPagerAdapter;

public class MainPronounceActivity extends AppCompatActivity {

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

    private TextViewPagerAdapter mTextViewPagerAdapter;

    private int mCurrentTextPosition = 0;
    private int mCurrentPosition;
    private boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pronounce);
        ButterKnife.bind(this);

        mTextViewPagerAdapter = new TextViewPagerAdapter(this);
        mTextSlideViewPager.setAdapter(mTextViewPagerAdapter);

        mTextSlideViewPager.addOnPageChangeListener(mOnPageChangeListener);

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
                    } else {
                        mAudioIndicator.setVisibility(View.INVISIBLE);
                        mPlayerActionButton.setImageResource(R.drawable.play);
                    }

            }
        });
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
}
