package com.multicampus.anddbsample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.multicampus.anddbsample.R;
import com.multicampus.anddbsample.restIF.DialogInterface;
import com.multicampus.anddbsample.vo.Contact;
import com.multicampus.anddbsample.vo.Dialog;

import java.util.ArrayList;
import java.util.Date;

public class DialogActivity extends AppCompatActivity {

    Contact me;
    Contact another;

    ArrayList<Dialog> dialogList;

    DialogInterface dialogIf;

    TextView textView;
    Button btnSend;
    EditText editView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        setTitle("대화");

        Intent intent = getIntent();
        me = (Contact)intent.getSerializableExtra("me");
        another = (Contact)intent.getSerializableExtra("another");

        dialogIf = new DialogInterface(this, me);

        setView();
        setEvent();

        dialogIf.getDialogs(another);
    }

    private void setView(){
        textView = (TextView)findViewById(R.id.contentTxt);
        btnSend = (Button)findViewById(R.id.btnSend);
        editView = (EditText)findViewById(R.id.editTxt);
    }

    private void setEvent(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(me.getId(), another.getId(), editView.getText().toString(), new Date().toString());
                editView.setText("");
                dialogIf.sendDialog(dialog);
                dialogIf.getDialogs(another);
            }
        });
    }

    public void getSuccess(ArrayList<Dialog> list){
        String txt = "";
        for (Dialog dialog : list){
            txt += dialog.getSender() + ": " + dialog.getText() + "\n";
        }
        textView.setText(txt);
    }

    public void sendSuccess() {
    }

    public void sendFailure() {
    }
}
