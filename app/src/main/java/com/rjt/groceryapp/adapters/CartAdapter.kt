package com.rjt.groceryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.rjt.groceryapp.R
import com.rjt.groceryapp.app.Config
import com.rjt.groceryapp.database.DBHelper
import com.rjt.groceryapp.interfaces.ClickListener
import com.rjt.groceryapp.models.Cart
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cart.view.*
import kotlinx.android.synthetic.main.row_cart_adapter.view.*

class CartAdapter (var mContext: Context, var mList: ArrayList<Cart>, var clickListener: ClickListener) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    lateinit var dbHelper: DBHelper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_cart_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var cart = mList.get(position)
        holder.bindView(cart, position)
    }

    fun setData (mlist : ArrayList<Cart>, plist : ArrayList<Double>){
        mList = mlist
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(cart: Cart, position: Int){

            dbHelper = DBHelper(mContext)

            itemView.text_view_cart_name.text = cart.productName
            itemView.text_view_price.text = "$ " + cart.price.toString()
            itemView.text_view_qty.text = cart.qty.toString()

//            itemView.text_view_number_of_price.text = dbHelper.getTotalPrice().get(position).toString()

            Picasso.with(mContext)
                .load(Config.IMAGE_URL + cart.Image)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(itemView.image_view)

            itemView.button_delete.setOnClickListener {
                Toast.makeText(mContext, "delete", Toast.LENGTH_LONG).show()
//                itemView.text_view_number_of_price.text = (totalPrice - dbHelper.getTotalPrice().get(position)).toString()
                dbHelper.deleteFromCart(cart)
                mList.removeAt(position)
//                pList.removeAt(position)
                notifyItemRemoved(position)
                notifyDataSetChanged()
                clickListener.onQuantityChange()

//                //this line below gives you the animation and also updates the
//                //list items after the deleted item
//                notifyItemRangeChanged(position, getItemCount());

            }

            itemView.button_add.setOnClickListener {
                cart.qty = cart.qty + 1
                itemView.text_view_qty.text = cart.qty.toString()
                mList.set(position, cart)
                notifyDataSetChanged()
                dbHelper.updateProductQty(cart)

                clickListener.onQuantityChange()

            }

            itemView.button_minus.setOnClickListener {
                cart.qty = cart.qty - 1
                if (cart.qty > 0) {
                    itemView.text_view_qty.text = cart.qty.toString()
                    mList.set(position, cart)
                    notifyDataSetChanged()
                    dbHelper.updateProductQty(cart)

                } else {
                    dbHelper.deleteFromCart(cart)
                    mList.removeAt(position)
                    notifyItemRemoved(position)
                }
                clickListener.onQuantityChange()
            }


        }

    }
}