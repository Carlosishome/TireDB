package edu.tcu.ciprudhomme.tiredb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "TireDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Tire";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "id";
    private static final String TIRE_SIZE_FIELD = "tireSize";
    private static final String DOT_FIELD = "dot";
    private static final String TREAD_LIFE_FIELD = "treadLife";
    private static final String BRAND_FIELD = "brand";
    private static final String QUANTITY_FIELD = "quantity";
    private static final String HAS_PATCHES_FIELD = "hasPatches";
    private static final String LOCATION_FIELD = "location";
    private static final String DELETED_FIELD = "deleted";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context) {
        if (sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD).append(" INT, ")
                .append(TIRE_SIZE_FIELD).append(" TEXT, ")
                .append(DOT_FIELD).append(" TEXT, ")
                .append(TREAD_LIFE_FIELD).append(" INT, ")
                .append(BRAND_FIELD).append(" TEXT, ")
                .append(QUANTITY_FIELD).append(" INT, ")
                .append(HAS_PATCHES_FIELD).append(" INT, ")  // Boolean should be stored as an integer (1 or 0)
                .append(LOCATION_FIELD).append(" TEXT, ")
                .append(DELETED_FIELD).append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Handle database upgrade if needed
    }

    // Add new tire to database
    public void addTireToDatabase(Tire tire) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_FIELD, tire.getId());
        contentValues.put(TIRE_SIZE_FIELD, tire.getTireSize());
        contentValues.put(DOT_FIELD, tire.getDot());
        contentValues.put(TREAD_LIFE_FIELD, tire.getTreadLife());
        contentValues.put(BRAND_FIELD, tire.getBrand());
        contentValues.put(QUANTITY_FIELD, tire.getQuantity());
        contentValues.put(HAS_PATCHES_FIELD, tire.hasPatches() ? 1 : 0);  // Store boolean as integer
        contentValues.put(LOCATION_FIELD, tire.getLocation());
        contentValues.put(DELETED_FIELD, getStringFromDate(tire.getDeleted()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    // Populate tire list array from the database
    public void populateTireListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String tireSize = result.getString(2);
                    String dot = result.getString(3);
                    int treadLife = result.getInt(4);
                    String brand = result.getString(5);
                    int quantity = result.getInt(6);
                    boolean hasPatches = result.getInt(7) == 1;  // Convert integer back to boolean
                    String location = result.getString(8);
                    String stringDeleted = result.getString(9);
                    Date deleted = getDateFromString(stringDeleted);
                    Tire tire = new Tire(id, tireSize, dot, treadLife, brand, quantity, hasPatches, location, deleted);
                    Tire.tireArrayList.add(tire);
                }
            }
        }
    }

    // Update tire in the database
    public void updateTireInDB(Tire tire) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_FIELD, tire.getId());
        contentValues.put(TIRE_SIZE_FIELD, tire.getTireSize());
        contentValues.put(DOT_FIELD, tire.getDot());
        contentValues.put(TREAD_LIFE_FIELD, tire.getTreadLife());
        contentValues.put(BRAND_FIELD, tire.getBrand());
        contentValues.put(QUANTITY_FIELD, tire.getQuantity());
        contentValues.put(HAS_PATCHES_FIELD, tire.hasPatches() ? 1 : 0);  // Store boolean as integer
        contentValues.put(LOCATION_FIELD, tire.getLocation());
        contentValues.put(DELETED_FIELD, getStringFromDate(tire.getDeleted()));

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf(tire.getId())});
    }

    // Convert Date object to string format
    private String getStringFromDate(Date date) {
        if (date == null) {
            return null;
        }
        return dateFormat.format(date);
    }

    // Convert string to Date object
    private Date getDateFromString(String string) {
        try {
            return dateFormat.parse(string);
        } catch (ParseException | NullPointerException e) {
            return null;
        }
    }
}
