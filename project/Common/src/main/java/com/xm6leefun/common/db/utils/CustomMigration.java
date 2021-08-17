package com.xm6leefun.common.db.utils;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * @Description: 迁移操作的迁移类
 * @Author: hhh
 * @CreateDate: 2021/3/25 15:31
 */
public class CustomMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        //数据库升级操作
        RealmSchema schema=realm.getSchema();
        if(oldVersion == 1 && newVersion ==2){

        }
    }
}
