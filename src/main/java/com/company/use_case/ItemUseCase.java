package com.company.use_case;


import com.company.anotation.UseCase;
import com.company.entity.Item;
import com.company.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@UseCase
public class ItemUseCase {

    private final ItemService itemService;

    @Autowired
    public ItemUseCase(ItemService itemService) {
        this.itemService = itemService;
    }

    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    public Item getItemById(Integer id) {
        return itemService.getItemById(id);
    }

    public Item createItem(Item item) {
        return itemService.addItem(item);
    }

    public Item updateItem(Integer id, Item itemDetails) {
        return itemService.updateItem(id, itemDetails);
    }

    public void deleteItem(Integer id) {
        itemService.deleteItem(id);
    }
}
