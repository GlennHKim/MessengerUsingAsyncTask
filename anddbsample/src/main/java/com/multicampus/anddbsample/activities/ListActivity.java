package com.multicampus.anddbsample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.multicampus.anddbsample.R;
import com.multicampus.anddbsample.restIF.ContactInterface;
import com.multicampus.anddbsample.view.ContactItem;
import com.multicampus.anddbsample.view.ContactItemAdapter;
import com.multicampus.anddbsample.vo.Contact;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    public static final int MENU_MAKE_COMPLETE = 0;
    public static final int MENU_CANCEL_COMPLETE = 1;

    Contact me;

    ContactInterface contactIf;

    ArrayList<ContactItem> data;
    private ListView listView;
    private int contextTargetPosition;  // list 내 어떤 항목의 context menu가 선택 되었는지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        contactIf = new ContactInterface(this);

        Intent intent = getIntent();
        me = (Contact)intent.getSerializableExtra("me");

        setView();
        setEvent();
        init();

        contactIf.getContactList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.addMenu:
                Log.d(this.getClass().getSimpleName(), "추가 메뉴 선택");
                break;
            case R.id.exitMenu:
                Log.d(this.getClass().getSimpleName(), "종료 메뉴 선택");
                break;
            case R.id.settingMenu:
                Log.d(this.getClass().getSimpleName(), "설정 메뉴 선택");
                break;
        }

        return true;
    }

    private void setView(){
        listView = (ListView)findViewById(R.id.listView);
    }

    private void setEvent(){
        registerForContextMenu(listView);

        final Contact myContact = new Contact(me.getId(), me.getName(), me.getTelNum(), me.getDesc(), me.getAddress());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactItem item = data.get(position);
                Intent intent = new Intent(ListActivity.this, DialogActivity.class);
                intent.putExtra("item", item);
                intent.putExtra("me", myContact);
                intent.putExtra("another", data.get(position).getContact());

                startActivity(intent);
            }
        });
    }

    private void init(){
        makeTodoList4();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        ContactItem dialogItem = data.get(contextTargetPosition);

        switch(item.getItemId()){
            case R.id.deleteMenu:
                Log.d(this.getClass().getSimpleName(), "삭제 메뉴 선택");
                removeItem(dialogItem);
                break;
        }

        // dataset이 바뀌었으니 list화면을 바꿔야 한다고 알려줌
        ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();

        return true;
    }

    private void removeItem(ContactItem item){
        data.remove(item);

        // TODO: 2016-06-30 DB 에서 삭제
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // context menu는 어디에도 붙을 수 있지만,
        // 우리가 관심 잇는 부분은 ItemListView 에 붙어 있는 context menu 이므로
        // 타입 캐스팅을 해줘야 함.
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        contextTargetPosition = info.position;

        ContactItem item = data.get(contextTargetPosition);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_item_list, menu);
    }

    private void makeTodoList4(){
        data = getData4();

        ContactItemAdapter adapter = new ContactItemAdapter(
                this, data, R.layout.list_dialog_item
        );

        listView.setAdapter(adapter);
    }

    /**
     * ContactItem으로 구성된 ArrayList 반환
     *
     * @return
     */
    private ArrayList<ContactItem> getData4() {
        ArrayList<ContactItem> list = new ArrayList<>();

        Contact c1 = new Contact("kimgon15", "김현곤", "010-231-1233", "나이소", "서울 신동아 아파트");
        Contact c2 = new Contact("pjs8602", "진슈우", "010-231-1233", "나이소", "서울 신동아 아파트");

        list.add(new ContactItem(c1));
        list.add(new ContactItem(c2));

        return list;
    }

    public void getSuccss(ArrayList<Contact> contacts){
        data.clear();

        for (Contact contact : contacts){
            data.add(new ContactItem(contact));
        }

        // dataset이 바뀌었으니 list화면을 바꿔야 한다고 알려줌
        ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
    }

    public void sendSuccess() {
    }

    public void sendFailure() {
    }
}
