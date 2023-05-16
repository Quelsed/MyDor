package com.example.dormitory.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dormitory.Models.Notes;

import java.util.List;

@Dao
public interface mainDAO {
    @Insert(onConflict = REPLACE)
    void insert(Notes notes);


    @Query("SELECT * FROM notes ORDER by id DESC")
    List<Notes> getAll();


    @Query ("UPDATE notes SET fio=:fio, roomnumber=:roomnumber, place=:place, notes=:notes, date=:date WHERE ID=:ID")
    void update(int ID, String fio, String roomnumber, String place, String notes, String date);
    @Delete
    void delete(Notes notes);

}
