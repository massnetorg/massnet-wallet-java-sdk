package net.massnet.signer

import net.massnet.signer.ByteUtils.hexToBytes
import net.massnet.signer.ByteUtils.toHexString
import org.bitcoinj.core.ECKey
import org.bitcoinj.script.Script
import org.junit.jupiter.api.Test

object ProtoTest {
    @Test
    fun testParseTransaction() {
        val hex =
            "080112a2010a260a240969c72a868f3e91fa119725ce1adda871a719ec72816e1f5456552115b8943e8a8cf42612484730440220730a00e8b563a84395b45e32b8e289dc2c721b42b570ced99eb3df508707806302200e8ed192760e3cf4de85e5aa8f1abfee0b3ff89a6afcca45bce2e1ab907a2d28011225512102a7fbac3213b58b53f489a866e3ce0e3a2e19886a212ea06e7f61b9b111d82bca51ae19ffffffffffffffff1a290880c2d72f12220020f574b7a463d02a1df76f204b1db346c280cc2bf5ab006d6dee6ae588c2a951ae1a2a08f0b7d7f0241222002052586f0c1017b3497a0745d6a5390cd26e34f3d3acb9f1a0d3da7c487e74e42b".hexToBytes()
        val transaction = Proto.Tx.parseFrom(hex)

        println(transaction)

        transaction.txOutList.forEach {
            val script = Script(it.pkScript.toByteArray())
            println("Out")
            println(script)
            println(script.program.toHexString())
        }

        transaction.txInList.flatMap { t -> t.witnessList }.forEach {
            val script = Script(it.toByteArray())
            println("In")
            println(script)
            println(script.program.toHexString())
        }

        println(transaction.toByteArray().toHexString())
    }

    @Test
    fun testECKey() {
        val priv = "649ce9ec4cf87af806ded9fb95a4eafe55c2e045ba7a16ca7d3ea20a63ac582e"
        val key = ECKey.fromPrivate(priv.hexToBytes(), true)
        println(key.pubKey.toHexString())
    }
}