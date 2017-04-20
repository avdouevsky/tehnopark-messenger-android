package com.mshvdvskgmail.technoparkmessenger.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mshvdvskgmail.technoparkmessenger.ChatController;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;

/**
 * Created by andrey on 20.04.2017.
 */
public class MessageEditView extends FrameLayout {
    private EditText editText;
    private FrameLayout frameSend;
    private ImageView imageAttach;

    private ICommand<Action> command;

    public MessageEditView(Context context) {
        this(context, null);
    }

    public MessageEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        buildUI();
    }

    private void buildUI(){
        FrameLayout.inflate(getContext(), R.layout.view_message_edit, this);

        editText = (EditText) findViewById(R.id.editText);
        frameSend = (FrameLayout) findViewById(R.id.frameSend);
        imageAttach = (ImageView) findViewById(R.id.imageAttach);

        frameSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(command != null) command.exec(Action.SEND);
            }
        });

        imageAttach.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(command != null) command.exec(Action.ATTACH);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            private long startTime = 0;
            private int timeout = 3;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(startTime + timeout * 1000 < System.currentTimeMillis()){
                    startTime = System.currentTimeMillis();
                    if(command!=null) command.exec(Action.TYPE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public String getText(){
        return editText.getText().toString();
    }

    public void clear(){
        editText.setText("");
    }

    public void setCommand(ICommand<Action> command) {
        this.command = command;
    }

    public enum Action{
        SEND, ATTACH, TYPE;
    }
}
