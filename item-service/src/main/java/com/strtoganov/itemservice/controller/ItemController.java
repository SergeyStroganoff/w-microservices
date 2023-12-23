package com.strtoganov.itemservice.controller;

import com.strtoganov.itemservice.domain.model.item.Item;
import com.strtoganov.itemservice.service.item.ItemService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@NoArgsConstructor
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/welcome")
    public String getGreetings() {
        return "This is welcome item message";
    }

    @GetMapping("/")
    public String getItemsList() {
        return "Www";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        Item item = new Item();
        item.setId(id);
        itemService.delete(item);
        return ResponseEntity.ok("Item with id: " + id + "deleted");
    }

    @PostMapping("/")
    public ResponseEntity<Item> addNewItem(@RequestBody Item item) {
        return ResponseEntity.ok().body(itemService.save(item));
    }

    @GetMapping("/{article}/{producer}/{style}")
    Item findItemByModelNameAndStyleArticle(@PathVariable String article,
                                            @PathVariable String producer,
                                            @PathVariable String style) {
        Item item = null;
        Optional<Item> itemOptional = itemService.findItemByModelNameAndStyleArticle(article, producer, style);
        if (itemOptional.isPresent()) {
            item = itemOptional.get();
        }
        return item;
    }
}
