package com.main.dscountr.product

import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.main.dscountr.R
import com.main.dscountr.adapter.RvAdapter

class ProductCategoriesActivity: AppCompatActivity() {

    private val itemList: Array<String>
        get() = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_categories)

        val gridview = findViewById<GridView>(R.id.gridview)

        val adapter = RvAdapter(this, R.layout.product_categories_item_layout, itemList)
        gridview.adapter = adapter
        gridview.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->
            Toast.makeText(this@ProductCategoriesActivity, " Clicked Position: " + (position + 1),
                Toast.LENGTH_SHORT).show()

        }
    }
}
