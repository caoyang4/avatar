package com.sankuai.avatar.capacity.property;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.constant.MiddleWareName;
import com.sankuai.avatar.capacity.constant.WhiteApp;
import com.sankuai.avatar.capacity.node.Node;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class AutoMigrationProperty extends AbstractProperty<Boolean>  {
    private String name = "自动迁移";

    private String description = String.format("系统判断单机故障无法自动摘除，判断容灾等级为1。 处理方法: 1.接入%s中任一种；2.如果确定服务可以自动迁移，请申请加入自动迁移白名单",
            MiddleWareName.formatMiddleWareNames(MiddleWareName.getAutoMigrationMiddleWareNameList()));

    @Override
    public Boolean execute(Node node) {
        boolean has = false;
        List<MiddleWareName> nodeMiddleWareNameList = Lists.newArrayList();
        for (MiddleWareName middleWareName:MiddleWareName.getAutoMigrationMiddleWareNameList()){
            if (node.hasMiddleWare(middleWareName)) {
                nodeMiddleWareNameList.add(middleWareName);
                has = true;
            }
        }
        if (node.isWhiteApp(WhiteApp.AUTO_MIGRATE)){
            has = true;
        }
        return has;
    }
}
