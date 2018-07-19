package com.example.blockchaincertificate

import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.admin.Admin
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.utils.Convert
import java.io.File


/**
 * Created by guo on 2018/7/18.
 * desc:
 */
@Slf4j
@RestController
class TestController {
    private val log = KotlinLogging.logger {}
    @Autowired
    private lateinit var web3j: Web3j
    @Autowired
    private lateinit var admin: Admin

    @RequestMapping("/test")
    fun test(): String? {
        val send = web3j.web3ClientVersion().send()
        return send.web3ClientVersion
    }

    @RequestMapping("/account")
    fun account(): String? {
        val walletFilePath0 = "./"
        //钱包文件保持路径，请替换位自己的某文件夹路径
        val walletFileName0 = WalletUtils.generateNewWalletFile("123456", File(walletFilePath0), false)
        return walletFileName0
    }


    @RequestMapping("/newaccount")
    fun newaccount(): String? {
        val personalNewAccount = admin.personalNewAccount("123456")
        val send = personalNewAccount.sendAsync()
        send.join()
        log.info(send.toString())
        return send.toString()
    }

    @RequestMapping("/loadWallet")
    fun loadWallet() {
        val walleFilePath = "./UTC--2018-07-19T03-46-04.460000000Z--6194ab1ec4a1e67df89537ed913fc014e538b303.json"
        val passWord = "123456"
        var credentials = WalletUtils.loadCredentials(passWord, walleFilePath)
        val address = credentials.getAddress()
        val publicKey = credentials.getEcKeyPair().getPublicKey()
        val privateKey = credentials.getEcKeyPair().getPrivateKey()
        log.info("address=" + address)
        log.info("public key=" + publicKey)
        log.info("private key=" + privateKey)

    }

    /***********查询指定地址的余额 */
    @RequestMapping("/getBlanceOf")
    fun getBlanceOf() {
        val address = "0x6194ab1ec4a1e67df89537ed913fc014e538b303"//等待查询余额的地址
        //第二个参数：区块的参数，建议选最新区块
        val balance = web3j.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send()
        //格式转化 wei-ether
        val blanceETH = Convert.fromWei(balance.balance.toString(), Convert.Unit.ETHER).toPlainString() + " ether"
        log.info(balance.balance.toString())
    }

    @RequestMapping("/transto")
    fun transto() {
//        val send = Transfer.sendFunds(web3j, credentials, address_to, BigDecimal.ONE, Convert.Unit.FINNEY).send()

        val ethGetTransactionCount = web3j.ethGetTransactionCount(
                "0x6194ab1ec4a1e67df89537ed913fc014e538b303", DefaultBlockParameterName.LATEST).sendAsync().get()
        val nonce = ethGetTransactionCount.transactionCount

        val personalUnlockAccount = admin.personalUnlockAccount("0x6194ab1ec4a1e67df89537ed913fc014e538b303", "123456").send()
        if (personalUnlockAccount.accountUnlocked()) {
            val transaction = Transaction.createFunctionCallTransaction("0x6194ab1ec4a1e67df89537ed913fc014e538b303",
                    nonce, 1.toBigInteger(), 21000.toBigInteger(), "0x06Eb0870C6957A2bD3b7FDEdb87d919eEE32BD2c"
                    , "0x7b2074696d653a20313531383933313435323537372c20747970653a2027696e666f272c206d73673a202757656233205465737421212127207d20")


            val send = admin.ethSendTransaction(transaction).send()
            log.info(send.transactionHash)
        }

    }

}