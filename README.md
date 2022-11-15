# 以太坊智能合约 Demo

## 代码说明

项目模拟了以太坊充值卡智能合约创建、购买、使用流程。

项目代码分为两部分：

1. 使用 Solidity 实现了充值卡的智能合约，代码地址: [Card.sol](./src/main/solidity/helloworld/Card.sol)
2. 使用 Java Web3j 框架实现了以太坊客户端交互，代码地址：
   - 项目入口： [CardApplication.java](./src/main/java/com/bik/web3/helloworld/CardApplication.java)
   - 接口交互： [CardService.java](./src/main/java/com/bik/web3/helloworld/CardService.java)

## 打包执行

### 环境安装

首先需要安装 Solidity 编译环境和 Web3j cli，安装方法见：

- Solidity 编译器安装教程：![https://docs.soliditylang.org/en/v0.8.17/installing-solidity.html](https://docs.soliditylang.org/en/v0.8.17/installing-solidity.html)
- Web3j cli 安装教程：![https://github.com/web3j/web3j-cli](https://github.com/web3j/web3j-cli)

安装本地以太坊测试网络，作者使用的是 Ganache 搭建了一个本地测试网。Ganache 安装教程：![https://trufflesuite.com/docs/ganache/quickstart](https://trufflesuite.com/docs/ganache/quickstart/)

### 编译

1. 编译智能合约

```shell
git clone https://github.com/jingpeicomp/web3-helloworld.git
cd web3-helloworld
solc src/main/solidity/helloworld/Card.sol --bin --abi --optimize --overwrite  -o ./contract
```

2. 用 Web3j 生成合约包装类

```shell
cd web3-helloworld
web3j generate solidity --binFile=./contract/Card.bin --abiFile=./contract/Card.abi -o ./src/main/java -p com.bik.web3.helloworld
```

### 执行

启动 Ganache，获取相关账号信息，并设置到 CardApplication 变量中，执行 main 方法即可。
