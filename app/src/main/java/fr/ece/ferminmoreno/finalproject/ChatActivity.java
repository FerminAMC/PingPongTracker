package fr.ece.ferminmoreno.finalproject;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private EditText messageText;
    private Button sendButton;
    private TextView receivedMsg;
    private ScrollView messageList;
    private ArrayList<String> roomArrayList;
    private ArrayAdapter<String> roomAdapter;
    private DatabaseReference mDatabase;
    private String mUserEmail;
    private String chatEmail;
    private String chatMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        messageText = (EditText) findViewById(R.id.editText);
        sendButton = (Button) findViewById(R.id.sendButton);
        receivedMsg = (TextView) findViewById(R.id.receivedMsg);
        messageList = (ScrollView) findViewById(R.id.scrollView);

        roomArrayList = new ArrayList<String>();
        roomAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                roomArrayList);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("messages");
        mUserEmail = getIntent().getStringExtra("EXTRA_USER_EMAIL");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference childRoot = mDatabase.push();
                Map<String, Object> map = new HashMap<>();
                map.put("name", mUserEmail);
                map.put("text", messageText.getText().toString());
                childRoot.updateChildren(map);

                /*Message message = new Message(mUserEmail, messageText.getText().toString());
                mDatabase.push().setValue(message);*/
                messageText.setText("");
            }
        });
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                updateMessage(dataSnapshot);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateMessage(DataSnapshot dataSnapshot){
        chatEmail = (String) dataSnapshot.child("name").getValue();
        chatMessage = (String) dataSnapshot.child("messageText").getValue();
        receivedMsg.append(chatEmail + ": " + chatMessage + "\n\n");
    }
}
