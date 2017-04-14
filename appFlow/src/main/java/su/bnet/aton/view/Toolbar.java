package su.bnet.aton.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.TextView;

import su.bnet.aton.R;

/**
 * Created by andrey on 24.11.2016.
 */

public class Toolbar extends android.support.v7.widget.Toolbar {

    private TextView textViewTitle;

    public Toolbar(Context context) {
        super(context);
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void buildUI(Activity activity){
        textViewTitle = (TextView) activity.findViewById(R.id.textViewTitle);
    }

    @Override
    public void setTitle(@StringRes int resId) {
        textViewTitle.setText(resId);
    }

    @Override
    public void setTitle(CharSequence title) {
        textViewTitle.setText(title);
    }
}
