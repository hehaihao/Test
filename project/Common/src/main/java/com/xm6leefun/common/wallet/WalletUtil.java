package com.xm6leefun.common.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.xm6leefun.common.utils.LogUtil;
import com.xm6leefun.common.utils.OcMath;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.wallet.KeysInfo;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.List;

import party.loveit.bip44forjava.core.ECKey;
import party.loveit.bip44forjava.core.Sha256Hash;
import party.loveit.bip44forjava.utils.Bip44Utils;

/**
 * wdh
 * OC主网钱包工具类
 */
public class WalletUtil {
    public static final String[] WORDS_PATH_LIST=new String[]{"m/44'/65535'/0'/0/0","m/44'/0'/0'/0/0"};

    private static  String KEY_WORDS_PATH="m/44'/65535'/0'/0/0";

    public static void setKeyWords(String wordsPath) {
        KEY_WORDS_PATH = wordsPath;
    }
    public static String getKeyWords() {
        return KEY_WORDS_PATH ;
    }


    public static String signs(byte[] C, String privateKey) throws NoSuchAlgorithmException {
        // 签名
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(256, new SecureRandom(OcMath.hexStringToByteArray(privateKey)));
        KeyPair mKeyPair = keyPairGenerator.genKeyPair();

        LogUtil.e("++++++++++++++" + OcMath.toHexStringNoPrefix(mKeyPair.getPublic().getEncoded()));
        byte[] sign = null;
        Signature signature = null;
        try {
            signature = Signature.getInstance("NONEwithECDSA");
            signature.initSign(mKeyPair.getPrivate());
            signature.update(C);
            sign = signature.sign();

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return OcMath.toHexStringNoPrefix(sign);
    }

    /**
     * 获取整交易文本的hash
     *
     * @param tradingText
     * @return
     */
    public static String getTradingTxid(String tradingText) {
        if (StrUtils.isEmpty(tradingText))
            tradingText = "{\n" +
                    "    \"privateKey\": \"8f2ef64a9f835dbfe6280d697895ee148683a38ee91dbd8ddd01e6fc4863d800\",\n" +
                    "    \"publicKey\": \"0226080012980d70f9c3471b9b58a33edf8943008e1cc6475977b85646cdac1ab1\",\n" +
                    "    \"tx\": [\n" +
                    "        {\n" +
                    "            \"txId\": \"dfe0ddb8feda1dd012e623b9369638484f793c3fdd9acf043cba8c22e85a9e7e\",\n" +
                    "            \"n\": 0,\n" +
                    "            \"scriptSig\": \"76a914cd639ef9bb4d0cd0bfc506278a48bb9f898d037f88ac\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"to\": [\n" +
                    "        {\n" +
                    "            \"address\": \"cd639ef9bb4d0cd0bfc506278a48bb9f898d037f\",\n" +
                    "            \"value\": 5000000000,\n" +
                    "            \"type\": 1\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"ins\": \"\",\n" +
                    "    \"time\": 1573783785,\n" +
                    "    \"lockTime\": 0,\n" +
                    "    \"lockType\": 1,\n" +
                    "    \"noce\": \"f5b207dde83befeeef53e545475de07e\"\n" +
                    "}";

        byte[] sha256 = Sha256Hash.hash(OcMath.hexStringToByteArray(tradingText));
        byte[] data = Sha256Hash.hash(sha256);
        return OcMath.toHexStringNoPrefix(data);
    }

    /**
     * 获取回调消息
     *
     * @param msg
     * @return
     */
    public static byte[] getMessge(String msg) {
        byte[] sha256 = Sha256Hash.hash(OcMath.hexStringToByteArray(msg));
        byte[] data = Sha256Hash.hash(sha256);
        return data;
    }


    /**
     * 随机生成公私钥
     *
     * @return 私钥、公钥、地址
     * @throws Exception
     */
    public static KeysInfo makeRandomKeys() {
        KeysInfo mOcKeysInfo = new KeysInfo();
        try {
            SecureRandom secureRandom = new SecureRandom();
            BigInteger privateKeyCheck = BigInteger.ZERO;
            BigInteger maxKey = new BigInteger("00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364140", 16);
            byte[] privateKeyAttempt = new byte[32];
            secureRandom.nextBytes(privateKeyAttempt);
            privateKeyCheck = new BigInteger(1, privateKeyAttempt);
            while (privateKeyCheck.compareTo(BigInteger.ZERO) == 0 || privateKeyCheck.compareTo(maxKey) == 1) {
                secureRandom.nextBytes(privateKeyAttempt);
                privateKeyCheck = new BigInteger(1, privateKeyAttempt);
            }
            ECKey ecKey = ECKey.fromPrivate(privateKeyAttempt);
            mOcKeysInfo.setPrivateKey(to64PrivKeyStr(OcMath.toHexStringNoPrefix(ecKey.getPrivKey())));
            mOcKeysInfo.setPublicKey(OcMath.toHexStringNoPrefix(ecKey.getPubKey()));
            mOcKeysInfo.setAddress(getAdressByPublic(mOcKeysInfo.getPublicKey()));
            mOcKeysInfo.setCode(200);
        } catch (Exception e) {
            mOcKeysInfo.setMsg(e.toString());
            mOcKeysInfo.setCode(9999);
        }
        return mOcKeysInfo;
    }


    /**
     * 根据私钥导入钱包
     *
     * @param privateKey
     * @return 私钥、公钥、地址
     */
    public static KeysInfo importWalletByPrivateKey(String privateKey) {
        KeysInfo mOcKeysInfo = new KeysInfo();
        try {
            ECKey ecKey = ECKey.fromPrivate(OcMath.toBigInt(privateKey));
            mOcKeysInfo.setPrivateKey(to64PrivKeyStr(OcMath.toHexStringNoPrefix(ecKey.getPrivKey())));
            mOcKeysInfo.setPublicKey(OcMath.toHexStringNoPrefix(ecKey.getPubKey()));
            mOcKeysInfo.setAddress(getAdressByPublic(mOcKeysInfo.getPublicKey()));
            mOcKeysInfo.setCode(200);
        } catch (Exception e) {
            mOcKeysInfo.setMsg(e.toString());
            mOcKeysInfo.setCode(9999);
        }
        return mOcKeysInfo;
    }


    /**
     * 生成助记词
     *
     * @return 私钥、公钥、地址、助记词
     * @throws Exception
     */
    public static KeysInfo makeWords() {
        KeysInfo mKeysInfo = new KeysInfo();
        try {
            /*String[] strings = {"company", "oval", "ready", "winner", "soon", "cook", "network", "once", "shaft", "already", "cook", "agent"};
            List<String> wordsList = Arrays.asList(strings);
            mOcKeysInfo.setWords(wordsList);*/
            mKeysInfo.setWords(Bip44Utils.generateMnemonicWords());
            BigInteger ocKeys = Bip44Utils.getPathPrivateKey(mKeysInfo.getWords(), KEY_WORDS_PATH);
            ECKey ecKey = ECKey.fromPrivate(ocKeys);
            mKeysInfo.setPublicKey(OcMath.toHexStringNoPrefix(ecKey.getPubKey()));
            mKeysInfo.setPrivateKey(to64PrivKeyStr(OcMath.toHexStringNoPrefix(ecKey.getPrivKeyBytes())));
            mKeysInfo.setAddress(getAdressByPublic(mKeysInfo.getPublicKey()));
            mKeysInfo.setWords(mKeysInfo.getWords());
            mKeysInfo.setCode(200);
        } catch (Exception e) {
            LogUtil.e("createWords：" + e.toString());
            mKeysInfo.setMsg(e.toString());
            mKeysInfo.setCode(9999);
        }
        return mKeysInfo;
    }

    /**
     * 设定64位私钥
     *
     * @param hex
     * @return
     */
    private static String to64PrivKeyStr(String hex) {
        return addZeroForNum(hex, 64);
    }

    /**
     * 补位
     *
     * @param str
     * @param strLength
     * @return
     */
    private static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);// 左补0
                // sb.append(str).append("0");//右补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * 根据助记词导入钱包
     *
     * @param words
     * @return 私钥、公钥、地址、助记词
     */
    public static KeysInfo importWalletByWords(List words) {
        KeysInfo mOcKeysInfo = new KeysInfo();
        try {
            BigInteger ocKeys = Bip44Utils.getPathPrivateKey(words, KEY_WORDS_PATH);
            ECKey ecKey = ECKey.fromPrivate(ocKeys);
            mOcKeysInfo.setPublicKey(OcMath.toHexStringNoPrefix(ecKey.getPubKey()));
            mOcKeysInfo.setPrivateKey(to64PrivKeyStr(OcMath.toHexStringNoPrefix(ecKey.getPrivKeyBytes())));
            mOcKeysInfo.setAddress(getAdressByPublic(mOcKeysInfo.getPublicKey()));
            mOcKeysInfo.setWords(words);
            mOcKeysInfo.setCode(200);
        } catch (Exception e) {
            LogUtil.e("importWalletByWords：" + e.toString());
            mOcKeysInfo.setMsg(e.toString());
            mOcKeysInfo.setCode(9999);
        }
        return mOcKeysInfo;
    }


