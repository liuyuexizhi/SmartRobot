package com.lyxz.smartrobot;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Utils.HttpUtils;
import bean.ChatMessage;

public class MainActivity extends AppCompatActivity {

    private ListView mMsgs;
    private List<ChatMessage> mDatas;
    private ChatMessageAdapter mAdapter;

    private EditText mInputText;
    private Button mSend;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ChatMessage fromMessage = (ChatMessage) msg.obj;
            mDatas.add(fromMessage);
            mAdapter.notifyDataSetChanged();
            mMsgs.setSelection(mDatas.size() - 1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initListener();
    }

    private void initListener() {
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String toMsg = mInputText.getText().toString();
                if (TextUtils.isEmpty(toMsg)) {
                    Toast.makeText(MainActivity.this, "输入内容不能为空!", Toast.LENGTH_SHORT).show();
                } else {
                    ChatMessage toMesssage = new ChatMessage(toMsg,
                            ChatMessage.Type.OUTCOMING, new Date());

                    mDatas.add(toMesssage);
                    mAdapter.notifyDataSetChanged();
                    mMsgs.setSelection(mDatas.size() - 1);

                    mInputText.setText("");

                    new Thread() {
                        @Override
                        public void run() {
                            ChatMessage fromMessage = HttpUtils.sendMessage(toMsg);
                            Message m = Message.obtain();
                            m.obj = fromMessage;
                            mHandler.sendMessage(m);
                        }
                    }.start();
                }
            }
        });
    }

    private void initData() {
        mDatas = new ArrayList<ChatMessage>();
        mDatas.add(new ChatMessage("我是小沐，很高兴为你服务！！！"
                , ChatMessage.Type.INCOMING, new Date()));

        mAdapter = new ChatMessageAdapter(this, mDatas);
        mMsgs.setAdapter(mAdapter);
    }

    private void initView() {
        mMsgs = (ListView) findViewById(R.id.id_listview_msgs);
        mInputText = (EditText) findViewById(R.id.id_input_msg);
        mSend = (Button) findViewById(R.id.id_send_msg);
    }
}
