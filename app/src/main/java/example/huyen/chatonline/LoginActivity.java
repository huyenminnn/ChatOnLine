package example.huyen.chatonline;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText taiKhoan, matKhau, edtEmail;
    Button dangNhap, dangKi;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhXa();

        mAuth = FirebaseAuth.getInstance();
        dangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dangki = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(dangki);
            }
        });

        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user = taiKhoan.getText().toString();
                String email = edtEmail.getText().toString();
                final String password = matKhau.getText().toString();
                if (email.equals(""))
                    Toast.makeText(LoginActivity.this, "Enter your email.", Toast.LENGTH_SHORT).show();
                else if (user.equals("")){
                    Toast.makeText(LoginActivity.this, "Enter your username.", Toast.LENGTH_SHORT).show();
                }
                else if (password.equals(""))
                    Toast.makeText(LoginActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
                else{
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Email or password isn't correct.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                StatusChat.user = user;
                                StatusChat.pass = password;
                                startActivity(new Intent(LoginActivity.this, ListChatActivity.class));
                                finish();
                            }
                        }
                    });

                }

            }
        });
    }
    public void anhXa(){
        taiKhoan = (EditText)findViewById(R.id.editText);
        edtEmail = findViewById(R.id.editTextEmail);
        matKhau = (EditText)findViewById(R.id.editText2);
        dangKi = findViewById(R.id.button6);
        dangNhap = findViewById(R.id.button8);
    }
}
