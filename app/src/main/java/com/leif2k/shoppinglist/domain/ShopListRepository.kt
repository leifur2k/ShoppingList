package com.leif2k.shoppinglist.domain

interface ShopListRepository {
    fun getShopList(): List<ShopItem>

    fun getShopItem(shopItemId: Int): ShopItem

    fun addShopItem(shopItem: ShopItem)

    fun editShopItem(shopItemId: Int)

    fun removeShopItem(shopItemId: Int)
}