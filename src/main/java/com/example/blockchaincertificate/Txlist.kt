package com.example.blockchaincertificate

/**
 * Created by guo on 2018/7/25.
 * desc:
 */

data class Txlist(
    val status: String,
    val message: String,
    val result: List<Result>
)

data class Result(
    val blockNumber: String,
    val timeStamp: String,
    val hash: String,
    val nonce: String,
    val blockHash: String,
    val transactionIndex: String,
    val from: String,
    val to: String,
    val value: String,
    val gas: String,
    val gasPrice: String,
    val isError: String,
    val txreceipt_status: String,
    val input: String,
    val contractAddress: String,
    val cumulativeGasUsed: String,
    val gasUsed: String,
    val confirmations: String
)