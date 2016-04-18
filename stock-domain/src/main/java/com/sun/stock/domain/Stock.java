package com.sun.stock.domain;

import com.sun.stock.domain.enums.StockType;

import java.io.Serializable;

/**
 * Created by zksun on 16-2-10.
 */
public class Stock implements Serializable {
	/**
	 * ��Ʊid
	 */
	private String stockId;
	/**
	 * �ɽ���
	 */
	private Long tradePrice;
	/**
	 * ��Ȩ��
	 */
	private Long excludePrice;
	/**
	 * ��Ʊ����
	 */
	private StockType stockType;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Long getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(Long tradePrice) {
		this.tradePrice = tradePrice;
	}

	public Long getExcludePrice() {
		return excludePrice;
	}

	public void setExcludePrice(Long excludePrice) {
		this.excludePrice = excludePrice;
	}

	public StockType getStockType() {
		return stockType;
	}

	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}

	@Override
	public String toString() {
		return "Stock{" +
				"stockId='" + stockId + '\'' +
				", tradePrice=" + tradePrice +
				", excludePrice=" + excludePrice +
				", stockType=" + stockType +
				'}';
	}
}
