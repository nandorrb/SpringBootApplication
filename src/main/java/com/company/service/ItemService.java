package com.company.service;


import com.company.entity.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    Item addItem(Item item);
    Item updateItem(Integer id, Item itemDetails);
    void deleteItem(Integer id);
    Item getItemById(Integer id);
}
