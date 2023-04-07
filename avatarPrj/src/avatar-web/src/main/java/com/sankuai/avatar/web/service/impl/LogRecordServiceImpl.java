package com.sankuai.avatar.web.service.impl;

import com.sankuai.microplat.logrecord.openapi.dto.LogRecordDTO;
import com.sankuai.microplat.logrecord.openapi.dto.LogRecordListRequest;
import com.sankuai.microplat.logrecord.openapi.dto.Page;
import com.sankuai.microplat.logrecord.openapi.exception.RpcException;
import com.sankuai.microplat.logrecord.sdk.beans.LogRecord;
import com.sankuai.microplat.logrecord.sdk.service.ILogRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-30 15:36
 */
@Slf4j
@Service
public class LogRecordServiceImpl implements ILogRecordService {

    @Override
    public void record(LogRecord logRecord) throws RpcException {
        log.info("【logRecord】log={}", logRecord);
    }

    @Override
    public void batchRecord(List<LogRecord> list) throws RpcException {
        log.info("【logRecord】log={}", list);
    }

    @Override
    public Page<LogRecordDTO> queryLog(LogRecordListRequest logRecordListRequest) throws RpcException {
        return null;
    }
}
