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
    private static final String WALLET_PRIVATE_KEY = System.getenv().getOrDefault("WALLET_PRIVATE_KEY", "71ee86fbb0bb2e68189d7d87f68fbf624cc49fbba8de4dbccef145a2d970fdf3");
    private static final Web3j WEB3J = Web3j.build(new HttpService(NODE_URL));

    private static final CardService cardService = new CardService();


    public static void main(String[] args) throws Exception {
        create();

        String contractAddress = "0x81105745F6793F3BDad1aa11F0cD372DB7cE18D5";
        Credentials ownerCredentials = Credentials.create(WALLET_PRIVATE_KEY);
        Credentials buyerCredentials = Credentials.create("b17d910de11dccf41ae6158ba6aec83bba76d832a4aac7996e5a7bafb8c45852");

        cardService.buy(WEB3J, buyerCredentials, contractAddress);
        cardService.confirm(WEB3J, ownerCredentials, contractAddress);
        String[] cardIdAndSecret = cardService.getCard(WEB3J, buyerCredentials, contractAddress);
        log.info("Buyer gets card id {} and secret {}", cardIdAndSecret[0], cardIdAndSecret[1]);
    }

    /**
     * 创建充值卡
     *
     * @throws Exception Exception
     */
    private static void create() throws Exception {
        String cardId = UUID.randomUUID().toString().replace("-", "");
        String cardSecret = UUID.randomUUID().toString().replace("-", "");
        BigInteger price = BigInteger.valueOf(999999999L);

        Credentials credentials = Credentials.create(WALLET_PRIVATE_KEY);
        cardService.create(WEB3J, credentials, cardId, cardSecret, price);
    }
}
