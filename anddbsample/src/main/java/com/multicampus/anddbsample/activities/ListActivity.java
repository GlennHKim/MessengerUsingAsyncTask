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
import com.multicampus.anddbsample.view.DialogItem;
import com.multicampus.anddbsample.view.DialogItemAdapter;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    public static final int MENU_MAKE_COMPLETE = 0;
    public static final int MENU_CANCEL_COMPLETE = 1;

    ArrayList<DialogItem> data;
    private ListView listView;
    private int contextTargetPosition;  // list 내 어떤 항목의 context menu가 선택 되었는지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setView();
        setEvent();
        init();
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogItem item = data.get(position);
                Intent intent = new Intent(ListActivity.this, DialogActivity.class);
                intent.putExtra("item", item);
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

        DialogItem dialogItem = data.get(contextTargetPosition);

        switch(item.getItemId()){
            case MENU_MAKE_COMPLETE:
                Log.d(this.getClass().getSimpleName(), "완료 메뉴 선택");
                completeItem(dialogItem, true);
                break;
            case MENU_CANCEL_COMPLETE:
                Log.d(this.getClass().getSimpleName(), "완료 취소 메뉴 선택");
                completeItem(dialogItem, false);
                break;
            case R.id.deleteMenu:
                Log.d(this.getClass().getSimpleName(), "삭제 메뉴 선택");
                removeItem(dialogItem);
                break;
        }

        // dataset이 바뀌었으니 list화면을 바꿔야 한다고 알려줌
        ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();

        return true;
    }

    private void completeItem(DialogItem item, boolean complete){
        item.setComplete(complete);

        // TODO: 2016-06-30 DB 상태값 변경
    }

    private void removeItem(DialogItem item){
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

        DialogItem item = data.get(contextTargetPosition);

        if(item.isComplete()){
            menu.add(0,MENU_CANCEL_COMPLETE,0,R.string.cancel_complete);
        }else{
            menu.add(0,MENU_MAKE_COMPLETE,0,R.string.complete);
        }

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_item_list, menu);
    }

    private void makeTodoList4(){
        data = getData4();

        DialogItemAdapter adapter = new DialogItemAdapter(
                this, data, R.layout.list_dialog_item
        );

        listView.setAdapter(adapter);
    }

    /**
     * TodoItem으로 구성된 ArrayList 반환
     * @return
     */
    private ArrayList<DialogItem> getData4(){
        ArrayList<DialogItem> list = new ArrayList<>();

        DialogItem item = new DialogItem(101, "111샘플 데이터", "이 항목은 샘플로 제공되는 항목입니다. 삭제하고 쓰세요.");
        list.add(item);

        item = new DialogItem(102, "이번달에 할일", "메일보내기 aaa@bbb.ccc 전화 010-1234-1234 쇼핑 http://www.gmarket.co.kr");
        list.add(item);

        return list;
    }
}
