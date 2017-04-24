package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.ChatListAdapter;

import java.net.URI;
import java.util.ArrayList;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mshvdvskgmail.technoparkmessenger.ChatController;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.activities.MainActivity;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;
import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.RMQChat;
import com.mshvdvskgmail.technoparkmessenger.network.RabbitMQ;
import com.mshvdvskgmail.technoparkmessenger.network.model.Attachment;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.ChatUser;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;
import com.mshvdvskgmail.technoparkmessenger.network.model.Result;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.mshvdvskgmail.technoparkmessenger.view.MessageEditView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by mshvdvsk on 30/03/2017.
 */

public class FragmentChat extends BaseFragment {
    private final static String TAG = FragmentChat.class.toString();
    private static final int CHOOSE_FILE_REQUESTCODE = 10202;

    private ImageView fragment_chat_iv_profile;
    private LinearLayout layoutToolbarHeader;

    private RecyclerView recyclerView;
    private ChatListAdapter mAdapter;
    private MessageEditView messageEditView;

    private long startTime;
    private int timeout = 2;

    private Chat chat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        chat = ArgsBuilder.create().chat();

        View root = inflater.inflate(R.layout.fragment_chat, container, false);

        fragment_chat_iv_profile = (ImageView) root.findViewById(R.id.fragment_chat_iv_profile);
        layoutToolbarHeader = (LinearLayout) root.findViewById(R.id.layoutToolbarHeader);

        recyclerView = (RecyclerView) root.findViewById(R.id.fragment_chat_rv_messages);
        TextView tvContact = (TextView)root.findViewById(R.id.fragment_chat_tv_name);
        final TextView tvStatus = (TextView)root.findViewById(R.id.fragment_chat_tv_online_status);
        ImageView ivProfile = (ImageView)root.findViewById(R.id.fragment_chat_iv_profile);
        final FrameLayout call = (FrameLayout) root.findViewById(R.id.fragment_chat_fl_call);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        List<ChatUser> usersList = chat.users;

