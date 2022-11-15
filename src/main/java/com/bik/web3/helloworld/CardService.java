package com.bik.web3.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

/**
 * 充值卡业务接口
 *
 * @author Mingo.Liu
 * @date 2022-11-15
 */
@Slf4j
public class CardService {
    /**
     * 平台创建充值卡
     *
     * @param web3j       web3j客户端
     * @param credentials 用户身份
     * @param cardId      充值卡ID
     * @param cardSecret  充值卡卡密
     * @param price       充值卡价格
     * @return string 虚拟卡合约地址
     * @throws Exception 异常
     */
    public String create(Web3j web3j, Credentials credentials, String cardId, String cardSecret, BigInteger price) throws Exception {
        ContractGasProvider gasProvider = new DefaultGasProvider();
        Card card = Card.deploy(web3j, credentials, gasProvider, cardId, cardSecret, price).send();
        log.info("---> Contract address: {}, owner: {}, id: {}", card.getContractAddress(), card.owner(), card.getId());
        return card.getContractAddress();
    }

    /**
     * 购买卡片
     *
     * @param web3j               web3j客户端
     * @param credentials         用户身份
     * @param cardContractAddress 充值卡合约地址
     * @throws Exception 异常
     */
    public void buy(Web3j web3j, Credentials credentials, String cardContractAddress) throws Exception {
        ContractGasProvider gasProvider = new DefaultGasProvider();
        Card card = Card.load(cardContractAddress, web3j, credentials, gasProvider);
        BigInteger price = card.getPrice().send();
        card.buy(price).send();
        log.info("---> Contract buyer: {}", card.buyer().send());
    }

    /**
     * 卖家确认，对应到线下就是卖家收到买家钱后发货
     *
     * @param web3j               web3j客户端
     * @param credentials         用户身份
     * @param cardContractAddress 充值卡合约地址
     * @throws Exception 异常
     */
    public void confirm(Web3j web3j, Credentials credentials, String cardContractAddress) throws Exception {
        ContractGasProvider gasProvider = new DefaultGasProvider();
        Card card = Card.load(cardContractAddress, web3j, credentials, gasProvider);
        card.confirm().send();
    }

    /**
     * 卖家确认后，买家才可以从充值卡合约获取卡密到业务系统充值(只有买家可以查询)
     *
     * @param web3j               web3j客户端
     * @param credentials         用户身份
     * @param cardContractAddress 充值卡合约地址
     * @return 卡号和卡密数组（可以到业务系统充值）
     * @throws Exception 异常
     */
    public String[] getCard(Web3j web3j, Credentials credentials, String cardContractAddress) throws Exception {
        ContractGasProvider gasProvider = new DefaultGasProvider();
        Card card = Card.load(cardContractAddress, web3j, credentials, gasProvider);
        String cardId = card.getId().send();
        String cardSecret = card.getCardSecret().send();
        return new String[]{cardId, cardSecret};
    }
}
