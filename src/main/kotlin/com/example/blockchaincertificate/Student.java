package com.example.blockchaincertificate;

import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * Created by guo on 2018/7/26.
 * desc:
 */

@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long number;
    private String name;
    private Integer sex;
    private String birth;
    private Long idCard;
    private String school;
    private String college;
    private String profession;
    private String period;
    private Timestamp createTime;
    private Integer status; //0未审核 1审核通过 2审核拒jue -1 召回
    private Integer jyj_status;//0未审核 1审核通过 2审核拒jue
    private String walletAddress;
    private String tx;
    @Transient
    private boolean isRollback;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Long getIdCard() {
        return idCard;
    }

    public void setIdCard(Long idCard) {
        this.idCard = idCard;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getJyj_status() {
        return jyj_status;
    }

    public void setJyj_status(Integer jyj_status) {
        this.jyj_status = jyj_status;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public boolean isRollback() {
        return isRollback;
    }

    public void setRollback(boolean rollback) {
        isRollback = rollback;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}

