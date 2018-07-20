package com.example.blockchaincertificate;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Class for performing Ether transactions on the Ethereum blockchain.
 */
public class MyTransfer extends ManagedTransaction {

    // This is the cost to send Ether between parties
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(21000);

    public MyTransfer(Web3j web3j, TransactionManager transactionManager) {
        super(web3j, transactionManager);
    }

    /**
     * Given the duration required to execute a transaction, asyncronous execution is strongly
     * recommended via {@link MyTransfer#sendFunds(String, BigDecimal, Convert.Unit)}.
     *
     * @param toAddress destination address
     * @param value     amount to send
     * @param unit      of specified send
     * @return {@link Optional} containing our transaction receipt
     * @throws ExecutionException   if the computation threw an
     *                              exception
     * @throws InterruptedException if the current thread was interrupted
     *                              while waiting
     * @throws TransactionException if the transaction was not mined while waiting
     */
    private TransactionReceipt send(String toAddress, BigDecimal value, Convert.Unit unit, String data)
            throws IOException, InterruptedException,
            TransactionException {

        BigInteger gasPrice = requestCurrentGasPrice();
        return send(toAddress, value, unit, gasPrice, GAS_LIMIT, data);
    }

    private TransactionReceipt send(
            String toAddress, BigDecimal value, Convert.Unit unit, BigInteger gasPrice,
            BigInteger gasLimit, String data) throws IOException, InterruptedException,
            TransactionException {

        BigDecimal weiValue = Convert.toWei(value, unit);
        if (!Numeric.isIntegerValue(weiValue)) {
            throw new UnsupportedOperationException(
                    "Non decimal Wei value provided: " + value + " " + unit.toString()
                            + " = " + weiValue + " Wei");
        }

        String resolvedAddress = ensResolver.resolve(toAddress);
        if (data == null)
            data = "";
        return send(resolvedAddress, data, weiValue.toBigIntegerExact(), gasPrice, gasLimit);
    }

    public static RemoteCall<TransactionReceipt> sendFunds(
            Web3j web3j, Credentials credentials,
            String toAddress, BigDecimal value, Convert.Unit unit, String data) throws InterruptedException,
            IOException, TransactionException {

        TransactionManager transactionManager = new RawTransactionManager(web3j, credentials);

        return new RemoteCall<>(() ->
                new MyTransfer(web3j, transactionManager).send(toAddress, value, unit, data));
    }

    /**
     * Execute the provided function as a transaction asynchronously. This is intended for one-off
     * fund transfers. For multiple, create an instance.
     *
     * @param toAddress destination address
     * @param value     amount to send
     * @param unit      of specified send
     * @return {@link RemoteCall} containing executing transaction
     */
    public RemoteCall<TransactionReceipt> sendFunds(
            String toAddress, BigDecimal value, Convert.Unit unit) {
        return new RemoteCall<>(() -> send(toAddress, value, unit, ""));
    }

    public RemoteCall<TransactionReceipt> sendFunds(
            String toAddress, BigDecimal value, Convert.Unit unit, BigInteger gasPrice,
            BigInteger gasLimit, String data) {
        return new RemoteCall<>(() -> send(toAddress, value, unit, gasPrice, gasLimit, data));
    }
}
