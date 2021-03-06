package cash.hx.hxjava.test.address;

import cash.hx.hxjava.address.Address;
import cash.hx.hxjava.address.AddressUtil;
import cash.hx.hxjava.address.AddressVersion;
import cash.hx.hxjava.exceptions.PubKeyInvalidException;
import cash.hx.hxjava.pubkey.PubKeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressTests {
    private static final Logger log = LoggerFactory.getLogger(AddressTests.class);

    @Test
    public void testValidateAddress() {
        String validateAddress = "HXNg8yzijJL8UiWyN1TRyRhKCQw8G3Wp68ok";
        String invalidateAddress = "HXNg8yzijJL8UiWyN1TRyRhKCQw8Ginvalid";
        boolean valid = AddressUtil.validateNormalAddress(validateAddress, false);
        boolean invalid = AddressUtil.validateNormalAddress(invalidateAddress, false);
        log.info("{} is valid", validateAddress);
        log.info("{} is invalid", invalidateAddress);
        Assert.assertTrue(valid);
        Assert.assertFalse(invalid);
    }

    @Test
    public void testGetAddressByPubKey() throws PubKeyInvalidException {
        String pubKeyStr = "HX84brURxte4VoYqTdxBf9f5gAVeSzSxJKddNjM6bHQFBcB7EZ6b";
        String validAddr = "HXNfnUKMv6jjzQ6LDAtqnFokusBzJd3VJXH6";
        byte[] pubKeyBytes = PubKeyUtil.getPubKeyBytes(pubKeyStr);
        Address address = Address.fromPubKey(pubKeyBytes, AddressVersion.NORMAL);
        String decodedAddr = address.getValue(Address.ADDRESS_PREFIX);
        log.info("decode address: {}, valid address: {}", decodedAddr, validAddr);
        Assert.assertEquals(validAddr, decodedAddr);
    }
}
