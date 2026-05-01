package com.example.moviebookingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CineFastDB";
    private static final int DATABASE_VERSION = 2;

    // Table and Columns
    private static final String TABLE_SNACKS = "snacks";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_PRICE = "price";
    private static final String COL_IMAGE = "image";
    private static final String COL_DESC = "description";

    private static final String TABLE_BOOKINGS = "bookings";
    private static final String COL_BOOKING_ID = "booking_id";
    private static final String COL_MOVIE_NAME = "movie_name";
    private static final String COL_SEAT_COUNT = "seat_count";
    private static final String COL_TOTAL_PRICE = "total_price";
    private static final String COL_DATE_TIME = "date_time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_SNACKS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_DESC + " TEXT, " +
                COL_PRICE + " REAL, " +
                COL_IMAGE + " INTEGER)";
        db.execSQL(createTable);

        String createBookings = "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                COL_BOOKING_ID + " TEXT PRIMARY KEY, " +
                COL_MOVIE_NAME + " TEXT, " +
                COL_SEAT_COUNT + " INTEGER, " +
                COL_TOTAL_PRICE + " REAL, " +
                COL_DATE_TIME + " TEXT)";
        db.execSQL(createBookings);

        // Insert initial data
        insertInitialSnacks(db);
    }

    private void insertInitialSnacks(SQLiteDatabase db) {
        addSnack(db, "Popcorn", "Large / Buttered", 8.99, R.drawable.popcorn_icon);
        addSnack(db, "Nachos", "With Cheese Dip", 7.99, R.drawable.nachos_icon);
        addSnack(db, "Soft Drink", "Large / Any Flavor", 5.99, R.drawable.soft_drink_icon);
        addSnack(db, "Candy Mix", "Assorted Candies", 6.99, R.drawable.candy_icon);
        addSnack(db, "Hot Dog", "Classic", 4.99, R.drawable.hot_dog_icon);
    }

    private void addSnack(SQLiteDatabase db, String name, String desc, double price, int image) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_DESC, desc);
        values.put(COL_PRICE, price);
        values.put(COL_IMAGE, image);
        db.insert(TABLE_SNACKS, null, values);
    }

    public ArrayList<Snack> getAllSnacks() {
        ArrayList<Snack> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SNACKS, null);

        if (cursor.moveToFirst()) {
            do {
                Snack snack = new Snack(
                        cursor.getString(1), // Name
                        cursor.getString(2), // Description
                        cursor.getDouble(3), // Price
                        cursor.getInt(4)     // Image ID
                );
                list.add(snack);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void addBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BOOKING_ID, booking.bookingId);
        values.put(COL_MOVIE_NAME, booking.movieName);
        values.put(COL_SEAT_COUNT, booking.seatCount);
        values.put(COL_TOTAL_PRICE, booking.totalPrice);
        values.put(COL_DATE_TIME, booking.dateTime);
        db.insertWithOnConflict(TABLE_BOOKINGS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<Booking> getAllBookings() {
        ArrayList<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COL_BOOKING_ID + ", " + COL_MOVIE_NAME + ", " + COL_SEAT_COUNT + ", " + COL_TOTAL_PRICE + ", " + COL_DATE_TIME +
                        " FROM " + TABLE_BOOKINGS +
                        " ORDER BY " + COL_DATE_TIME + " DESC",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Booking b = new Booking(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getDouble(3),
                        cursor.getString(4)
                );
                list.add(b);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean deleteBookingById(String bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_BOOKINGS, COL_BOOKING_ID + "=?", new String[]{bookingId});
        return rows > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SNACKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
    }
}