package su.bnet.aton.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import su.bnet.aton.R;
import su.bnet.aton.helpers.Fonts;

/**
 * Created by andrey on 23.11.2016.
 * @deprecated использовался для теста конвертации шрифтов
 */
@Deprecated
public class FontTextView extends TextView {
    public FontTextView(Context context) {
        super(context);

        font(null);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        font(attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        font(attrs);
    }

    private void font(@Nullable AttributeSet attrs){
        if(isInEditMode()) return;

        if(attrs == null){
            setTypeface(Fonts.getInstance().get());
            return;
        }

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FontTextView,
                0, 0);
        int type = a.getInt(R.styleable.FontTextView_face_type, 2);
        a.recycle();
        setTypeface(Fonts.getInstance().get(type));
    }
}
