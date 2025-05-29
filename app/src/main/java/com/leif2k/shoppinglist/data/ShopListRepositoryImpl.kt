package com.leif2k.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leif2k.shoppinglist.domain.ShopItem
import com.leif2k.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>({ o1, o2-> o1.id.compareTo(o2.id) })

    init {
        for (i in 0 until 1000) {
            addShopItem(ShopItem("Item $i", i, Random.nextBoolean()))
        }
    }

    private var autoIncrementId = 0

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId }
            ?: throw RuntimeException("Element id $shopItemId wasn't found")
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val item = getShopItem(shopItem.id)
        shopList.remove(item)
        addShopItem(shopItem)
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    private fun updateList() {
        shopListLD.value = shopList.toList()
    }
}