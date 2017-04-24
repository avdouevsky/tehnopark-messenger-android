package com.mshvdvskgmail.technoparkmessenger.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.GroupMembersAdapter;
import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.ChatUser;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 24.04.2017.
 */
public class MemberListView extends FrameLayout {
    private TextView tvMembersCount;
    private TextView tvMemberAdd;
    private RecyclerView recyclerView;
    private GroupMembersAdapter adapter;

    private ICommand<User> clickListener;

    public MemberListView(Context context) {
        this(context, null);
    }

    public MemberListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MemberListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        buildUI();
    }

    private void buildUI(){
        FrameLayout.inflate(getContext(), R.layout.view_member_list, this);

        tvMembersCount = (TextView) findViewById(R.id.tvMembersCount);
        tvMemberAdd = (TextView) findViewById(R.id.tvMemberAdd);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        adapter = new GroupMembersAdapter(getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
    }

    public void setData(Chat chat){
        List<User> users = new ArrayList<>();
        for(ChatUser cu : chat.users) if(cu.user != null) users.add(cu.user);
        setData(users);
    }

    public void setData(List<User> users){
        adapter.setData(users);
        tvMembersCount.setText(String.format("%d", adapter.getItemCount()));
        tvMemberAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo add member
            }
        });
    }

    public void setClickListener(ICommand<User> clickListener) {
        adapter.setClickListener(clickListener);
    }
}
