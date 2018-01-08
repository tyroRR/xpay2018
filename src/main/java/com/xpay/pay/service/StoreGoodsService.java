package com.xpay.pay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpay.pay.dao.StoreGoodsMapper;
import com.xpay.pay.exception.Assert;
import com.xpay.pay.model.StoreGoods;

@Service
public class StoreGoodsService {
	@Autowired
	private StoreGoodsMapper mapper;
	
	public List<StoreGoods> findByStoreId(long storeId) {
		Assert.isTrue(storeId>0, "Invalid storeId");
		return mapper.findByStoreId(storeId);
	}
	
	public StoreGoods findByCode(String code) {
		Assert.notNull(code, "Invalid code");
		return mapper.findByCode(code);
	}
	
	public boolean insert(StoreGoods goods) {
		Assert.isTrue(goods == null, "Invalid good to be inserted");
		return mapper.insert(goods);
	}
	
	public boolean update(StoreGoods goods) {
		Assert.isTrue(goods == null || goods.getId() == null || goods.getId()>0, "Invalid good to be updated");
		return mapper.updateById(goods);
	}
	
	public boolean delete(StoreGoods goods) {
		Assert.isTrue(goods == null || goods.getId() == null || goods.getId()>0, "Invalid good to be deleted");
		return mapper.deleteById(goods.getId());
	}
}