package com.multicampus.anddbsample.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.multicampus.anddbsample.R;
import com.multicampus.anddbsample.restIF.ContactInterface;
import com.multicampus.anddbsample.vo.Contact;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnJoin;

    EditText editId;
    EditText editName;

    Contact me;

    ContactInterface contactIf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        contactIf = new ContactInterface(this);

        setView();
        setEvent();
    }

    private void setView(){
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnJoin = (Button)findViewById(R.id.btnJoin);
        editId = (EditText)findViewById(R.id.yourId);
        editName = (EditText)findViewById(R.id.yourName);
    }

    private void setEvent(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact(editId.getText().toString(), editName.getText().toString(), "", "", "");
                contactIf.Login(contact);
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loginSuccess(final Contact me){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        this.me = me;
        final Contact contact = new Contact(me.getId(), me.getName(), me.getTelNum(), me.getDesc(), me.getAddress());
        dialog.setTitle(R.string.msg_title_login_success)
                .setMessage(R.string.msg_content_login_success)
                .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                        intent.putExtra("me", contact);
                        startActivity(intent);
                    }
                });
        dialog.show();
    }

    public void loginFailure(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(R.string.msg_title_login_fail)
                .setMessage(R.string.msg_content_login_fail)
                .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog.show();

    }
}
