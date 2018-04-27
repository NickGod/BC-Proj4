package lib;

import com.sun.deploy.util.StringUtils;
import com.sun.tools.javac.util.ArrayUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

/**
 * Block Class, the element to compose a Blockchain.
 */
public class Block implements Serializable{

    private String hash;

    private String previousHash;

    private String data;

    private long timestamp;

    private int difficulty;

    private long nonce;

    private BigInteger target;

    private POW pow;

    public Block() {}

    public Block( String previousHash, String data,
                 long timestamp, int difficulty) {
        this.previousHash = previousHash;
        this.data = data;
        this.timestamp = timestamp;
        this.difficulty = difficulty;
        this.nonce = 0;
        this.target = computeTarget(difficulty);
//        this.hash = this.computeHash(data, previousHash, timestamp, nonce, difficulty);
    }



    private BigInteger computeTarget(int difficulty) {
        return  BigInteger.valueOf(1).shiftLeft((256 - difficulty));

    }

//    private String computeHash(String data, String previousHash, long timestamp, long nonce, long difficulty){
//        String preHash = previousHash;
//        String curData = data;
//        if( preHash == null || preHash.equals(" ")  || preHash.length() == 0) {
//            byte[] preHashByte = new byte[32];
//            new Random().nextBytes(preHashByte);
//            preHash = new String (preHashByte);
//        }
//
//        if( curData == null || curData.equals("") || curData.length() == 0) {
//            byte[] dataByte = new byte[32];
//            new Random().nextBytes(dataByte);
//            curData = new String (dataByte);
//        }
//
//        byte[] temp = (preHash + curData + Long.toString(timestamp), ).getBytes();
//        return  new String(DigestUtils.sha256(temp));
//    }

    private String prepareData(long nonce) {
        String preHash = this.previousHash;
        String curData = this.data;
        if( preHash == null || preHash.equals(" ")  || preHash.length() == 0) {
            byte[] preHashByte = new byte[32];
            new Random().nextBytes(preHashByte);
            preHash = new String (preHashByte);
        }

        if( curData == null || curData.equals("") || curData.length() == 0) {
            byte[] dataByte = new byte[32];
            new Random().nextBytes(dataByte);
            curData = new String (dataByte);
        }

        return preHash + curData + Long.toString(this.timestamp) + difficulty + nonce;
    }

    public void computePOW() {
        long nonce = 0;

        System.out.println("Start mine: " + nonce + "----- ---- ---- ----- -----" );

        long index = 0;
        String curHash = "";
        while(index < Long.MAX_VALUE) {
            byte[] curData = prepareData(nonce).getBytes();
            curHash = DigestUtils.sha256Hex(curData);
            if(new BigInteger(curHash, 16).compareTo(this.target) == -1) {
                System.out.println("Found Hash: " + curHash + "--- ---- --- ----");
                System.out.println("Found Nonce: " + nonce + "--- ---- --- ----");
                break;
            }else {
                index++;
            }
        }
        this.pow = new POW(nonce,curHash);
        this.setHash(curHash);
        this.setNonce(nonce);
    }

    public POW getPow() {
        return this.pow;
    }

    public long getNonce() {

        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getData() {
        return data;
    }

    public long getTimestamp() {
        return timestamp;
    }


    public static Block fromString(String s){
        return null;
    }

}