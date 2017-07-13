package org.sagittarius.common.person;

/**
 * 常用银行卡枚举(cardBin只提供一种)
 * 
 * @author jasonzhang 2017年5月10日 上午11:02:40
 *
 */
public enum EnumBankCard {
	/** 邮政储蓄 */
	PSBC("邮政储蓄", "0025840", "621098", 19),
	/** 工商银行 */
	ICBC("工商银行", "1021000", "623062", 19),
	/** 农业银行 */
	ABC("农业银行", "1031000", "95595", 19),
	/** 中国银行 */
	BC("中国银行", "1041000", "601382", 19),
	/** 建设银行 */
	CBC("建设银行", "1051000", "622280", 19),
	/** 交通银行 */
	BCM("交通银行", "3011000", "622260", 19),
	/** 中信银行 */
	CCB("中信银行", "3021000", "620527", 19),
	/** 光大银行 */
	CEB("光大银行", "3031000", "620535", 19),
	/** 华夏银行 */
	HXB("华夏银行", "3041000", "623020", 16),
	/** 民生银行 */
	CMBC("民生银行", "3051000", "415599", 16),
	/** 广发银行 */
	GDB("广发银行", "3065810", "623259", 19),
	/** 招商银行 */
	CMB("招商银行", "3085840", "622580", 16),
	/** 兴业银行 */
	CIB("兴业银行", "3091000", "438588", 18),
	/** 浦发银行 */
	SPDB("浦发银行", "3102900", "621351", 16),
	/** 北京银行 */
	BOB("北京银行", "3131000", "623561", 19),
	/** 平安银行 */
	PAB("平安银行", "3135840", "622986", 16),
	/** 上海银行 */
	BOS("上海银行", "3135841", "621005", 18),;

	/**
	 * @param cardName
	 * @param bankNo
	 * @param cardBin
	 * @param cardLength
	 */
	private EnumBankCard(String cardName, String bankNo, String cardBin, int cardLength) {
		this.cardName = cardName;
		this.bankNo = bankNo;
		this.cardBin = cardBin;
		this.cardLength = cardLength;
	}

	String cardName;
	String bankNo;
	String cardBin;
	int cardLength;

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public int getCardLength() {
		return cardLength;
	}

	public void setCardLength(int cardLength) {
		this.cardLength = cardLength;
	}

	@Override
	public String toString() {
		return this.cardName;
	}

}
