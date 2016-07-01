package com.multicampus.todoapp;

import java.io.Serializable;

/**
 * Created by student on 2016-06-29.
 */
public class TodoItem implements Serializable{
    private int todoId;
    private String title;
    private String content;
    private boolean complete;
    private boolean important;

    public TodoItem(int todoId, String title, String content) {
        this(todoId, title, content, false, false);
    }

    public TodoItem(int todoId, String title, String content, boolean complete, boolean important) {
        this.todoId = todoId;
        this.title = title;
        this.content = content;
        this.complete = complete;
        this.important = important;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }
}
