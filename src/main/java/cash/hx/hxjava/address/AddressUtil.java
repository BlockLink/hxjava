package cash.hx.hxjava.address;

public class AddressUtil {

    /**
     * validate address format
     * @param address
     * @param isTestnet is this chain regtest net
     * @return
     */
    public static boolean validateNormalAddress(String address, boolean isTestnet) {
        try {
            String prefix = isTestnet ? Address.TESTNET_ADDRESS_PREFIX : Address.ADDRESS_PREFIX;
            Address addrObj = Address.fromString(address, prefix);
            return addrObj.getValue(prefix).equals(address) && AddressVersion.NORMAL == addrObj.getVersion();
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }
}
