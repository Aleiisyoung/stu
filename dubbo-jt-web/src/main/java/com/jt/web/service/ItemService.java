package com.jt.web.service;

import java.util.List;

import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;

public interface ItemService {

	Item findItemById(Long itemId);

	ItemDesc finItemDescById(Long itemDescId);

	List<Item> findKey(String key);


}
