package com.xm6leefun.common.db.utils;

import com.xm6leefun.common.app.BaseApplication;
import com.xm6leefun.common.utils.LogUtil;
import com.xm6leefun.common.utils.OcMath;
import java.util.HashSet;
import java.util.Set;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import party.loveit.bip44forjava.core.ECKey;
import party.loveit.bip44forjava.core.Sha256Hash;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/25 15:19
 */
public class RealmUtils {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    static RealmConfiguration realmConfig = null;
    protected static Set<RealmConfiguration> mConfigsList = new HashSet<RealmConfiguration>();
    private static int version_wallet_oc = 1;  // 数据库版本号

    public static int getVersion_wallet_oc() {
        return version_wallet_oc;
    }

    public static void setVersion_wallet_oc(int version_wallet_oc) {
        RealmUtils.version_wallet_oc = version_wallet_oc;
    }
    public static void setInitWalletOCRealm() {
        //此处根据业务调用初始化，避免出现默认库bug
        if (Realm.getDefaultConfiguration() == null)
            Realm.init(BaseApplication.getAppContext());
        if (!Realm.getDefaultConfiguration().getRealmFileName().equals("poketok")) {
            LogUtil.e("=============realmDBName  !equals======setWalletOCRealm=====");
            setWalletOCRealm();
        }
    }

    private static void setWalletOCRealm() {
        ECKey ecKey = ECKey.fromPrivate(OcMath.toBigInt("0abc4301"));
        byte[] sha256 = Sha256Hash.hash(ecKey.getPubKey());
        LogUtil.e("sha256---------"+OcMath.toHexStringNoPrefix(OcMath.toHexStringNoPrefix(sha256).getBytes()));
        realmConfig = new RealmConfiguration.Builder()
                .migration(new CustomMigration())
//                .encryptionKey(OcMath.toHexStringNoPrefix(sha256).getBytes())  //设置数据库密码
                .name("poketok.realm")
                .schemaVersion(getVersion_wallet_oc())
                .build();
        mConfigsList.add(realmConfig);
        Realm.setDefaultConfiguration(realmConfig);
    }

    public static Realm getRealm() {
        return Realm.getInstance(getRealmConfiguration());
    }

    public static RealmConfiguration getRealmConfiguration() {
        if (realmConfig == null) {
            setWalletOCRealm();
        }
        return realmConfig;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
