package english.pronouncer.com.e_pronouncer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import english.pronouncer.com.e_pronouncer.R;

public class TextViewPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;

    @BindView(R.id.tv_text_display)
    TextView mTextDisplay;

    public String[] dummyText = {
            "Who are you?",
            "What is you name?",
            "Where are you from?",
            "How are you?"
    };

    public TextViewPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return dummyText.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.pronouncer_text_layout, container, false);
        ButterKnife.bind(this, view);
        mTextDisplay.setText(dummyText[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
