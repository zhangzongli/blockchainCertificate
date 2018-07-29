package com.example.blockchaincertificate;

import com.example.blockchaincertificate.mapper.StudentMapper
import com.example.blockchaincertificate.utils.HexUtils
import com.google.gson.Gson
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.admin.Admin
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.tx.Contract
import org.web3j.utils.Numeric
import java.io.File
import java.math.BigInteger

/**
 * Created by guo on 2018/7/26.
 * desc:
 */

@RestController
public class MainController {
    private val log = KotlinLogging.logger {}
    @Autowired
    private lateinit var web3j: Web3j
    @Autowired
    private lateinit var admin: Admin
    @Autowired
    private lateinit var etherscanService: EtherscanService

    @Autowired
    private lateinit var studentMapper: StudentMapper

    private val contractAddress = "0x5af16f99b0a0c5d4cd5b26d50dfcc932993021d1"

    private val jyjAddress = "0x6194ab1ec4a1e67df89537ed913fc014e538b303"

    private val xxAddress = "0x4463be57a8ec36540e4a9219655d6d290e7f22fc"


    @RequestMapping("/create_wallet")
    public fun createWallet(student: Student) {
        student.status = 0
        student.jyj_status = 0
        student.walletAddress = account()
        studentMapper.insert(student)
    }


    @RequestMapping("/getList")
    public fun getList(status: Int): List<Student> {
//        val example = Example(Student::class.java)
//        example.createCriteria().andEqualTo("status",status)
        return studentMapper.findByStatus(status)
    }

    /**
     * 通过审批 向钱包内转账 并提交至教育局
     */
    @RequestMapping("/xx_agree")
    public fun createCertificate(number: Long): String {
        val student = studentMapper.selectByPrimaryKey(number)
        //转账写入区块
        val tx = transto(Gson().toJson(student),
                student.walletAddress.split("--")[2].replace(".json", ""))
        student.status = 1
        student.tx = tx
        studentMapper.updateByPrimaryKey(student)
        return "success"
    }

    /**
     * 通过教育局审批 写入智能合约
     * */
    @RequestMapping("/jyj_agree")
    public fun writeContract(number: Long): String {
        val student = studentMapper.selectByPrimaryKey(number)
        //写入智能合约
        contract(student.number, student.walletAddress.split("--")[2].replace(".json", ""))
        student.jyj_status = 1
        studentMapper.updateByPrimaryKey(student)
        return "success"
    }


    /**
     * 召回 并提交至教育局
     */
    @RequestMapping("/xx_rollback")
    public fun xxRollback(number: Long): String {
        val student = studentMapper.selectByPrimaryKey(number)
        student.isRollback = true
        //转账写入区块
        val tx = transto(Gson().toJson(student),
                student.walletAddress.split("--")[2].replace(".json", ""))
        student.status = -1
        student.tx = tx
        studentMapper.updateByPrimaryKey(student)
        return "success"
    }


    /**
     * 通过学校提出的召回审批 写入智能合约
     * */
    @RequestMapping("/jyj_rollback")
    public fun jyjRollback(number: Long): String {
        val student = studentMapper.selectByPrimaryKey(number)
        //写入智能合约
        contract(student.number, student.walletAddress.split("--")[2].replace(".json", ""))
        student.jyj_status = 1
        studentMapper.updateByPrimaryKey(student)
        return "success"
    }

    /**
     * 获取学生地址
     */
    @RequestMapping("/getStudentAddress")
    public fun getStudentAddress(number: Long): String? {
        val contract = Certificate_sol_Certificate.load(contractAddress, web3j, loadWalletjyj(), Contract.GAS_PRICE, Contract.GAS_LIMIT)
        log.info { contract.isValid }
        val get = contract.record(Uint256(number)).send()
        log.info { get }
        return get.value
    }

    /**
     * 获取学位信息
     */
    @RequestMapping("/getTransaction")
    fun getTransactionsByAddress(address: String): Result? {
        val response = etherscanService.getTransactionsByAddress(address).execute()
        log.info { response.body() }
        return response.body()?.result?.last()
    }


    private fun account(): String {
        val walletFilePath0 = "./"
        //钱包文件保持路径，请替换位自己的某文件夹路径
        val walletFileName0 = WalletUtils.generateNewWalletFile("123456", File(walletFilePath0), false)
        return walletFileName0
    }

    private fun transto(data: String, studentAddress: String): String? {
        val hex = HexUtils.strToHex(data)
        log.info { "hex------------>$hex" }
        val ethGetTransactionCount = web3j.ethGetTransactionCount(
                xxAddress, DefaultBlockParameterName.LATEST).sendAsync().get()
        val nonce = ethGetTransactionCount.transactionCount

        val s = "0x" + studentAddress
        log.info { "studentAddress---------->$s" }
        val rawTransaction = RawTransaction.createTransaction(nonce, Contract.GAS_PRICE, Contract.GAS_LIMIT,
                s,
                BigInteger.valueOf(10000),
                hex)
        val signedMessage = TransactionEncoder.signMessage(rawTransaction, loadWalletxx())
        val hexValue = Numeric.toHexString(signedMessage)
        val send = web3j.ethSendRawTransaction(hexValue).send()
        log.info(send.transactionHash)
        return send.transactionHash
    }


    private fun loadWalletxx(): Credentials? {
        val walleFilePath = "./UTC--2018-07-28T07-28-15.556000000Z--4463be57a8ec36540e4a9219655d6d290e7f22fc--xx.json"
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


    private fun loadWalletjyj(): Credentials? {
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

    /**
     * 写入智能合约
     */
    private fun contract(number: Long, tx: String) {
        val contract = Certificate_sol_Certificate.load(contractAddress, web3j, loadWalletjyj(), Contract.GAS_PRICE, Contract.GAS_LIMIT)
        log.info { contract.isValid }
//        val set = contract.set(Uint256(100)).send()
        val set = contract.mint(Address(tx)
                , Uint256(number)).send()
//        val get = contract.record(Uint256(20010)).send()
        log.info { set }
    }
}
