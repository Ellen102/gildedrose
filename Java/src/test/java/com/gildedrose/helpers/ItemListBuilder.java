package com.gildedrose.helpers;

import com.gildedrose.Item;

import java.util.ArrayList;

public class ItemListBuilder {
    private ArrayList<ItemBuilder> items;

    private ItemListBuilder(){
        items = new ArrayList<>();
    }
    public static ItemListBuilder anEmptyItemList() {
        return new ItemListBuilder();
    }

    public Item[] build(){
        return items.stream().map(ItemBuilder::build).toArray(Item[]::new);
    }

    public ItemListBuilder withItem(String name) {
        items.add(new ItemBuilder(name, 1, 1));
        return this;
    }
    public ItemListBuilder withItemBuilder(ItemBuilder itemBuilder) {
        items.add(itemBuilder);
        return this;
    }

}
