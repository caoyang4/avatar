package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.web.dal.entity.CasUser;
import com.sankuai.avatar.web.util.UserUtils;
import com.sankuai.microplat.logrecord.sdk.beans.Operator;
import com.sankuai.microplat.logrecord.sdk.service.IOperatorGetService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-12-30 15:50
 */
@Service
public class OperatorGetServiceImpl implements IOperatorGetService {

    @Override
    public Operator getUser() {
        Operator operator = new Operator();
        CasUser casUser = UserUtils.getCurrentCasUser();
        if (Objects.nonNull(casUser) && StringUtils.isNotEmpty(casUser.getLoginName())) {
            operator.setOperatorId(casUser.getLoginName());
        } else {
            operator.setOperatorId("avatar");
        }
        return operator;
    }
}
