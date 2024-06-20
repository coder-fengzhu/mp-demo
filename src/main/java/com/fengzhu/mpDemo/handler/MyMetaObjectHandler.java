package com.fengzhu.mpDemo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充...");
        long now = Instant.now().toEpochMilli();
        this.strictInsertFill(metaObject, "createTime", Long.class, now);
        this.strictInsertFill(metaObject, "updateTime", Long.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新填充...");
        long now = Instant.now().toEpochMilli();
        this.strictUpdateFill(metaObject, "updateTime", Long.class, now);
    }
}