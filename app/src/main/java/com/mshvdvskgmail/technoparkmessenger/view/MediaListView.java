package com.mshvdvskgmail.technoparkmessenger.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.GroupFilesAdapter;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;
import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;
import com.mshvdvskgmail.technoparkmessenger.network.model.Attachment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by andrey on 24.04.2017.
 */

public class MediaListView extends FrameLayout {
    private TextView tvFilesCount;
    private RecyclerView recyclerView;
    private LinearLayout llMediaLink;

    private GroupFilesAdapter adapter;

    private int fileCounter;

    private ICommand<Void> countClickListener;

    public MediaListView(Context context) {
        this(context, null);
    }

    public MediaListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediaListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        buildUI();
    }

    private void buildUI(){
        FrameLayout.inflate(getContext(), R.layout.view_media_list, this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvFilesCount = (TextView) findViewById(R.id.tvFilesCount);
        llMediaLink = (LinearLayout) findViewById(R.id.ll_media_link);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(lm);
        adapter = new GroupFilesAdapter(getContext());
        recyclerView.setAdapter(adapter);

        llMediaLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countClickListener != null) countClickListener.exec(null);
            }
        });

//        llMediaLink.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.MEDIA, ArgsBuilder.create().chat(chat).bundle()));
//            }
//        });
    }

    public void setData(@NonNull List<Attachment> attachments){
        adapter.setData(attachments);
        fileCounter = attachments.size();
        tvFilesCount.setText(String.format("%d", attachments.size()));
    }

    public void setCountClickListener(ICommand<Void> countClickListener) {
        this.countClickListener = countClickListener;
    }

    public int getFileCounter(){
        return fileCounter;
    }
}
