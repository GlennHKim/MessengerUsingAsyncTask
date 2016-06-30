package com.multicampus.anddbsample.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.multicampus.anddbsample.R;
import com.multicampus.anddbsample.restIF.ContactInterface;
import com.multicampus.anddbsample.restIF.GetBear;
import com.multicampus.anddbsample.restIF.PostBear;
import com.multicampus.anddbsample.vo.Contact;

public class MainActivity extends AppCompatActivity {

    Button btnSubmit;
    Button btnList;

    EditText edittxtId;
    EditText edittxtName;
    EditText edittxtTelnum;
    EditText edittxtAddress;
    EditText edittxtDesc;

    GetBear getBear = new GetBear();
    PostBear postBear = new PostBear();

    ContactInterface contactIf = new ContactInterface(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();
        setDefaultEvent();
    }

    private void setView(){
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnList = (Button)findViewById(R.id.btnList);

        edittxtAddress = (EditText)findViewById(R.id.yourAddress);
        edittxtTelnum = (EditText)findViewById(R.id.yourTelnum);
        edittxtName = (EditText)findViewById(R.id.yourName);
        edittxtDesc = (EditText)findViewById(R.id.yourDesc);
        edittxtId = (EditText)findViewById(R.id.yourId);
    }

    private void setDefaultEvent(){
        btnSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //postBear.postBear(new Bear("AI-BaoBao"));
                Contact contact = new Contact(
                        edittxtId.getText().toString(),
                        edittxtName.getText().toString(),
                        edittxtTelnum.getText().toString(),
                        edittxtDesc.getText().toString(),
                        edittxtAddress.getText().toString()
                );
                contactIf.postContact(contact);
            }
        });

        btnList.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //getBear.getBear("57721c41aa42685017000001");
                contactIf.getContact();
            }
        });
    }
}
