package com.rjt.groceryapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rjt.groceryapp.app.Config
import com.rjt.groceryapp.models.Cart
import com.rjt.groceryapp.models.Product


class DBHelper(var context: Context) : SQLiteOpenHelper(context, Config.DATABASE_NAME, null, Config.DATABASE_VERSION){

    private var db: SQLiteDatabase = this.writableDatabase

    companion object{
        val TABLE_NAME = "cart"
        val COLUMN_ID = "_id"
        val COLUMN_NAME = "product_name"
        val COLUMN_IMAGE = "product_image"
        val COLUMN_PRICE = "price"
        var COLUMN_QTY = "qty"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "create table if not exists $TABLE_NAME ($COLUMN_ID char(50), $COLUMN_NAME char(50), $COLUMN_IMAGE char(200), $COLUMN_PRICE DOUBLE, $COLUMN_QTY INTEGER)"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun updateProductQty(cart: Cart) {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_QTY, cart.qty)
        var whereClause = "$COLUMN_ID == ?"
        var whereArgs = arrayOf(cart.cartId)
        db.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

    fun getProductFromCart(product: Product): Int {
        var quantity = 0
        val query = "SELECT * FROM " + TABLE_NAME + " where " + "$COLUMN_ID == ?"
        val cursor = db.rawQuery(
            query,
            arrayOf(product._id)
        )
        if (cursor.moveToFirst()) {
            quantity = cursor.getString(cursor.getColumnIndex(COLUMN_QTY)).toInt()
            cursor.close()
            return quantity
        }
        return quantity
    }

    fun addToCart(product: Product) {
        var database = this.writableDatabase
        val contentValues = ContentValues()

        if (!isProductInCart(product)) {
            contentValues.put(COLUMN_NAME, product.productName)
            contentValues.put(COLUMN_IMAGE, product.image)
            contentValues.put(COLUMN_QTY, product.qty)
            contentValues.put(COLUMN_ID, product._id)
            contentValues.put(COLUMN_PRICE, product.price)
            database.insert(TABLE_NAME, null, contentValues)
        } else {
//            // get product qty , add qty by 1, updateqty
//            product.qty = getProductQty(product)
            updateProductQty(product)
        }
    }

    fun updateProductQty(product: Product) {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_QTY, product.qty)
        var whereClause = "$COLUMN_ID == ?"
        var whereArgs = arrayOf(product._id)
        db.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

    fun getProductQty(product: Product) : Int {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_QTY, product.qty)
        var whereClause = "$COLUMN_ID == ?"
        var whereArgs = arrayOf(product._id)
        db.update(TABLE_NAME, contentValues, whereClause, whereArgs)
        return product.qty
    }


    fun isProductInCart(product: Product) : Boolean{
        val query = "SELECT * from $TABLE_NAME where $COLUMN_ID=?"
        val cursor = db.rawQuery(query, arrayOf(product._id))
        var count = cursor.count
        if (count == 0) {
            return false
        } else {
            return true
        }
    }



    fun getAllCartProduct(): ArrayList<Cart>{
        var cartList = ArrayList<Cart>()

        var query = "select * from $TABLE_NAME"
        var column = arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_IMAGE, COLUMN_PRICE, COLUMN_QTY)
        val cursor = db.query(TABLE_NAME, column, null, null, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            do{
                var cart: Cart = Cart("", "", "", 0.0,0)
                cart.cartId = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                cart.productName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                cart.Image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                cart.price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
                cart.qty = cursor.getInt(cursor.getColumnIndex(COLUMN_QTY))

                cartList.add(cart)
            }while(cursor.moveToNext())
        }
        cursor.close()
        return cartList
    }

    fun getTotalPrice(): Double {
        var totalPrice: Double = 0.0

        var query = "select * from $TABLE_NAME"
        var column = arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_IMAGE, COLUMN_PRICE, COLUMN_QTY)
        val cursor = db.query(TABLE_NAME, column, null, null, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            do{
                var cart: Cart = Cart("", "", "", 0.0,0)
                cart.cartId = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                cart.productName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                cart.Image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                cart.price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
                cart.qty = cursor.getInt(cursor.getColumnIndex(COLUMN_QTY))

                totalPrice += cart.price * cart.qty

            }while(cursor.moveToNext())
        }
        cursor.close()
        return totalPrice
    }


    fun deleteFromCart(cart: Cart) {
        var whereClause: String = "$COLUMN_ID=?"
        var whereArgs = arrayOf(cart.cartId)
        db.delete(TABLE_NAME, whereClause, whereArgs)
    }

}