package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
import com.jt.web.service.ItemService;

@Controller
@RequestMapping("/items")
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	@RequestMapping(value="/{itemId}")
	public String findItemById(//动态路径
						@PathVariable Long itemId,
						Model model){
		//根据商品id(从后台)查询商品信息和商品详情信息
		Item item=itemService.findItemById(itemId);
		
		ItemDesc itemDesc=itemService.finItemDescById(itemId);
		
		//将数据保存在request域中
		model.addAttribute("item",item);
		model.addAttribute("itemDesc", itemDesc);
		//跳转页面
		return "item";
	}
}
