package ch.epfl.dedis.cothority.demo.cothoritydemo;

import ch.epfl.dedis.lib.Roster;
import ch.epfl.dedis.lib.SkipblockId;
import ch.epfl.dedis.lib.Hex;
import ch.epfl.dedis.lib.exception.CothorityCryptoException;
import ch.epfl.dedis.lib.exception.CothorityException;
import ch.epfl.dedis.byzcoin.InstanceId;
import ch.epfl.dedis.byzcoin.ByzCoinRPC;
import ch.epfl.dedis.lib.darc.DarcId;
import ch.epfl.dedis.lib.darc.Signer;
import ch.epfl.dedis.lib.darc.SignerEd25519;

/**
 * For testing with our deployed servers, you may use this class. You will need to ask
 * for the details to fill in here.
 * </pre>
 */
public final class SecureKG {
    /**
     * Gets the roster of the secure KG server.
     * @return the roster
     */
    public static Roster getRoster() {
        return Roster.FromToml("[[servers]]\n" +
                "  Address = \"tls://securekg.dedis.ch:18002\"\n" +
                "  Suite = \"Ed25519\"\n" +
                "  Public = \"13dc1a4f714422e7952cef38efd527925341efaa3a992398cb52fa3e0e6dd2b8\"\n" +
                "  Description = \"Conode_1\"\n" +
                "[[servers]]\n" +
                "  Address = \"tls://securekg.dedis.ch:18004\"\n" +
                "  Suite = \"Ed25519\"\n" +
                "  Public = \"705f2877119a39f366ea53f381e37234f9678dee5f17c9f20b11df7c6cdc0e64\"\n" +
                "  Description = \"Conode_2\"\n" +
                "[[servers]]\n" +
                "  Address = \"tls://securekg.dedis.ch:18006\"\n" +
                "  Suite = \"Ed25519\"\n" +
                "  Public = \"1084f8f919112931b18a545e14e4cb668ba0b6d4884f64b463fe3fa4493b8f0e\"\n" +
                "  Description = \"Conode_3\"\n");
    }

    /**
     * Gets the genesis skipblock ID of an existing byzcoin service.
     * @return the genesis skipblock ID
     */
    public static SkipblockId getSkipchainId() throws CothorityCryptoException {
        return new SkipblockId(Hex.parseHexBinary("347c2e0f2998bb9e4fda92612b43abdf54f65d220043a46e47acd991ecd3c6d7"));
    }

    /**
     * Gets the signer that has "invoke:eventlog" and "spawn:eventlog" permissions.
     */
    public static Signer getSigner() {
        return new SignerEd25519(Hex.parseHexBinary("ea29d91778840fcc567d7bb4c4a82929923aa8ce1ea7ecf16b25106bc0362e00"));
    }

    /**
     * Gets the eventlog instance ID.
     * @return the instance ID.
     */
    public static InstanceId getEventlogId() throws CothorityCryptoException {
        return new InstanceId(Hex.parseHexBinary("24728948c65135c7c866811b8bc77728dd40369c5f60cb2c3590246042676231"));
    }

    /**
     * Get the pre-configured byzcoin RPC.
     * @return the byzcoin RPC object
     */
    public static ByzCoinRPC getbyzcoinRPC() throws CothorityException {
        return ByzCoinRPC.fromByzCoin(getRoster(), getSkipchainId());
    }
}

