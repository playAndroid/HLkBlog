package hlk.com.hlkblog.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import hlk.com.hlkblog.R;
import hlk.com.hlkblog.adapter.ChatListAdapter;
import hlk.com.hlkblog.bean.ChatMessage;
import hlk.com.hlkblog.util.IMUtils;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 智能机器人问答
 * Created by user on 2017/1/18.
 */

public class RobotFragment extends BaseFragment {
    @Bind(R.id.list_view)
    ListView mListView;
    @Bind(R.id.ll_msg)
    LinearLayout ll_msg;
    @Bind(R.id.et_content)
    EditText mContent;
    @Bind(R.id.btn_send)
    Button btn_send;
    private ArrayList<ChatMessage> mDatas = new ArrayList<>();
    private ChatListAdapter adapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ChatMessage from = (ChatMessage) msg.obj;
            mDatas.add(from);
            adapter.notifyDataSetChanged();
            mListView.setSelection(mDatas.size() - 1);

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_robot, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerListener();
        initData();
    }

    private void registerListener() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    private void initData() {
        mDatas.add(new ChatMessage(ChatMessage.Type.INPUT, "hello,我是灰豆,可以陪你聊天哦,嘿嘿"));
        adapter = new ChatListAdapter(getActivity(), mDatas);
        mListView.setAdapter(adapter);
    }

    /**
     * 发送消息
     */
    private void sendMessage() {
        final String msg = mContent.getText().toString().trim();
        if (TextUtils.isEmpty(msg)) {
            Toast.makeText(getContext(), "没有填写任何消息", Toast.LENGTH_SHORT).show();
            return;
        }
        ChatMessage to = new ChatMessage(ChatMessage.Type.OUTPUT, msg);
        mDatas.add(to);
        adapter.notifyDataSetChanged();
        mListView.setSelection(mDatas.size() - 1);
        mContent.setText("");
        InputMethodManager im = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        //是否活跃的,键盘管理器
        if (im.isActive()) {
            // 如果开启
            im.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }

        new Thread() {
            @Override
            public void run() {
                ChatMessage from = null;
                try {
                    from = IMUtils.sendMassage(msg);
                } catch (Exception e) {
                    from = new ChatMessage(ChatMessage.Type.INPUT, "服务器异常了,挂掉了");
                }
                Message message = Message.obtain();
                message.obj = from;
                mHandler.sendMessage(message);

            }
        }.start();

    }

}
