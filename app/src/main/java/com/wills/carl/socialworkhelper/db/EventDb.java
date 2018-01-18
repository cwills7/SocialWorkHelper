package com.wills.carl.socialworkhelper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.EventLog;
import android.util.Log;

import com.wills.carl.socialworkhelper.Supervision;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 1/15/2018.
 */

public class EventDb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Event.db";
    public static final String EVENT_TABLE_NAME = "events";
    public static final String EVENT_DATE = "date";
    public static final String SUPERVISION_HOURS = "supervisionhours";
    public static final String SUPERVISION_TYPE = "supervisiontype";
    public static final String SUPERVISION_NOTES = "supervisionnotes";
    public static final String CEU_HOURS = "ceuhours";
    public static final String CEU_TYPE = "ceutype";
    public static final String CEU_NOTES = "ceunotes";
    public static final String NEW_INDICATOR = "newindicator";

    public static final String MONTH_NOTE_TABLE_NAME = "monthnote";
    public static final String MONTH_NOTE_MONTH = "month";
    public static final String MONTH_NOTE_YEAR = "year";
    public static final String MONTH_NOTE_NOTE = "note";


    public EventDb (Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table if not exists " + EVENT_TABLE_NAME+ " (" +
                EVENT_DATE + " INTEGER primary key, " +
                SUPERVISION_HOURS + " INTEGER, " +
                SUPERVISION_TYPE + " text, " +
                SUPERVISION_NOTES + " text, " +
                CEU_HOURS + " INTEGER, " +
                CEU_TYPE + " text, " +
                CEU_NOTES + " text, " +
                NEW_INDICATOR + " text)");
        db.execSQL("create table if not exists " + MONTH_NOTE_TABLE_NAME+ " (" +
                MONTH_NOTE_MONTH + " INTEGER, " +
                MONTH_NOTE_YEAR + " INTEGER, " +
                MONTH_NOTE_NOTE + " text)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //TODO: work out upgrade logic if necessary
    }

    public void removeAll(){
        //Drops the tables and recreates them.
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MONTH_NOTE_TABLE_NAME);
        db.execSQL("create table if not exists " + EVENT_TABLE_NAME+ " (" +
                EVENT_DATE + " INTEGER primary key, " +
                SUPERVISION_HOURS + " INTEGER, " +
                SUPERVISION_TYPE + " text, " +
                SUPERVISION_NOTES + " text, " +
                CEU_HOURS + " INTEGER, " +
                CEU_TYPE + " text, " +
                CEU_NOTES + " text, " +
                NEW_INDICATOR + " text)");
        db.execSQL("create table if not exists " + MONTH_NOTE_TABLE_NAME+ " (" +
                MONTH_NOTE_MONTH + " INTEGER, " +
                MONTH_NOTE_YEAR + " INTEGER, " +
                MONTH_NOTE_NOTE + " text)");
    }


    public List<Supervision> getEventsFromDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + EVENT_TABLE_NAME, null);
        res.moveToFirst();
        List<Supervision> supList = new ArrayList<>();

        while(!res.isAfterLast()){
            Supervision sup = new Supervision(res.getLong(res.getColumnIndex(EVENT_DATE)));
            sup.setSupervisionHours(res.getInt(res.getColumnIndex(SUPERVISION_HOURS)));
            sup.setSupervisionType(res.getString(res.getColumnIndex(SUPERVISION_TYPE)));
            sup.setSupervisionNotes(res.getString(res.getColumnIndex(SUPERVISION_NOTES)));
            sup.setCeuHours(res.getInt(res.getColumnIndex(CEU_HOURS)));
            sup.setCeuType(res.getString(res.getColumnIndex(CEU_TYPE)));
            sup.setCeuNotes(res.getString(res.getColumnIndex(CEU_NOTES)));
            sup.setIsNew("N");
            supList.add(sup);
        }
        return supList;
    }

    public Supervision getSupervision(int date){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + EVENT_TABLE_NAME + " where " + EVENT_DATE + " = " + date, null);
        res.moveToFirst();
        Supervision sup = new Supervision(date);
        sup.setSupervisionType("Online");
        if(res.getCount() == 0){
            return null;
        }
        if( !res.isAfterLast()){
            sup.setSupervisionHours(res.getInt(res.getColumnIndex(SUPERVISION_HOURS)));
            sup.setSupervisionType(res.getString(res.getColumnIndex(SUPERVISION_TYPE)));
            sup.setSupervisionNotes(res.getString(res.getColumnIndex(SUPERVISION_NOTES)));
            sup.setCeuHours(res.getInt(res.getColumnIndex(CEU_HOURS)));
            sup.setCeuType(res.getString(res.getColumnIndex(CEU_TYPE)));
            sup.setCeuNotes(res.getString(res.getColumnIndex(CEU_NOTES)));
            sup.setIsNew(res.getString(res.getColumnIndex(NEW_INDICATOR)));
        }
        return sup;
    }

    public void insertSupervision(Supervision sup){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", sup.getDate());
        values.put("supervision", sup.getSupervisionHours());
        values.put("supervisiontype", sup.getSupervisionType());
        values.put("supervisionnotes", sup.getSupervisionNotes());
        values.put("ceu", sup.getCeuHours());
        values.put("ceunotes", sup.getCeuNotes());
        values.put("newindicator", sup.getIsNew());
        db.insert(EVENT_TABLE_NAME, null, values);
    }

    public int saveSupervision(Supervision sup){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", sup.getDate());
        values.put("supervision", sup.getSupervisionHours());
        values.put("supervisiontype", sup.getSupervisionType());
        values.put("supervisionnotes", sup.getSupervisionNotes());
        values.put("ceu", sup.getCeuHours());
        values.put("ceunotes", sup.getCeuNotes());
        String selection = EVENT_DATE + "=?";
        String [] selectionArgs = {((Long) sup.getDate()).toString()};
        int updated = db.update(EVENT_TABLE_NAME, values, selection, selectionArgs);
        return updated;
    }

    public String getMonthNote(int month, int year){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + MONTH_NOTE_TABLE_NAME + " where " + MONTH_NOTE_MONTH + " = " + month + " AND " + MONTH_NOTE_YEAR + " = " + year,
                null);
        Log.d("GETMONTHNOTE", "Month-year " + month + " - " + year);

        res.moveToFirst();
        String note = "";
        if(res.getCount() == 0){
            return null;
        }
        if(!res.isAfterLast()){
            note = res.getString(res.getColumnIndex(MONTH_NOTE_NOTE));
            Log.d("GETRESULT", "NOTE: " + note);
        }
        return note;
    }

    public void insertMonthNote(int month, int year, String note) {
        if(!note.isEmpty()){
            SQLiteDatabase db = this.getWritableDatabase();
            String prevNote = getMonthNote(month, year);
            if (prevNote == null || prevNote.isEmpty()) {
                Log.d("Debug", "PrevNote: " + prevNote + " This Note: " + note);
                Log.d("INSERTMONTHNOTE", "Month-year " + month + " - " + year + " note: " + note);
                ContentValues values = new ContentValues();
                values.put("month", month);
                values.put("year", year);
                values.put("note", note);
                long insertResult = db.insert(MONTH_NOTE_TABLE_NAME, null, values);
                Log.d("INSERTRESULT", Long.toString(insertResult));
            } else {
                Log.d("Debug", "PrevNote: " + prevNote + " This Note: " + note);
                updateMonthNote(month, year, note);
            }
        }
    }

    public int updateMonthNote(int month, int year, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("UPDATEMONTHNOTE", "Month-year " + month + " - " + year + " note: " + note);
        values.put("note",note);
        String selection = MONTH_NOTE_MONTH + "=? AND " + MONTH_NOTE_YEAR +"=?";
        String [] selectionArgs = {String.valueOf(month), String.valueOf(year)};
        int updated = db.update(MONTH_NOTE_TABLE_NAME, values, selection, selectionArgs);
        Log.d("UPDATED? = ", Integer.toString(updated));
        return updated;
    }
}
