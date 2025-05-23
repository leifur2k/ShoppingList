package com.leif2k.shoppinglist.domain

interface ShopListRepository {
    fun getShopList(): List<ShopItem>

    fun getShopItem(shopItemId: Int): ShopItem

    fun addShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun removeShopItem(shopItem: ShopItem)
}
