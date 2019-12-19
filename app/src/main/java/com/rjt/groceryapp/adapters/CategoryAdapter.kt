package com.rjt.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rjt.groceryapp.R
import com.rjt.groceryapp.activities.SubCategoryActivity
import com.rjt.groceryapp.models.Category
import kotlinx.android.synthetic.main.row_category_adapter.view.*

class CategoryAdapter(var mContext: Context, var mList: ArrayList<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_category_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var category = mList.get(position)
        holder.bindView(category)
    }

    fun setData (list : ArrayList<Category>){
        mList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(category: Category){
            if (position == 0) {
                itemView.category_image.setImageResource(R.drawable.ic_local_drink_oriange_24dp)
            } else if (position == 1) {
                itemView.category_image.setImageResource(R.drawable.ic_free_breakfast_dairy_24dp)
            } else if (position == 2) {
                itemView.category_image.setImageResource(R.drawable.ic_spa_green_24dp)
            } else if (position == 3) {
                itemView.category_image.setImageResource(R.drawable.ic_cake_cake_24dp)
            }

            itemView.text_view_category_name.text = category.catName

            itemView.setOnClickListener {
                var intent = Intent(mContext, SubCategoryActivity::class.java)
                intent.putExtra("ID", category.catId)
                intent.putExtra("NAME", category.catName)
                mContext.startActivity(intent)
            }
        }

    }
}