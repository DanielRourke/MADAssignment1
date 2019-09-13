package com.example.madass1;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String DB_NAME = "MADSASSONE";
    private static final String DB_TABLE_PRODUCT = "Product";
    private static final String DB_TABLE_SHOP = "Shopping";
    private static final String DB_TABLE_PANTRY = "Pantry";

    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE " + DB_TABLE_PRODUCT +
            "(ProductID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Name TEXT NOT NULL, " +
            "Location TEXT, " +
            "Type TEXT, PictureFilePath TEXT);";

    private static final String CREATE_TABLE_SHOP ="CREATE TABLE " + DB_TABLE_SHOP +
            "(ShoppingID INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "ProductID INTEGER  NOT NULL," +
            "Quantity INTEGER NOT NULL," +
            "FOREIGN KEY(ProductId) " +
            "REFERENCES " +DB_TABLE_PRODUCT + "(ProductID));";

    private static final String CREATE_TABLE_PANTRY ="CREATE TABLE " + DB_TABLE_PANTRY +
            "(PantryID INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "ProductID INTEGER  NOT NULL, " +
            "Quantity INTEGER NOT NULL, " +
            "FOREIGN KEY(ProductId) " +
            "REFERENCES " +DB_TABLE_PRODUCT + "(ProductID));";

    private static final int DB_VERSION = 1;


    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DatabaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

//    public boolean addRow(Integer id, String fn,String ln, int yob, String g) {
//        synchronized(this.db) {
//
//            ContentValues newProduct = new ContentValues();
//            newProduct.put("StudentID", id);
//            newProduct.put("FirstName", fn);
//            newProduct.put("LastName", ln);
//            newProduct.put("YearOfBirth", yob);
//            newProduct.put("Gender", g);
//            System.out.println(id + ", " + fn + ", "+ ln + ", " + yob + ", " + g);
//            try {
//                db.insertOrThrow(DB_TABLE, null, newProduct);
//            } catch (Exception e) {
//                Log.e("Error in inserting rows", e.toString());
//                e.printStackTrace();
//                return false;
//            }
//            //db.close();
//            return true;
//        }
//    }

    public boolean addProduct( String name, String location, String type, String filePath) {
        synchronized(this.db) {

            ContentValues newProduct = new ContentValues();
            newProduct.put("Name", name);
            newProduct.put("Location", location);
            newProduct.put("Type", type);
            newProduct.put("PictureFilePath", filePath);

            System.out.println(name + ", " + location + ", " + type + ", " + filePath);
            try {
                db.insertOrThrow(DB_TABLE_PRODUCT, null, newProduct);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public boolean editProduct( Integer id,  String name, String location, String type, String filePath) {
        synchronized(this.db) {

            ContentValues updatedProduct = new ContentValues();
            updatedProduct.put("Name", name);
            updatedProduct.put("Location", location);
            updatedProduct.put("Type", type);
            updatedProduct.put("PictureFilePath", filePath);
            //where Lines copied from "https://abhiandroid.com/database/operation-sqlite.html"
            String whereClause = "ProductId=?";
            String whereArgs[] = {id.toString()};
            System.out.println(name + ", " + location + ", " + type + ", " + filePath);


            try {
                db.update(DB_TABLE_PRODUCT, updatedProduct ,whereClause ,whereArgs);
                //db.insertOrThrow(DB_TABLE_PRODUCT, null, newProduct);
            } catch (Exception e) {
                Log.e("Error Editing rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public boolean deleteProduct( Integer id) {
        synchronized(this.db) {

            String whereClause = "ProductId=?";
            String whereArgs[] = {id.toString()};

            try {
                db.delete(DB_TABLE_PRODUCT, whereClause ,whereArgs);
            } catch (Exception e) {
                Log.e("Error Editing rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public Product getProduct(Integer id) {

        synchronized (this.db) {
            String[] columns = new String[]{"ProductId AS _id", "Name", "Location", "Type", "PictureFilePath"};
            String whereClause = "_id=?";
            String whereArgs[] = {id.toString()};
            Cursor cursor;
            try {
                cursor = db.query(DB_TABLE_PRODUCT, columns, whereClause, whereArgs, null, null, null);
            } catch (Exception e) {
                Log.e("Error Getting row", e.toString());
                e.printStackTrace();
                return null;
            }

            cursor.moveToFirst();
            //db.close();
            return new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }
    }

    public boolean addShoppingItem( int productId , int quantity ) {
        synchronized(this.db) {

            ContentValues newShoppingItem = new ContentValues();
            newShoppingItem.put("ProductId", productId);
            newShoppingItem.put("Quantity", quantity);

            System.out.println(productId + ", " + quantity);
            try {
                db.insertOrThrow(DB_TABLE_SHOP, null, newShoppingItem);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public boolean addPantryItem( int productId , int quantity ) {
        synchronized(this.db) {

            ContentValues newPantryItem = new ContentValues();
            newPantryItem.put("ProductId", productId);
            newPantryItem.put("Quantity", quantity);

            System.out.println(productId + ", " + quantity);
            try {
                db.insertOrThrow(DB_TABLE_SHOP, null, newPantryItem);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public ArrayList<String> retrieveRows() {
        ArrayList<String> productRows = new ArrayList<String>();
        String[] columns = new String[] {"ProductId", "Name", "Location", "Type", "PictureFilePath"};
        Cursor cursor = db.query(DB_TABLE_PRODUCT, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            productRows.add(Integer.toString(cursor.getInt(0)) + ", " + cursor.getString(1) + ", "
                    + cursor.getString(2) + ", "+ Integer.toString(cursor.getInt(3)) + ", "
                    + cursor.getString(4));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return productRows;
    }

    public Cursor retrieveProducts() {
        String[] columns = new String[] {"ProductId AS _id", "Name", "Location", "Type", "PictureFilePath"};

        return db.query(DB_TABLE_PRODUCT, columns, null, null, null, null, null);
    }

    public Cursor retrieveProducts(String orderBy) {
        String[] columns = new String[] {"ProductId AS _id", "Name", "Location", "Type", "PictureFilePath"};

        return db.query(DB_TABLE_PRODUCT, columns, null, null, null, null, orderBy);
    }

    public void clearRecords()
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE_PRODUCT, null, null);
        db.delete(DB_TABLE_SHOP, null, null);
        db.delete(DB_TABLE_PANTRY, null, null);
    }

    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper (Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_PRODUCT);
            db.execSQL(CREATE_TABLE_SHOP);
            db.execSQL(CREATE_TABLE_PANTRY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Products table", "Upgrading database i.e. dropping table and re-creating it");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_PRODUCT);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_SHOP);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_PANTRY);
            onCreate(db);
        }
    }

    public class Product
    {
        public Integer id;
        public String name;
        public String location;
        public String type;
        public String picturePath;
        Product(int i, String n, String l, String t, String p)
        {
            id = i;
            name = n;
            location = l;
            type = t;
            picturePath = p;
        }

        public String getId()
        {
            return id.toString();
        }


    }
}