        if(chat.peer2peer == 0){
            Log.w("chat", "status: "+ chat.admin + " v. "+Controller.getInstance().getAuth().getUser().unique_id);
            //групповой чат
            tvContact.setText(chat.name);
            if(chat.admin.equals(Controller.getInstance().getAuth().getUser().unique_id)) {
                tvStatus.setText("Вы администратор");
            }else{
                tvStatus.setText("");
            }
            ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO ((MainActivity) getContext()).executeAction("showGroupSettings", chat);
                }
            });
            call.setVisibility(View.GONE);
        }else{
            //одиночный
            User user = usersList.get(0).user;
            if(user.id.equals(Controller.getInstance().getAuth().getUser().id)) {
                user = usersList.get(1).user;
            }
            tvContact.setText(user.cn);
            tvStatus.setText(user.online == 1 ? "ОНЛАЙН" : "ОФЛАЙН");

            final User finalUser = user;
            ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO ((MainActivity) getContext()).executeAction("showProfile", finalUser);
                }
            });
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        messageEditView = (MessageEditView) root.findViewById(R.id.viewMessageEdit);

        messageEditView.setCommand(new ICommand<MessageEditView.Action>() {
            @Override
            public void exec(MessageEditView.Action data) {
                switch (data){
                    case TYPE:
                        ChatController.getInstance().r.sendUserStatus(Controller.getInstance().getAuth().user.token, Controller.getInstance().getAuth().user.id, chat.uuid,
                                messageEditView.getText(), "text_input");
                        break;
                    case SEND:
                        sendMessage(null);
                        break;
                    case ATTACH:
                        openFile();
                        break;
                }
            }
        });

        FrameLayout flBackButton = (FrameLayout) root.findViewById(R.id.fragment_chat_fl_back);
        flBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mAdapter = new ChatListAdapter(getContext(), chat);
        recyclerView.setAdapter(mAdapter);

        if(chat.peer2peer == 0){
            View.OnClickListener groupSettings = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.GROUPS_SETTINGS, ArgsBuilder.create().chat(chat).bundle()));
                }
            };
            fragment_chat_iv_profile.setOnClickListener(groupSettings);
            layoutToolbarHeader.setOnClickListener(groupSettings);
        }


        loadData();

        return root;
    }

    private void loadData(){
        REST.getInstance().messages(Controller.getInstance().getAuth().getUser().token, chat.uuid, 0, 100)
                .subscribe(new REST.DataSubscriber<List<Message>>(){
                    @Override
                    public void onData(List<Message> data){
                        mAdapter.setData(data);
                    }
                });
    }

    private void sendMessage(@Nullable List<Attachment> attachments) {
        ChatController.getInstance().r.sendMessage(Controller.getInstance().getAuth().user.token,
                Controller.getInstance().getAuth().user.id, chat.uuid, messageEditView.getText(), "no local id", attachments).subscribe(new RMQChat.LogSubscriber<RabbitMQ>("sendMessage"));
        messageEditView.clear();
    }

    private void sendFile(File file, String mime){
        REST.getInstance().upload_attach(Controller.getInstance().getAuth().getUser().token, file, mime)
                .subscribe(new REST.DataSubscriber<Attachment>() {
                    @Override
                    public void onData(Attachment data) {
                        List<Attachment> attachments = new ArrayList<Attachment>();
                        attachments.add(data);
                        sendMessage(attachments);
                    }
                });
    }

    protected void eventMessage(Message message) {
        //TODO место багов, нужно проверять LocalID и по нему принимать решение об ошибке, а то придет из другой комнаты сообщение и всё...
        if(message.getType() == Message.Type.DIALOG) {
            mAdapter.addData(message);
            recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);

            ChatController.getInstance().r.sendMessageStatus(Controller.getInstance().getAuth().user.token,
                    Controller.getInstance().getAuth().user.id, chat.uuid, message.uuid, message.local_id, Message.Status.DELIVERED)
            .subscribe(new RMQChat.LogSubscriber<RabbitMQ>("sendMessageStatus"));
        }else if(message.getType() == Message.Type.ERROR){
            Log.e("temp", message.message);
            Toast.makeText(getContext(), "Ошибка отправки", Toast.LENGTH_LONG).show();
        }
    }

//    protected void eventDataLoad(String dataSource){
//        if(dataSource.equals("Groups")) {
//            Log.w("GroupsList", "eventDataLoad " + dataSource);
////            groups.clear();
////            groups.addAll(Controller.getInstance().getGroupChats());
//            mAdapter.notifyDataSetChanged();
//        }
//    }

//    public void openFile(String mimeType) {
    public void openFile() {
        //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); //TODO не получилось возбудить эту штуку
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.addCategory(Intent.CATEGORY_OPENABLE);
        //intent.setType("*/*");
        intent.setType("image/*");  //TODO заглушка на открытие только картинок
        startActivityForResult(intent, CHOOSE_FILE_REQUESTCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_FILE_REQUESTCODE && null != data)
        {
            Log.v(TAG, "onActivityResult "+ data.getData() + " | "+ getMimeType(data.getDataString()));
            File file = new File(URI.create(data.getData().getEncodedPath()).getPath());
            Log.v(TAG, "file: " + file.isFile());

            sendFile(file, getMimeType(data.getDataString()));
        }
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

//    public String getRealPathFromURI(Context context, Uri contentUri) {
//        Cursor cursor = null;
//        try {
//            String[] proj = { MediaStore.Images.Media.DATA };
//            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//
//            return cursor.getString(column_index);
//        } catch (Exception e){
//            Log.w(TAG, e);
//            return null;
//        }
//        finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }


//    private String getRealPathFromURI(Uri contentUri) {
//        String[] proj = { MediaStore.Images.Media.DATA };
//        CursorLoader loader = new CursorLoader(this.getContext(), contentUri, proj, null, null, null);
//        Cursor cursor = loader.loadInBackground();
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String result = cursor.getString(column_index);
//        cursor.close();
//        return result;
//    }
}
