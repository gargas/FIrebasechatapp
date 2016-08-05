package com.himanshugargas.firebasechatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.himanshugargas.firebasechatapp.adapters.ChatAdapter;
import com.himanshugargas.firebasechatapp.models.ChatMessage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String CHAT_LIST_KEY = "chat list";
    private RecyclerView recyclerView;
    private EditText chatEditText;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatMessageList;
    private FirebaseApp app;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));
        recyclerView = (RecyclerView)findViewById(R.id.chat_list_view);
        chatEditText = (EditText) findViewById(R.id.chat_edit_text);
        findViewById(R.id.send_message_layout).setOnClickListener(this);
        if (savedInstanceState!=null) {
            chatMessageList = savedInstanceState.getParcelableArrayList(CHAT_LIST_KEY);
        }
        if (chatMessageList==null){
            chatMessageList = new ArrayList<>();
        }
        initialiseFireBase();
        adapter = new ChatAdapter(ChatMessage.class,R.layout.chat_item_other_layout,ChatAdapter.CustomViewHolder.class,databaseRef);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initialiseFireBase() {
        app = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(app);
        databaseRef = database.getReference("chat");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (chatMessageList!=null && chatMessageList.size()>0) {
            outState.putParcelableArrayList(CHAT_LIST_KEY, chatMessageList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_message_layout:
                sendMessage();
                break;
        }
    }

    private void sendMessage() {
        String chatmessage = chatEditText.getText().toString();
        if (!TextUtils.isEmpty(chatmessage)) {
            ChatMessage chatMessage = new ChatMessage(chatmessage,"Nexus 6p", true);
            recyclerView.smoothScrollToPosition(adapter.getItemCount()==0?0:adapter.getItemCount() - 1);
            chatEditText.setText("");
            databaseRef.push().setValue(chatMessage);
        }
    }
}