    /**
     * 生成Keystrore
     *
     * @param privateKeyByte
     * @return 获取得到明文的私钥、公钥、地址、keystore、密码
     * @throws Exception
     */
    public static KeysInfo makeKeyStore(String privateKeyByte, String passWords) {
        KeysInfo mOcKeysInfo = new KeysInfo();
        try {
            ECKey ecKey = ECKey.fromPrivate(OcMath.toBigInt(privateKeyByte));
            ECKeyPair keyPair = ECKeyPair.create(ecKey.getPrivKey());
            WalletFile walletFile = Wallet.createLight(passWords, keyPair);
            mOcKeysInfo.setPrivateKey(to64PrivKeyStr(OcMath.toHexStringNoPrefix(ecKey.getPrivKey())));
            mOcKeysInfo.setPublicKey(OcMath.toHexStringNoPrefix(ecKey.getPubKey()));
            //重定义地址算法
            walletFile.setAddress(getAdressByPublic(OcMath.toHexStringNoPrefix(ecKey.getPubKey())));
            mOcKeysInfo.setAddress(walletFile.getAddress());
            mOcKeysInfo.setKeystore(new Gson().toJson(walletFile));
            mOcKeysInfo.setPassword(passWords);
            mOcKeysInfo.setCode(200);
        } catch (Exception e) {
            LogUtil.e("enPrikey2KeyStore：" + e.toString());
            mOcKeysInfo.setMsg(e.toString());
            mOcKeysInfo.setCode(9999);
        }
        return mOcKeysInfo;
    }

