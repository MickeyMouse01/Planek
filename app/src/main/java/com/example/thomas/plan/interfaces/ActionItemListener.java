package com.example.thomas.plan.interfaces;

public interface ActionItemListener<T> {
    void onCheckedClick(T item);
    void onItemClick(T item);
    void onItemInfoClick(T item);
    void onItemDeleteClick(T item);

}



