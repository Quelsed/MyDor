package com.example.dormitory;

public class NotesFB {
    public String id, fio, comm, date, place;
    public int roomnumber;

    public NotesFB() {

    }

    public NotesFB(String id, String fio, int roomnumber, String comm, String place, String date) {
        this.id = id;
        this.fio = fio;
        this.roomnumber = roomnumber;
        this.comm = comm;
        this.place = place;
        this.date = date;
    }


}
