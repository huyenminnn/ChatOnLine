package example.huyen.chatonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    EditText input;
    TextView friend;
    Button send;
    ScrollView scrollView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        anhXa();
        friend.setText(StatusChat.friend);
        database = FirebaseDatabase.getInstance();
        final String st1 = StatusChat.user + "-" + StatusChat.friend;
        final String st2 = StatusChat.friend + "-" + StatusChat.user;
        final DatabaseReference data1 = database.getReference().child("message").child(st1);
        final DatabaseReference data2 = database.getReference().child("message").child(st2);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = input.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", StatusChat.user);
                    data1.push().setValue(map);
                    data2.push().setValue(map);
                    input.setText("");
                }
            }
        });

        data1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Map map = dataSnapshot.getValue(Map.class);
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator );
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(StatusChat.user)){
                    addMessageBox(message, 1);
                    Log.e("mess",message);
                }
                else{
                    addMessageBox(message, 2);
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

    public void anhXa(){
        input = findViewById(R.id.editText5);
        friend = findViewById(R.id.textView6);
        send = findViewById(R.id.button5);
        scrollView = findViewById(R.id.scrollView);
        layout = findViewById(R.id.layout1);
    }

    public void addMessageBox(String message,int i){
        TextView textView = new TextView(ChatActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(i == 1) {
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.send);
            textView.setGravity(Gravity.CENTER);

        }
        else{
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.rec);
            textView.setGravity(Gravity.CENTER);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}
