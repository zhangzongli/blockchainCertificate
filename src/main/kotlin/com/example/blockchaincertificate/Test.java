package com.example.blockchaincertificate;

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
//        System.out.println(HexUtils.strToHex("{ time: 1518931452577, type: 'info', msg: 'Web3 Test!!!' } "));

        System.out.println(hexStringToString("0x7b226e756d626572223a352c226e616d65223a227a68616e6773616e222c22736578223a302c226269727468223a22323031352d30312d3031222c2269645f63617264223a22343130313031313939393031303131313232222c227363686f6f6c223a22e4b88ae6b5b7e4baa4e5a4a7222c22636f6c6c656765223a22e8aea1e7ae97e69cbae5ada6e999a2222c2270726f66657373696f6e223a22e8aea1e7ae97e69cbae7a791e5ada6e4b88ee68a80e69caf222c22706572696f64223a2232303131222c2263726561746554696d65223a224a756c2032382c203230313820383a35303a323120414d222c22737461747573223a302c226a796a5f737461747573223a312c2277616c6c657441646472657373223a225554432d2d323031382d30372d32385430382d35302d32312e3136323030303030305a2d2d643862323336613138643237343336336337386332393763623233646133336431313031356539302e6a736f6e222c227478223a22307831386231623732373266333937303933616362383062353439373763303137326633343066393865353933323136323863353830633666363062306632313439222c226973526f6c6c6261636b223a66616c73657d"));
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

    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

}
