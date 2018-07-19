package com.example.blockchaincertificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.core.Request;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by guo on 2018/7/19.
 * desc:
 */

@Slf4j
public class Test {
    @Autowired
    private Admin admin;

    public static void main(String[] args) {
        new Test().test();
    }

    private void test() {
        Request<?, NewAccountIdentifier> request = admin.personalNewAccount("123456");
        try {
            NewAccountIdentifier result = request.send();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void transto() throws Exception {

    }


}
