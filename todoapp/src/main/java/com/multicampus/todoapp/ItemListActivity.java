package com.multicampus.todoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ItemListActivity extends AppCompatActivity {

    public static final int MENU_MAKE_COMPLETE = 0;
    public static final int MENU_CANCEL_COMPLETE = 1;

    ArrayList<TodoItem> data;
    private ListView listView;
    private int contextTargetPosition;  // list 내 어떤 항목의 context menu가 선택 되었는지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

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
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                final EditText todoTitle = new EditText(this);
                dialog.setView(todoTitle);

                dialog.setTitle(R.string.add_item_title)
                        .setMessage(R.string.add_item_msg)
                        .setIcon(android.R.drawable.ic_input_add)
                        .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CharSequence title = todoTitle.getText();
                                Log.d(ItemListActivity.this.getClass().getSimpleName(), title.toString());
                                Intent intent = new Intent(ItemListActivity.this, ItemEditActivity.class);
                                intent.putExtra(Intent.EXTRA_TITLE, title.toString());
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                dialog.show();

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
                Log.d(ItemListActivity.class.getSimpleName(), position + ", " + id +" 클릭.");
                TodoItem item = data.get(position);
                Intent intent = new Intent(ItemListActivity.this, ItemViewActivity.class);
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

        TodoItem todoItem = data.get(contextTargetPosition);

        switch(item.getItemId()){
            case MENU_MAKE_COMPLETE:
                Log.d(this.getClass().getSimpleName(), "완료 메뉴 선택");
                completeItem(todoItem, true);
                break;
            case MENU_CANCEL_COMPLETE:
                Log.d(this.getClass().getSimpleName(), "완료 취소 메뉴 선택");
                completeItem(todoItem, false);
                break;
            case R.id.deleteMenu:
                Log.d(this.getClass().getSimpleName(), "삭제 메뉴 선택");
                removeItem(todoItem);
                break;
        }

        // dataset이 바뀌었으니 list화면을 바꿔야 한다고 알려줌
        ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();

        return true;
    }

    private void completeItem(TodoItem item, boolean complete){
        item.setComplete(complete);

        // TODO: 2016-06-30 DB 상태값 변경
        DBHandler handler = DBHandler.open(this);
        handler.updateComplete(item.getTodoId(), complete);
        handler.close();
    }

    private void removeItem(TodoItem item){
        data.remove(item);

        // TODO: 2016-06-30 DB 에서 삭제
        DBHandler handler = DBHandler.open(this);
        handler.delete(item.getTodoId());
        handler.close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // context menu는 어디에도 붙을 수 있지만,
        // 우리가 관심 잇는 부분은 ItemListView 에 붙어 있는 context menu 이므로
        // 타입 캐스팅을 해줘야 함.
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        contextTargetPosition = info.position;

        TodoItem item = data.get(contextTargetPosition);

        if(item.isComplete()){
            menu.add(0,MENU_CANCEL_COMPLETE,0,R.string.cancel_complete);
        }else{
            menu.add(0,MENU_MAKE_COMPLETE,0,R.string.complete);
        }

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_item_list, menu);
    }

    private void makeTodoList1(){
        ArrayList<String> data = getData1();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
    }

    private void makeTodoList2(){
//        ArrayList<String> data = getData2();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sample, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
    }

    private void makeTodoList3(){
        ArrayList<Map<String, String>> data = getData3();
        // map으로 구성된 adapter
        //SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, new String[]{"title", "content"}, new int[]{android.R.id.text1, android.R.id.text2});
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.list_todo_item, new String[]{"title", "content"}, new int[]{R.id.titleTxt, R.id.contentTxt});

        listView.setAdapter(adapter);
    }

    private void makeTodoList4(){
        //data = getData4();
        data = getDataFromDB();

        TodoItemAdapter adapter = new TodoItemAdapter(
                this, data, R.layout.list_todo_item
        );

        listView.setAdapter(adapter);
    }
    /**
     * ArrayList로 만든 리스트 데이터 반환
     * @return
     */
    private ArrayList<String> getData1(){
        ArrayList<String> list = new ArrayList<>();
        list.add("샘플1");
        list.add("샘플2");
        return list;
    }

    /**
     * xml로 부터 데이터를 읽어서 반환
     * @return
     */
    private ArrayList<String> getData2(){
        Resources res = getResources();
        return new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.sample)));
    }

    /**
     * Map으로 구성된 ArrayList 반환
     * @return
     */
    private ArrayList<Map<String, String>> getData3(){
        ArrayList<Map<String,String>> list = new ArrayList<>();
        Map<String, String> item = new HashMap<>();
        item.put("title", "샘플 데이터");
        item.put("content", "이 항목은 샘플로 제공되는 항목입니다. 삭제하고 쓰세요.");

        list.add(item);

        item = new HashMap<>();
        item.put("title", "이번달에 할일");
        item.put("content", "메일보내기 aaa@bbb.ccc 전화 010-1234-1234 쇼핑 http://www.gmarket.co.kr");

        list.add(item);

        return list;
    }

    private ArrayList<TodoItem> getDataFromDB(){
        ArrayList<TodoItem> list = new ArrayList<>();

        DBHandler handler = DBHandler.open(this);

        Cursor cursor = handler.selectAll();

        while(cursor.moveToNext()){
            int todoId = cursor.getInt(cursor.getColumnIndex("_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            boolean important = cursor.getInt(cursor.getColumnIndex("important")) == 1 ? true : false;
            boolean complete = cursor.getInt(cursor.getColumnIndex("complete")) == 1 ? true : false;

            TodoItem item = new TodoItem(todoId, title, content, important, complete);
            list.add(item);
        }

        handler.close();

        return list;
    }

    /**
     * TodoItem으로 구성된 ArrayList 반환
     * @return
     */
    private ArrayList<TodoItem> getData4(){
        ArrayList<TodoItem> list = new ArrayList<>();

        TodoItem item = new TodoItem(101, "111샘플 데이터", "이 항목은 샘플로 제공되는 항목입니다. 삭제하고 쓰세요.");
        list.add(item);

        item = new TodoItem(102, "이번달에 할일", "메일보내기 aaa@bbb.ccc 전화 010-1234-1234 쇼핑 http://www.gmarket.co.kr");
        list.add(item);

        return list;
    }
}
