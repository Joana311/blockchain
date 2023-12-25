package org.prog3.project.Transaction;

import org.prog3.project.PikkwolfChain;
import org.prog3.project.StringUtil;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {
    public String transactionId; // this is also the hash of the transaction.
    public PublicKey sender;     // senders address/public key.
    public PublicKey reciepient; // Recipients address/public key.
    public float value;
    public byte[] signature;     // this is to prevent anybody else from spending funds in our wallet.
    public ArrayList<TransactionInput> inputs;
    public ArrayList<TransactionOutput> outputs;
    private static int sequence = 0; // a rough count of how many transactions have been generated.

    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.inputs = inputs;
        outputs = new ArrayList<>();
    }

    private String calulateHash() {
        // This Calculates the transaction hash (which will be used as its ID)
        sequence++; // increase the sequence to avoid 2 identical transactions having the same hash
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(reciepient) +
                        value +
                        sequence);
    }

    public void generateSignature(PrivateKey privateKey) {
        // Signs all the data we don't wish to be tampered with
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + value;
        signature = StringUtil.applyECDSASig(privateKey, data);
    }

    public boolean verifiySignature() {
        // Verifies the data we signed hasn't been tampered with
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + value;
        return !StringUtil.verifyECDSASig(sender, data, signature);
    }

    public boolean processTransaction() {
        // Returns true if new transaction could be created
        if (verifiySignature()) {
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }
        // gather transaction inputs (Make sure they are unspent):
        for (TransactionInput i : inputs) {
            i.UTXO = PikkwolfChain.UTXOs.get(i.transactionOutputId);
        }
        // check if transaction is valid:
        if (getInputsValue() < PikkwolfChain.minimumTransaction) {
            System.out.println("#Transaction Inputs to small: " + getInputsValue());
            return false;
        }
        // generate transaction outputs:
        float leftOver = getInputsValue() - value; // get value of inputs then the leftover change:
        transactionId = calulateHash();
        outputs.add(new TransactionOutput(this.reciepient, value, transactionId)); // send value to recipient
        outputs.add(new TransactionOutput(this.sender, leftOver, transactionId)); // send the left over 'change' back to sender
        // add outputs to Unspent list
        for (TransactionOutput o : outputs) {
            PikkwolfChain.UTXOs.put(o.id, o);
        }
        // remove transaction inputs from UTXO lists as spent:
        for (TransactionInput i : inputs) {
            if (i.UTXO == null) continue; // if Transaction can't be found skip it
            PikkwolfChain.UTXOs.remove(i.UTXO.id);
        }
        return true;
    }

    public float getInputsValue() {
        // returns sum of inputs(UTXOs) values
        float total = 0;
        for (TransactionInput i : inputs) {
            if (i.UTXO == null) continue; // if Transaction can't be found skip it
            total += i.UTXO.value;
        }
        return total;
    }

    public float getOutputsValue() {
        // returns sum of outputs:
        float total = 0;
        for (TransactionOutput o : outputs) {
            total += o.value;
        }
        return total;
    }

}
