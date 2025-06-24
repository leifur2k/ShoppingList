package com.leif2k.shoppinglist.presentation.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.leif2k.shoppinglist.R

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private var mode = MODE_ADD
    private var shopItemId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.shop_item_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            v.updatePadding(
                left = systemBars.left,
                top = maxOf(systemBars.top, imeInsets.top),
                right = systemBars.right,
                bottom = maxOf(systemBars.bottom, imeInsets.bottom)
            )
            insets
        }

        parseIntent()

        if (savedInstanceState == null) {
            launchRightMode()
        }


    }

    private fun launchRightMode() {
        val fragment = when (mode) {
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            else -> ShopItemFragment.newInstanceAddItem()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (intent.getStringExtra(EXTRA_SCREEN_MODE) == MODE_EDIT) {
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)
            mode = MODE_EDIT
        } else if (intent.getStringExtra(EXTRA_SCREEN_MODE) == MODE_ADD) {
            mode = MODE_ADD
        }
    }

    companion object {
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }
    }

    override fun onEditingFinished() {
        finish()
    }
}