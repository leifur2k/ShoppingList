package com.leif2k.shoppinglist.domain

class RemoveShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun removeShopItem(shopItemId: Int) {
        shopListRepository.removeShopItem(shopItemId)
    }
}