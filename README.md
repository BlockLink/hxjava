hxjava
=========

hx java sdk


# Usage

* `mvn package install -Dmaven.test.skip=true` to install lib locally
* add dependency to pom.xml or build.gradle

# Demo

```$java
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