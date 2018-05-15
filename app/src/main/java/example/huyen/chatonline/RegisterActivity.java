package example.huyen.chatonline;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText email, pass, username;
    Button register;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        anhXa();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email1 = email.getText().toString();
                final String password = pass.getText().toString();
                final String user = username.getText().toString();

                if (email1.equals(""))
                    Toast.makeText(RegisterActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
                else if (password.equals(""))
                    Toast.makeText(RegisterActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
                else if (user.equals(""))
                    Toast.makeText(RegisterActivity.this, "Enter your username.", Toast.LENGTH_SHORT).show();

                else {

                mAuth.createUserWithEmailAndPassword(email1,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            String url = "https://chatonline-6b21a.firebaseio.com/users.json";
                            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    User newUser = new User(email1,password,user);

                                    //Log.e("log: ",response);
                                    if (response.equals("null")){

                                        DatabaseReference user_name = database.getReference().child("users").child(user);
                                        user_name.setValue(newUser);
                                    }
                                    else {
                                        try {
                                            JSONObject object = new JSONObject(response);
                                            if (!object.has(user)){
                                                DatabaseReference user_name = database.getReference().child("users").child(user);
                                                user_name.setValue(newUser);
                                                Toast.makeText(RegisterActivity.this, "OK", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "username already exists", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                            queue.add(request);

                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });}
            }
        });
    }

    public void anhXa(){
        email = findViewById(R.id.editText4);
        username = findViewById(R.id.editText6);
        pass = findViewById(R.id.editText8);
        register = findViewById(R.id.button4);
    }
}
