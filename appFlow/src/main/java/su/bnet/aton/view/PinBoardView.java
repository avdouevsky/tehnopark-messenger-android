package su.bnet.aton.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import su.bnet.aton.R;
import su.bnet.aton.helpers.Fonts;

/**
 * Created by andrey on 24.11.2016.
 */

public class PinBoardView extends FrameLayout implements View.OnClickListener{
    private final static String TAG = PinBoardView.class.toString();

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView textView8;
    private TextView textView9;
    private TextView textView0;
    private ImageView imageViewBackspace;
    private TextView textViewMessage;

    private TextView textViewMsg1;
    private TextView textViewMsg2;
    private LinearLayout linearLayoutPin1;
    private LinearLayout linearLayoutPin2;

    private int type;

    /**
     * Последовательность, привязанная к кнопкам
     * Можно заменить на любую, хоть символы
     */
    private static byte[] secureSequence = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9
    };

    public PinBoardView(Context context) {
        this(context, null);
    }

    public PinBoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PinBoardView,
                0, 0);
        type = a.getInt(R.styleable.PinBoardView_pin_routine, 0);
        a.recycle();

        buildUI();
    }

    private void buildUI() {
        FrameLayout.inflate(getContext(), R.layout.view_pin_board, this);

        textView0 = (TextView) findViewById(R.id.textView0);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView7 = (TextView) findViewById(R.id.textView7);
        textView8 = (TextView) findViewById(R.id.textView8);
        textView9 = (TextView) findViewById(R.id.textView9);
        imageViewBackspace = (ImageView) findViewById(R.id.imageViewBackspace);
        textViewMessage = (TextView) findViewById(R.id.textViewMessage);

        textViewMsg1 = (TextView) findViewById(R.id.textViewMsg1);
        textViewMsg2 = (TextView) findViewById(R.id.textViewMsg2);
        linearLayoutPin1 = (LinearLayout) findViewById(R.id.linearLayoutPin1);
        linearLayoutPin2 = (LinearLayout) findViewById(R.id.linearLayoutPin2);

        textView0.setOnClickListener(this);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        textView5.setOnClickListener(this);
        textView6.setOnClickListener(this);
        textView7.setOnClickListener(this);
        textView8.setOnClickListener(this);
        textView9.setOnClickListener(this);

        if(type == 1){
            textViewMsg2.setVisibility(GONE);
            linearLayoutPin2.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(View v) {
        byte b = -1;
        switch (v.getId()){
            case R.id.textView0:
                b = secureSequence[0];
                break;
            case R.id.textView1:
                b = secureSequence[1];
                break;
            case R.id.textView2:
                b = secureSequence[2];
                break;
            case R.id.textView3:
                b = secureSequence[3];
                break;
            case R.id.textView4:
                b = secureSequence[4];
                break;
            case R.id.textView5:
                b = secureSequence[5];
                break;
            case R.id.textView6:
                b = secureSequence[6];
                break;
            case R.id.textView7:
                b = secureSequence[7];
                break;
            case R.id.textView8:
                b = secureSequence[8];
                break;
            case R.id.textView9:
                b = secureSequence[9];
                break;
        }
        Log.d(TAG, "pressed: " + b);
        Toast.makeText(getContext(), "pressed: " + b, Toast.LENGTH_SHORT).show();
    }
}
