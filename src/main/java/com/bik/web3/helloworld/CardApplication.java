package com.bik.web3.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.UUID;

/**
 * 应用启动类
 *
 * @author Mingo.Liu
 * @date 2022-11-15
 */
@Slf4j
public class CardApplication {
    private static final String NODE_URL = System.getenv().getOrDefault("WEB3J_NODE_URL", "http://127.0.0.1:7545");
    private static final String WALLET_PRIVATE_KEY = System.getenv().getOrDefault("WALLET_PRIVATE_KEY", "ad4a99143cfacf019e6c3921a3956e31a0ef837844a00011a6a40d9cbb8d6d0b");
    private static final Web3j WEB3J = Web3j.build(new HttpService(NODE_URL));

    private static final CardService cardService = new CardService();


    public static void main(String[] args) throws Exception {
//        create();
        String contractAddress = "0x908D9A92f3C3E35CBa342C36C49E216174CE3b6c";

        Credentials credentials = Credentials.create(WALLET_PRIVATE_KEY);
        Credentials buyerCredentials = Credentials.create("ab2cbc37ccfc400cb2f44be1effb75bbab2fa9c7e4d2682b8a1ca41232c0ab30");

//        cardService.buy(WEB3J, buyerCredentials, contractAddress);
//        cardService.confirm(WEB3J, credentials, contractAddress);
        String[] cardIdAndSecret = cardService.getCard(WEB3J, buyerCredentials, contractAddress);
        log.info("Buyer gets card id {} and secret {}", cardIdAndSecret[0], cardIdAndSecret[1]);
    }

    private static void create() throws Exception {
        String cardId = UUID.randomUUID().toString().replace("-", "");
        String cardSecret = UUID.randomUUID().toString().replace("-", "");
        BigInteger price = BigInteger.valueOf(999999999L);

        Credentials credentials = Credentials.create(WALLET_PRIVATE_KEY);
        cardService.create(WEB3J, credentials, cardId, cardSecret, price);
    }
}
