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

            itemView.text_view_category_name.text = category.catName

            itemView.setOnClickListener {
                var intent = Intent(mContext, SubCategoryActivity::class.java)
                intent.putExtra("ID", category.catId.toString())
                mContext.startActivity(intent)
            }
        }

    }
}