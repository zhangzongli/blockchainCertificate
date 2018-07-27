package com.example.blockchaincertificate

import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

/**
 * Created by guo on 2018/7/26.
 * desc:
 */

@Table(name = "student")
data class Student(
        @Id
        @GeneratedValue(generator = "JDBC")
        val number: Long,
        var name: String,
        var sex:Int,
        var birth:String,
        var idCard:Long,
        var school:String,
        var college:String,
        var period:String,
        var createTime:Long,
        var status:Int, //0未审核 1审核通过 2审核拒绝
        var walletAddress:String
)