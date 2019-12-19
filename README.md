hxjava
=========

hx java sdk


# Usage

* `mvn package install -Dmaven.test.skip=true` to install lib locally
* add dependency to pom.xml or build.gradle

# Demo

* create and sign transfer transaction

```
String refInfo = "30375,575365464";
String chainId = Constants.mainnetChainId;
String wifStr = "";
String fromAddr = "";
String toAddr = "";
BigDecimal amount = new BigDecimal("0.001");
BigDecimal fee = new BigDecimal("0.0011");
String memo = "test";
Transaction tx = TransactionBuilder.createTransferTransaction(refInfo, fromAddr, toAddr, amount, "1.3.0", 5, fee, memo, null);
String txJson = TransactionBuilder.signTransaction(tx, wifStr, chainId, Address.ADDRESS_PREFIX);
log.info("signed tx: {}", txJson);
// then you can use client rpc's lightwallet_broadcast or node rpc to broadcast this signed transaction json
```

* invoke contract transaction

```
String refInfo = "16573,2299525098";
String chainId = Constants.mainnetChainId;
String wifStr = "";
String callerAddr = "";
String callerPubKey = "";
String contractId = "HXCHcRE3jsyHGrtoE2ZJZtpHsEiYTQ7VrHkb";
String contractApi = "balanceOf";
String contractArg = "";

long gasLimit = 10000;
long gasPrice = 1;

BigDecimal fee = new BigDecimal("0.003");

Transaction tx = TransactionBuilder.createContractInvokeTransaction(refInfo, callerAddr, callerPubKey,
        contractId, contractApi, contractArg, fee, gasLimit, gasPrice, null);
String txJson = TransactionBuilder.signTransaction(tx, wifStr, chainId, Address.ADDRESS_PREFIX);
log.info("signed tx: {}", txJson);
```

* generate private key and address

```
ECKey ecKey = PrivateKeyGenerator.generate();
String privateKeyHex = ecKey.getPrivateKeyAsHex();
String wif = PrivateKeyUtil.privateKeyToWif(ecKey);
log.info("privateKeyHex: {}", privateKeyHex);
log.info("privateKey wif: {}", wif);
Address address = Address.fromPubKey(ecKey.getPubKey(), AddressVersion.NORMAL);
log.info("address: {}", address);
```