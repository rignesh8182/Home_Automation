package com.example.home_automation

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Currency

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, "item_history_db", null, 1) {

    val table_name:String="item_history"
    val db_name:String="item_history_db"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $table_name (item_id INTEGER primary key autoincrement,item_name Text,item_state Text,item_img Text,time Long)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("drop table if exists $db_name")
        onCreate(db)
    }

    public fun insert_data(name: String?, state: Boolean?, img: String?,time:Long?): Boolean {
        var db: SQLiteDatabase = this.writableDatabase
        var values: ContentValues = ContentValues()
        values.put("item_name",name)
        values.put("item_state",state)
        values.put("item_img",img)
        values.put("time",time)
        var res:Long=db.insert(table_name,null,values)

        return !res.equals(-1)
    }

    fun getAll():Cursor{
        val db:SQLiteDatabase=this.writableDatabase
        var res:Cursor=db.rawQuery("select * from $table_name",null)
        return res
    }

    fun del_item(item_id:Int): Int {
        val db=writableDatabase
        return db.delete(table_name,"item_id = ?", arrayOf(item_id.toString()))
    }
}