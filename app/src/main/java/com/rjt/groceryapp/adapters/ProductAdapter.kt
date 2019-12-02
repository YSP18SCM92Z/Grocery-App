package com.rjt.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rjt.groceryapp.R
import com.rjt.groceryapp.activities.ProductDetailActivity
import com.rjt.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_product_adapter.view.*

class ProductAdapter (var mContext: Context, var mList: ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_product_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = mList.get(position)
        holder.bindView(product)
    }

    fun setData (list : ArrayList<Product>){
        mList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(product: Product){

            itemView.text_view_product_name.text = product.productName
            itemView.text_view_price.text = "$ " + product.price.toString()

            Picasso.with(mContext)
                .load("http://rjtmobile.com/grocery/images/" + product.image)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(itemView.image_view)


            itemView.setOnClickListener {
                var intent = Intent(mContext, ProductDetailActivity::class.java)
                intent.putExtra(Product.PRODUCT, product)
                mContext.startActivity(intent)
            }
        }

    }
}