package com.leif2k.shoppinglist.data

import com.leif2k.shoppinglist.domain.ShopItem
import com.leif2k.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId } ?: throw RuntimeException("Element id $shopItemId wasn't found")
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id ==ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)

    }

    override fun editShopItem(shopItem: ShopItem) {
        val item = getShopItem(shopItem.id)
        shopList.remove(item)
        shopList.add(shopItem)

    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }
}