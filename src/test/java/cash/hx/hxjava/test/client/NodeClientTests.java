package cash.hx.hxjava.test.client;

import cash.hx.hxjava.client.NodeClient;
import cash.hx.hxjava.client.response.AmountInfoResponse;
import cash.hx.hxjava.client.response.AssetInfoResponse;
import cash.hx.hxjava.client.response.TxOperationReceiptResponse;
import cash.hx.hxjava.operation.NodeException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NodeClientTests {
    private static final Logger log = LoggerFactory.getLogger(NodeClientTests.class);

    @Test
    public void testNodeClientGetInfo() throws NodeException {
        String nodeRpcEndpoint = "ws://nodeapi2.hxlab.org:6090";
        NodeClient nodeClient = new NodeClient(nodeRpcEndpoint);
        try {
            nodeClient.open();
            nodeClient.sendLogin();
            String addr = "";
            String pubKeyStr = "";
            String contractId = "HXCJb6BiDdSL9Duo9UtXaxncyRHcNsfQB7cV";
            String contractApi = "tokenName";
            String contractArg = "";
            String contractTxId = "";
            List<AmountInfoResponse> balances = nodeClient.getAddrBalances(addr);
            List<AssetInfoResponse> assets = nodeClient.listAssets();
            JSONObject testingResult = nodeClient.invokeContractTesting(pubKeyStr, contractId, contractApi, contractArg);
            String invokeResult = nodeClient.invokeContractOffline(pubKeyStr, contractId, contractApi, contractArg);
            log.info("balances: {}", JSON.toJSONString(balances));
            log.info("assets: {}", JSON.toJSONString(assets));
            log.info("testing result: {}", testingResult.toJSONString());
            log.info("invoke result: {}", invokeResult);
            String refInfo = nodeClient.constructRefInfo(6648413, "0065725d686b75c3d6576ecc17d5161c260d6dcd");
            log.info("example refInfo: {}", refInfo);
            JSONObject blockInfo = nodeClient.getBlock(100);
            log.info("blockInfo: {}", JSON.toJSONString(blockInfo));
            List<TxOperationReceiptResponse> txReceipts = nodeClient.getContractTxReceipts(contractTxId);
            log.info("txReceipts: {}", JSON.toJSONString(txReceipts));
        } finally {
            nodeClient.close();
        }
    }
}
