package com.example.blockchaincertificate;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


/**
 * Created by guo on 2018/7/26.
 * desc:
 */

@Table(name = "student")
@Data
public class Student {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long number;
    private String name;
    private int sex;
    private String birth;
    private Long idCard;
    private String school;
    private String college;
    private String period;
    private Long createTime;
    private int status; //0未审核 1审核通过 2审核拒
    private String walletAddress;

}

