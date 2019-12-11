package cash.hx.hxjava.asset;

public class AssetUtil {
    public static Asset defaultAsset() {
        Asset asset = new Asset();
        asset.setAmount(0);
        asset.setAssetId("1.3.0");
        return asset;
    }
}
