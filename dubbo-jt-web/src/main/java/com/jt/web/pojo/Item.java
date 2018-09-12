package com.jt.web.pojo;

import javax.annotation.Generated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.context.annotation.ComponentScan.Filter;

import com.jt.common.po.BasePojo;


public class Item extends BasePojo{
	@Field("id")
	private Long id;
	@Field("title")
	private String title;
	@Field("sellPoint")
	private String sellPoint;
	@Field("price")
	private Long price;//用Long计算速度快，精确
	@Field("num")
	private Integer num;//商品数量
	private String barcode;//二维码
	@Field("image")
	private String image;//图片内容:1.png,2.png
	private Long cid;//商品分类id
	private Integer status;
	
	public String[] getImages(){
		//${item.images[0]} 满足数据获取 添加get方法
		return image.split(",");
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
