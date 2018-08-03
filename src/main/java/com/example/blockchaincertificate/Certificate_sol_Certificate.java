package com.example.blockchaincertificate;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class Certificate_sol_Certificate extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060008054600160a060020a031916331790556101be806100326000396000f3006080604052600436106100565763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416632c16cd8a811461005b57806340c10f191461009c57806352e1e277146100cf575b600080fd5b34801561006757600080fd5b506100736004356100e4565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b3480156100a857600080fd5b506100cd73ffffffffffffffffffffffffffffffffffffffff6004351660243561010c565b005b3480156100db57600080fd5b50610073610176565b60016020526000908152604090205473ffffffffffffffffffffffffffffffffffffffff1681565b60005473ffffffffffffffffffffffffffffffffffffffff16331461013057610172565b6000818152600160205260409020805473ffffffffffffffffffffffffffffffffffffffff191673ffffffffffffffffffffffffffffffffffffffff84161790555b5050565b60005473ffffffffffffffffffffffffffffffffffffffff16815600a165627a7a72305820d7d802d1e2850626908720f8ad96ed2e70e513c222b59ab64fc600233ef3ae230029";

    public static final String FUNC_RECORD = "record";

    public static final String FUNC_MINT = "mint";

    public static final String FUNC_JIAOYUJU = "jiaoyuju";

    protected Certificate_sol_Certificate(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Certificate_sol_Certificate(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<Address> record(Uint256 param0) {
        final Function function = new Function(FUNC_RECORD, 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> mint(Address tx, Uint256 number) {
        final Function function = new Function(
                FUNC_MINT, 
                Arrays.<Type>asList(tx, number), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Address> jiaoyuju() {
        final Function function = new Function(FUNC_JIAOYUJU, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public static RemoteCall<Certificate_sol_Certificate> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Certificate_sol_Certificate.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Certificate_sol_Certificate> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Certificate_sol_Certificate.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Certificate_sol_Certificate load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Certificate_sol_Certificate(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Certificate_sol_Certificate load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Certificate_sol_Certificate(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
