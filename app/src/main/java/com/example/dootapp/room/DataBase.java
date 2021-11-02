package com.example.dootapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dootapp.ui.cread.TaskModel;

@Database(entities = {TaskModel.class},version = 1)
public abstract class DataBase  extends RoomDatabase {
    public abstract TaskDao taskDao();
}
