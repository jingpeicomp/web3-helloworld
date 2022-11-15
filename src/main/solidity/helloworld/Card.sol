// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.0;

//定义商品基础合约
abstract contract Goods {
    //卖家
    address payable public owner;
    //买家
    address payable public buyer;
    //商品ID。如果商品是充值卡的话，就是充值卡ID
    string id;
    //价格（单位wei）
    uint256 price;
    //商品（订单）状态定义，
    enum State {
        Created,
        Locked,
        Completed
    }
    //商品状态
    State public state;

    modifier condition(bool condition_) {
        require(condition_);
        _;
    }

    // Only the buyer can call this function.
    error OnlyBuyer();
    // Only the seller can call this function.
    error OnlyOwner();
    // The function cannot be called at the current state.
    error InvalidState();

    modifier onlyBuyer() {
        require(msg.sender == buyer, "Only buyer can call this.");
        _;
    }

    modifier onlyOwner() {
        require(msg.sender == owner, "Only owner can call this.");
        _;
    }

    modifier inState(State _state) {
        require(state == _state, "Invalid state.");
        _;
    }

    event Buy();
    event ownerConfirmed();

    constructor(string memory _id, uint256 _price) payable {
        owner = payable(msg.sender);
        id = _id;
        price = _price;
    }

    function getId() external view returns (string memory) {
        return id;
    }

    function getPrice() external view returns (uint) {
        return price;
    }

    /// 买家购买
    function buy()
        external
        payable
        inState(State.Created)
        condition(msg.value == price)
    {
        emit Buy();
        buyer = payable(msg.sender);
        state = State.Locked;
        buyer.transfer(price);
    }

    /// 卖家确认
    function confirm() external onlyOwner inState(State.Locked) {
        emit Buy();
        state = State.Completed;
    }
}

//定义充值卡合约
contract Card is Goods {
    //卡基本信息（权益）
    struct Info {
        //时长（月份）
        int8 months;
        //支持设备数
        int8 sessions;
    }

    //卡密，通过卡密可以使用充值卡
    string cardSecret;

    constructor(
        string memory _id,
        string memory _cardSecret,
        uint256 _price
    ) public Goods(_id, _price) {
        cardSecret = _cardSecret;
    }

    //获取卡密，只有订单状态为完成，并且身份为买家才可以查看卡密
    function getCardSecret()
        external
        view
        onlyBuyer
        inState(State.Completed)
    returns (string memory)
    {
        return cardSecret;
    }
}
