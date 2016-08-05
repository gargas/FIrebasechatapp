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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
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
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.chat_list_view);
        chatEditText = (EditText) findViewById(R.id.chat_edit_text);
        findViewById(R.id.send_message_layout).setOnClickListener(this);
        if (savedInstanceState!=null) {
            chatMessageList = savedInstanceState.getParcelableArrayList(CHAT_LIST_KEY);
        }
        if (chatMessageList==null){
            chatMessageList = new ArrayList<>();
        }
        adapter = new ChatAdapter(chatMessageList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initialiseFireBase();
    }

    private void initialiseFireBase() {
        app = FirebaseApp.getInstance();
      //  database = FirebaseDatabase.getInstance(app);
        auth = FirebaseAuth.getInstance(app);
        storage = FirebaseStorage.getInstance(app);

        databaseRef = database.getReference("chat");

        databaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                if (adapter!=null){
                    chatMessage.setSelf(false);
                    if (!chatMessage.getSender().equalsIgnoreCase("nexus 5x"))
                    adapter.addMessage(chatMessage);
                    recyclerView.smoothScrollToPosition(chatMessageList.size()-1);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
            ChatMessage chatMessage = new ChatMessage(chatmessage,"Nexus 5x", true);
            adapter.addMessage(chatMessage);
            recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
            chatEditText.setText("");
            databaseRef.push().setValue(chatMessage);
        }
    }
}
