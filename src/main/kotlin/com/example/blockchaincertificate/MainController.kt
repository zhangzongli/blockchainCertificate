package com.example.blockchaincertificate;

import com.example.blockchaincertificate.mapper.StudentMapper
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.admin.Admin
import tk.mybatis.mapper.entity.Example
import java.io.File

/**
 * Created by guo on 2018/7/26.
 * desc:
 */

@RestController("/api")
public class MainController {
    private val log = KotlinLogging.logger {}
    @Autowired
    private lateinit var web3j: Web3j
    @Autowired
    private lateinit var admin: Admin
    @Autowired
    private lateinit var etherscanService: EtherscanService

    private lateinit var studentMapper: StudentMapper

    private val contractAddress = "0x5af16f99b0a0c5d4cd5b26d50dfcc932993021d1"

    private val jyjAddress = "0x6194ab1ec4a1e67df89537ed913fc014e538b303"


    @RequestMapping("/create_wallet")
    public fun createWallet(student: Student) {
        student.status = 0
        student.walletAddress = account()
        studentMapper.insert(student)
    }


    @RequestMapping("/getList")
    public fun getList(status: Int): MutableList<Student>? {
        val example = Example(Student::class.java)
        example.createCriteria().andEqualTo("status",status)
        return studentMapper.selectByExample(example)
    }


    @RequestMapping("/create")
    public fun createCertificate(number: Long) {
        val student = studentMapper.selectByPrimaryKey(number)

    }


    fun account(): String {
        val walletFilePath0 = "./"
        //钱包文件保持路径，请替换位自己的某文件夹路径
        val walletFileName0 = WalletUtils.generateNewWalletFile("123456", File(walletFilePath0), false)
        return walletFileName0
    }
}