    /**
     * 根据keystore导入钱包
     *
     * @param passWords
     * @param keyStoreJson
     * @return 明文的私钥、公钥、地址、keystore、密码
     * @throws CipherException
     */
    public static KeysInfo importWalletByKeyStore(String passWords, String keyStoreJson) {
        KeysInfo mOcKeysInfo = new KeysInfo();
        try {
            ObjectMapper om = new ObjectMapper();
            WalletFile walletFile = om.readValue(keyStoreJson, WalletFile.class);
            ECKeyPair keyPair = Wallet.decrypt(passWords, walletFile);
            ECKey ecKey = ECKey.fromPrivate(keyPair.getPrivateKey());

            mOcKeysInfo.setPrivateKey(to64PrivKeyStr(OcMath.toHexStringNoPrefix(ecKey.getPrivKey())));
            mOcKeysInfo.setPublicKey(OcMath.toHexStringNoPrefix(ecKey.getPubKey()));
            mOcKeysInfo.setAddress(walletFile.getAddress());
            mOcKeysInfo.setPassword(passWords);
            mOcKeysInfo.setKeystore(keyStoreJson);
            mOcKeysInfo.setCode(200);

        } catch (Exception e) {
            //密码错误等报错提示
            LogUtil.e("deKeyStore2Keys：" + e.toString());
            mOcKeysInfo.setMsg(e.toString());
            mOcKeysInfo.setCode(9999);
        }
        return mOcKeysInfo;
    }


    /**
     * 根据公钥获取地址
     *
     * @param hexPublicKey
     * @return 地址
     * @throws Exception
     */
    public static String getAdressByPublic(String hexPublicKey) {
        byte[] sha256 = Sha256Hash.hash(OcMath.hexStringToByteArray(hexPublicKey));
        RIPEMD160Digest digest = new RIPEMD160Digest();
        digest.update(sha256, 0, sha256.length);
        byte[] out = new byte[digest.getDigestSize()];
        digest.doFinal(out, 0);
        return OcMath.toHexStringNoPrefix(out).toLowerCase();
    }


    private static Web3j web3j = Web3jFactory.build(new HttpService("https://ropsten.infura.io/v3/b1a395a114ba485586c43d0fa227d443"));

    public static String signTransfer(String privateKeyByte, String keyStoreJson) throws Exception {

        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                "fromAddress", DefaultBlockParameterName.LATEST).sendAsync().get();

        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                nonce, Convert.toWei("18", Convert.Unit.GWEI).toBigInteger(),
                Convert.toWei("45000", Convert.Unit.WEI).toBigInteger(), "toAddress", new BigInteger("value"));


        WalletFile walletFile = null;
        Credentials mCredentials = Credentials.create(Wallet.decrypt("", walletFile));

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, mCredentials);
//        ECKey.ECDSASignature.decodeFromDER()
        String hexValue = OcMath.toHexString(signedMessage);
        return "";
    }

}
