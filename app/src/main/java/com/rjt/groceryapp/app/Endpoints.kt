package com.rjt.groceryapp.app

class Endpoints {

    companion object{
        private val URL_CATEGORY: String = "category"
        private val URL_SUBCATEGORY: String = "subcategory/"
        private val URL_PRODUCTS: String = "products/"

        fun getCategory(): String {
            return Config.BASE_URL + URL_CATEGORY
        }

        fun getSubCategoryByCatId(catId: Int): String{
            return Config.BASE_URL + URL_SUBCATEGORY + catId
        }

        fun getProductBySubId(subId : Int) : String{
            return Config.BASE_URL + URL_PRODUCTS + subId
        }

    }

}