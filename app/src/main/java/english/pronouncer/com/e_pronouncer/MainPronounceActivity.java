package english.pronouncer.com.e_pronouncer;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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

    private TextViewPagerAdapter mTextViewPagerAdapter;

    private int mCurrentTextPosition = 0;
    private int mCurrentPosition;

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
