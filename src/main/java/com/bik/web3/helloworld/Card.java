package com.bik.web3.helloworld;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class Card extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051620008d3380380620008d383398101604081905261003191610126565b600080546001600160a01b0319163317905582816002610051838261021b565b50600355506005610062838261021b565b505050506102da565b634e487b7160e01b600052604160045260246000fd5b600082601f83011261009257600080fd5b81516001600160401b03808211156100ac576100ac61006b565b604051601f8301601f19908116603f011681019082821181831017156100d4576100d461006b565b816040528381526020925086838588010111156100f057600080fd5b600091505b8382101561011257858201830151818301840152908201906100f5565b600093810190920192909252949350505050565b60008060006060848603121561013b57600080fd5b83516001600160401b038082111561015257600080fd5b61015e87838801610081565b9450602086015191508082111561017457600080fd5b5061018186828701610081565b925050604084015190509250925092565b600181811c908216806101a657607f821691505b6020821081036101c657634e487b7160e01b600052602260045260246000fd5b50919050565b601f82111561021657600081815260208120601f850160051c810160208610156101f35750805b601f850160051c820191505b81811015610212578281556001016101ff565b5050505b505050565b81516001600160401b038111156102345761023461006b565b610248816102428454610192565b846101cc565b602080601f83116001811461027d57600084156102655750858301515b600019600386901b1c1916600185901b178555610212565b600085815260208120601f198616915b828110156102ac5788860151825594840194600190910190840161028d565b50858210156102ca5787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b6105e980620002ea6000396000f3fe60806040526004361061007b5760003560e01c80638da5cb5b1161004e5780638da5cb5b1461010f57806398d5fdca1461012f578063a6f2ae3a1461014d578063c19d93fb1461015557600080fd5b80635d1ca63114610080578063689f6d2f146100ab5780637022b58e146100c05780637150d8ae146100d7575b600080fd5b34801561008c57600080fd5b5061009561017c565b6040516100a291906104c5565b60405180910390f35b3480156100b757600080fd5b5061009561020e565b3480156100cc57600080fd5b506100d5610338565b005b3480156100e357600080fd5b506001546100f7906001600160a01b031681565b6040516001600160a01b0390911681526020016100a2565b34801561011b57600080fd5b506000546100f7906001600160a01b031681565b34801561013b57600080fd5b506003546040519081526020016100a2565b6100d5610402565b34801561016157600080fd5b5060045461016f9060ff1681565b6040516100a29190610529565b60606002805461018b90610551565b80601f01602080910402602001604051908101604052809291908181526020018280546101b790610551565b80156102045780601f106101d957610100808354040283529160200191610204565b820191906000526020600020905b8154815290600101906020018083116101e757829003601f168201915b5050505050905090565b6001546060906001600160a01b031633146102705760405162461bcd60e51b815260206004820152601960248201527f4f6e6c792062757965722063616e2063616c6c20746869732e0000000000000060448201526064015b60405180910390fd5b60028060045460ff16600281111561028a5761028a610513565b146102a75760405162461bcd60e51b81526004016102679061058b565b600580546102b490610551565b80601f01602080910402602001604051908101604052809291908181526020018280546102e090610551565b801561032d5780601f106103025761010080835404028352916020019161032d565b820191906000526020600020905b81548152906001019060200180831161031057829003601f168201915b505050505091505090565b6000546001600160a01b031633146103925760405162461bcd60e51b815260206004820152601960248201527f4f6e6c79206f776e65722063616e2063616c6c20746869732e000000000000006044820152606401610267565b60018060045460ff1660028111156103ac576103ac610513565b146103c95760405162461bcd60e51b81526004016102679061058b565b6040517f974c86b588512b7bcccd8d013809b53141f70434ba97c6fcaa58341cc9b1e06290600090a1506004805460ff19166002179055565b60008060045460ff16600281111561041c5761041c610513565b146104395760405162461bcd60e51b81526004016102679061058b565b60035434148061044857600080fd5b6040517f974c86b588512b7bcccd8d013809b53141f70434ba97c6fcaa58341cc9b1e06290600090a1600180546001600160a01b0319163390811782556004805460ff191690921790915560035460405181156108fc0291906000818181858888f193505050501580156104c0573d6000803e3d6000fd5b505050565b600060208083528351808285015260005b818110156104f2578581018301518582016040015282016104d6565b506000604082860101526040601f19601f8301168501019250505092915050565b634e487b7160e01b600052602160045260246000fd5b602081016003831061054b57634e487b7160e01b600052602160045260246000fd5b91905290565b600181811c9082168061056557607f821691505b60208210810361058557634e487b7160e01b600052602260045260246000fd5b50919050565b6020808252600e908201526d24b73b30b634b21039ba30ba329760911b60408201526060019056fea26469706673582212208a1a48b7ea5c7c8377203d99f47a3d7aba5284dd2e1ae0a6b37f7a48d2514c8264736f6c63430008110033";

    public static final String FUNC_BUY = "buy";

    public static final String FUNC_BUYER = "buyer";

    public static final String FUNC_CONFIRM = "confirm";

    public static final String FUNC_GETCARDSECRET = "getCardSecret";

    public static final String FUNC_GETID = "getId";

    public static final String FUNC_GETPRICE = "getPrice";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_STATE = "state";

    public static final Event BUY_EVENT = new Event("Buy", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event OWNERCONFIRMED_EVENT = new Event("ownerConfirmed", 
            Arrays.<TypeReference<?>>asList());
    ;

    @Deprecated
    protected Card(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Card(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Card(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Card(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<BuyEventResponse> getBuyEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BUY_EVENT, transactionReceipt);
        ArrayList<BuyEventResponse> responses = new ArrayList<BuyEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BuyEventResponse typedResponse = new BuyEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BuyEventResponse> buyEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BuyEventResponse>() {
            @Override
            public BuyEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BUY_EVENT, log);
                BuyEventResponse typedResponse = new BuyEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<BuyEventResponse> buyEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BUY_EVENT));
        return buyEventFlowable(filter);
    }

    public static List<OwnerConfirmedEventResponse> getOwnerConfirmedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERCONFIRMED_EVENT, transactionReceipt);
        ArrayList<OwnerConfirmedEventResponse> responses = new ArrayList<OwnerConfirmedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnerConfirmedEventResponse typedResponse = new OwnerConfirmedEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnerConfirmedEventResponse> ownerConfirmedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnerConfirmedEventResponse>() {
            @Override
            public OwnerConfirmedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERCONFIRMED_EVENT, log);
                OwnerConfirmedEventResponse typedResponse = new OwnerConfirmedEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<OwnerConfirmedEventResponse> ownerConfirmedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERCONFIRMED_EVENT));
        return ownerConfirmedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> buy(BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BUY, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<String> buyer() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BUYER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> confirm() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CONFIRM, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getCardSecret() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCARDSECRET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getPrice() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> state() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_STATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static Card load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Card(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Card load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Card(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Card load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Card(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Card load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Card(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Card> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _id, String _cardSecret, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id), 
                new org.web3j.abi.datatypes.Utf8String(_cardSecret), 
                new org.web3j.abi.datatypes.generated.Uint256(_price)));
        return deployRemoteCall(Card.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Card> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _id, String _cardSecret, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id), 
                new org.web3j.abi.datatypes.Utf8String(_cardSecret), 
                new org.web3j.abi.datatypes.generated.Uint256(_price)));
        return deployRemoteCall(Card.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Card> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _id, String _cardSecret, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id), 
                new org.web3j.abi.datatypes.Utf8String(_cardSecret), 
                new org.web3j.abi.datatypes.generated.Uint256(_price)));
        return deployRemoteCall(Card.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Card> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _id, String _cardSecret, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_id), 
                new org.web3j.abi.datatypes.Utf8String(_cardSecret), 
                new org.web3j.abi.datatypes.generated.Uint256(_price)));
        return deployRemoteCall(Card.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class BuyEventResponse extends BaseEventResponse {
    }

    public static class OwnerConfirmedEventResponse extends BaseEventResponse {
    }
}
