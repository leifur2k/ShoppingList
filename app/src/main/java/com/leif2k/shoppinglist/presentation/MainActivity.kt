package com.leif2k.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.leif2k.shoppinglist.R
import com.leif2k.shoppinglist.common.LOG_TAG

class MainActivity : AppCompatActivity() {

    private lateinit var rvShopList: RecyclerView
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()

        shopListAdapter = ShopListAdapter()
        rvShopList.adapter = shopListAdapter
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_ENABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_DISABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getShopList().observe(this, {
            shopListAdapter.submitList(it)
        })



        shopListAdapter.onShopItemClickListener = {
            Log.d(LOG_TAG, it.toString())
        }

        shopListAdapter.onShopItemLongClickListener = {
            viewModel.editShopItem(it)
        }


        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val shopItem = shopListAdapter.currentList[position]
                viewModel.removeShopItem(shopItem)
            }
        })
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }


    private fun initViews() {
        rvShopList = findViewById(R.id.rvShopList)
    }
}