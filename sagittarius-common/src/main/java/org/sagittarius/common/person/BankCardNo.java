package org.sagittarius.common.person;

import java.util.Random;

public class BankCardNo {

    /**
     * 随机获取一个银行卡卡号
     *
     * @return 返回一个随机银行卡号
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
     * @param enumBankCard EnumBankCard 枚举
     * @return 返回指定银行枚举的卡号
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
