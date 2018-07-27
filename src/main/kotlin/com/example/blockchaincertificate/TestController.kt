package com.example.blockchaincertificate

import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.admin.Admin
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.tx.Contract
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import org.web3j.utils.Numeric
import java.io.File
import java.math.BigDecimal


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
    @Autowired
    private lateinit var etherscanService: EtherscanService

    private val contractAddress = "0x5af16f99b0a0c5d4cd5b26d50dfcc932993021d1"

    private val jyjAddress = "0x6194ab1ec4a1e67df89537ed913fc014e538b303"



    @RequestMapping("/test")
    fun test(): String? {
        val send = web3j.web3ClientVersion().send()
        return send.web3ClientVersion
    }

    /**
     * 创建钱包
     */
    @RequestMapping("/account")
    fun account(): String? {
        val walletFilePath0 = "./"
        //钱包文件保持路径，请替换位自己的某文件夹路径
        val walletFileName0 = WalletUtils.generateNewWalletFile("123456", File(walletFilePath0), false)
        return walletFileName0
    }

    /**
     * 创建钱包 （测试链不可用）
     */
    @RequestMapping("/newaccount")
    fun newaccount(): String? {
        val personalNewAccount = admin.personalNewAccount("123456")
        val send = personalNewAccount.sendAsync()
//        send.join()
        log.info(send.toString())
        return send.toString()
    }

    @RequestMapping("/loadWalletxx")
    fun loadWalletxx(): Credentials? {
        val walleFilePath = "./UTC--2018-07-19T03-46-04.460000000Z--6194ab1ec4a1e67df89537ed913fc014e538b303--xx.json"
        val passWord = "123456"
        val credentials = WalletUtils.loadCredentials(passWord, walleFilePath)
        val address = credentials.getAddress()
        val publicKey = credentials.getEcKeyPair().getPublicKey()
        val privateKey = credentials.getEcKeyPair().getPrivateKey()
        log.info("address=" + address)
        log.info("public key=" + publicKey)
        log.info("private key=" + privateKey)
        return credentials
    }


    @RequestMapping("/loadWalletjyj")
    fun loadWalletjyj(): Credentials? {
        val walleFilePath = "./UTC--2018-07-19T03-46-04.460000000Z--6194ab1ec4a1e67df89537ed913fc014e538b303--jyj.json"
        val passWord = "123456"
        val credentials = WalletUtils.loadCredentials(passWord, walleFilePath)
        val address = credentials.getAddress()
        val publicKey = credentials.getEcKeyPair().getPublicKey()
        val privateKey = credentials.getEcKeyPair().getPrivateKey()
        log.info("address=" + address)
        log.info("public key=" + publicKey)
        log.info("private key=" + privateKey)
        return credentials
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

    /**
     * 无信息转账
     */
    @RequestMapping("/transto")
    fun transto() {
        val address_to = "0x41F1dcbC0794BAD5e94c6881E7c04e4F98908a87"
        val send = Transfer.sendFunds(web3j, loadWalletxx(), address_to, BigDecimal.ONE, Convert.Unit.FINNEY)
                .send()

        log.info("Transaction complete:")
        log.info("trans hash=" + send.transactionHash)
        log.info("from :" + send.from)
        log.info("to:" + send.to)
        log.info("gas used=" + send.gasUsed)
        log.info("status: " + send.status)

    }

    /**
     * 含data转账
     */
    @RequestMapping("/transto2")
    fun testTransto() {
        val ethGetTransactionCount = web3j.ethGetTransactionCount(
                "0x6194ab1ec4a1e67df89537ed913fc014e538b303", DefaultBlockParameterName.LATEST).sendAsync().get()
        val nonce = ethGetTransactionCount.transactionCount

        val rawTransaction = RawTransaction.createTransaction(nonce, Contract.GAS_PRICE, Contract.GAS_LIMIT,
                "0x06Eb0870C6957A2bD3b7FDEdb87d919eEE32BD2c",
                10000.toBigInteger(),
                "0x7b2074696d653a20313531383933313435323537372c20747970653a2027696e666f272c206d73673a202757656233205465737421212127207d20")
        val signedMessage = TransactionEncoder.signMessage(rawTransaction, loadWalletxx())
        val hexValue = Numeric.toHexString(signedMessage)
        val send = web3j.ethSendRawTransaction(hexValue).send()
        log.info(send.transactionHash)

    }

    @RequestMapping("/deploy")
    fun deploy() {
        val deploy = Certificate_sol_Certificate.deploy(web3j, loadWalletjyj(), Contract.GAS_PRICE, Contract.GAS_LIMIT).send()

        log.info(deploy.contractAddress)
        log.info(deploy.contractBinary)
    }


    @RequestMapping("/contract")
    fun contract() {
        val contract = Certificate_sol_Certificate.load(contractAddress, web3j, loadWalletjyj(), Contract.GAS_PRICE, Contract.GAS_LIMIT)
        log.info { contract.isValid }
//        val set = contract.set(Uint256(100)).send()
//        val set = contract.mint(Address("0x6194Ab1ec4A1E67dF89537ed913fc014e538b303"), Uint256(20010)).send()
        val get = contract.record(Uint256(20010)).send()
        log.info { get }
    }

    @RequestMapping("/transactions")
    fun getTransactionsByAddress() {
        val response = etherscanService.getTransactionsByAddress("0x6194Ab1ec4A1E67dF89537ed913fc014e538b303").execute()
        log.info { response.body() }
    }

}