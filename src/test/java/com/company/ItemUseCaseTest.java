package com.company;

import com.company.entity.Item;
import com.company.repository.ItemRepository;
import com.company.service.ItemService;
import com.company.service.ItemServiceImpl;
import com.company.use_case.ItemUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@Rollback
class ItemUseCaseTest {

    @Autowired
    private ItemRepository itemRepository;

    private ItemService itemService;
    private ItemUseCase itemUseCase;

    @BeforeEach
    void setUp() {
        itemService = new ItemServiceImpl(itemRepository);
        itemUseCase = new ItemUseCase(itemService);
    }

    @Test
    void testGetAllItems() {
        Item item1 = new Item();
        item1.setName("Item 1");
        item1.setPrice(10.0);
        itemRepository.save(item1);

        Item item2 = new Item();
        item2.setName("Item 2");
        item2.setPrice(20.0);
        itemRepository.save(item2);

        List<Item> result = itemUseCase.getAllItems();
        assertEquals(2, result.size());
    }

    @Test
    void testAddItem() {
        Item item = new Item();
        item.setName("New Item");
        item.setPrice(15.0);

        Item createdItem = itemUseCase.createItem(item);

        assertNotNull(createdItem);
        assertEquals("New Item", createdItem.getName());
        assertTrue(itemRepository.findById(createdItem.getId()).isPresent());
    }

    @Test
    void testUpdateItem() {
        Item existingItem = new Item();
        existingItem.setName("Existing Item");
        existingItem.setPrice(20.0);
        itemRepository.save(existingItem);

        Item updatedDetails = new Item();
        updatedDetails.setName("Updated Item");
        updatedDetails.setPrice(25.0);

        Item updatedItem = itemUseCase.updateItem(existingItem.getId(), updatedDetails);

        assertNotNull(updatedItem);
        assertEquals("Updated Item", updatedItem.getName());
        assertEquals(25.0, updatedItem.getPrice());
    }

    @Test
    void testDeleteItem() {
        Item item = new Item();
        item.setName("Item to delete");
        item.setPrice(30.0);
        itemRepository.save(item);

        itemUseCase.deleteItem(item.getId());
        Optional<Item> deletedItem = itemRepository.findById(item.getId());
        assertFalse(deletedItem.isPresent());
    }

    @Test
    void testGetItemById() {
        Item item = new Item();
        item.setName("Item 1");
        item.setPrice(10.0);
        itemRepository.save(item);

        Item result = itemUseCase.getItemById(item.getId());
        assertNotNull(result);
        assertEquals("Item 1", result.getName());
    }
}
