package example.huyen.chatonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread bamgio=new Thread(){
            public void run(){
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent login = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(login);
                }
            }
        };
        bamgio.start();
                }
    //sau khi chuyển sang màn hình login, kết thúc màn hình chào
    protected void onPause(){
        super.onPause();
        finish();
    }

}
