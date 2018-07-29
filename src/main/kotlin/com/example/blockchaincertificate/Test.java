package com.example.blockchaincertificate;

import com.example.blockchaincertificate.utils.HexUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.core.Request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by guo on 2018/7/19.
 * desc:
 */

@Slf4j
public class Test {
    @Autowired
    private Admin admin;

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(HexUtils.strToHex("{ time: 1518931452577, type: 'info', msg: 'Web3 Test!!!' } "));
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
