package example.huyen.chatonline;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ListChatActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> arrListChat= new ArrayList<>();;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        lv = (ListView) findViewById(R.id.listView);

        DatabaseReference user_name = database.getReference();
        String url = "https://chatonline-6b21a.firebaseio.com/users.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getListUsers(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(ListChatActivity.this);
        queue.add(request);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StatusChat.friend = arrListChat.get(i);
                Intent chat = new Intent(ListChatActivity.this, ChatActivity.class);
                startActivity(chat);
            }
        });
    }

    public void getListUsers(String response){
        try {
            JSONObject object = new JSONObject(response);
            Iterator iterator = object.keys();
            String key = "";
            while (iterator.hasNext()){
                key = iterator.next().toString();
                if(!key.equals(StatusChat.user)) {
                    arrListChat.add(key);
                }
                //arrListChat.add(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        lv.setAdapter(new ArrayAdapter<String >(this,android.R.layout.simple_list_item_1,arrListChat));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                break;
            case R.id.search:
                break;
            case R.id.logout:
                mAuth.signOut();
                Intent back_login = new Intent(ListChatActivity.this, LoginActivity.class);
                startActivity(back_login);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
