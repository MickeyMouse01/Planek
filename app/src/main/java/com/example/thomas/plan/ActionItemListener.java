package com.example.thomas.plan;

public interface ActionItemListener<T> {
    void onItemClick(T item);
    void onItemInfoClick(T item);
    void onItemDeleteClick(T item);

}



