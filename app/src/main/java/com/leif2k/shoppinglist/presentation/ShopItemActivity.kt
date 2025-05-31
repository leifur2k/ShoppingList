package com.leif2k.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.leif2k.shoppinglist.R
import com.leif2k.shoppinglist.common.LOG_TAG
import com.leif2k.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var butSave: Button

    private lateinit var viewModel: ShopItemViewModel
    private var mode = MODE_ADD


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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

        initViews()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        parseIntent()
        initClickListeners()
        initObservers()

    }


    private fun parseIntent() {
        if (intent.getStringExtra(EXTRA_SCREEN_MODE) == MODE_EDIT) {
            val shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)
            viewModel.getShopItem(shopItemId)
            mode = MODE_EDIT
        } else if (intent.getStringExtra(EXTRA_SCREEN_MODE) == MODE_ADD) {
            mode = MODE_ADD
        }
    }

    private fun initClickListeners() {
        butSave.setOnClickListener {
            if (mode == MODE_EDIT) {
                viewModel.editShopItem(etName.text.toString(), etCount.text.toString())
            } else if (mode == MODE_ADD){
                viewModel.addShopItem(etName.text.toString(), etCount.text.toString())
            }
        }

        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }
            override fun afterTextChanged(s: Editable?) {
            }

        })

        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun initObservers() {
        viewModel.errorInputName.observe(this) {
            if (it) {
                tilName.error = "Incorrect Name"
            }else {
                tilName.error = null
            }
        }

        viewModel.errorInputCount.observe(this) {
            if (it) {
                tilCount.error = "Incorrect Count"
            }else {
                tilCount.error = null
            }
        }

        viewModel.shopItem.observe(this) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }

        viewModel.isReadyToFinish.observe(this) {
                finish()
        }
    }

    private fun initViews() {
        tilName = findViewById(R.id.tilName)
        tilCount = findViewById(R.id.tilCount)
        etName = findViewById(R.id.etName)
        etCount = findViewById(R.id.etCount)
        butSave = findViewById(R.id.butSave)
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
}