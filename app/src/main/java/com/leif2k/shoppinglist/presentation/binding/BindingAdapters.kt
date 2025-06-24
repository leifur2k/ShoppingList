package com.leif2k.shoppinglist.presentation.binding

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.leif2k.shoppinglist.R
import com.leif2k.shoppinglist.domain.ShopItem

@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_input_name)
    } else {
        null
    }
    textInputLayout.error = message
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_input_count)
    } else {
        null
    }
    textInputLayout.error = message
}

@BindingAdapter("shopItemCount")
fun bindShopItemCount(textInputEditText: TextInputEditText, shopItem: ShopItem?) {
    shopItem?.let { textInputEditText.setText(shopItem.count.toString()) }
}