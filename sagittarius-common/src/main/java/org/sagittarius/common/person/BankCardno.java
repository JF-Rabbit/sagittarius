package org.sagittarius.common.person;

import java.util.Random;

public class BankCardno {

	/**
	 * 随机获取一个银行卡卡号
	 * 
	 * @return
	 */
	public static String getRandomBankCardNo() {
		EnumBankCard enumBankCard = EnumBankCard.values()[new Random().nextInt(EnumBankCard.values().length)];

		StringBuilder bankCardNo = new StringBuilder();
		bankCardNo.append(enumBankCard.getCardBin());
		for (int i = 0; i < enumBankCard.cardLength - enumBankCard.getCardBin().length(); i++) {
			bankCardNo.append("1234567890".charAt(new Random().nextInt(10)));
		}

		return bankCardNo.toString();
	}

	/**
	 * 获取指定银行的随机银行卡号
	 * 
	 * @param enumBankCard
	 *            EnumBankCard 枚举
	 * @return
	 */
	public static String getRandomBankCardNo(EnumBankCard enumBankCard) {

		StringBuilder bankCardNo = new StringBuilder();
		bankCardNo.append(enumBankCard.getCardBin());
		for (int i = 0; i < enumBankCard.cardLength - enumBankCard.getCardBin().length(); i++) {
			bankCardNo.append("1234567890".charAt(new Random().nextInt(10)));
		}

		return bankCardNo.toString();
	}

}
