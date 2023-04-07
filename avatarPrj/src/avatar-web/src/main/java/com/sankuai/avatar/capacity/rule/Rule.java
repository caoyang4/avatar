package com.sankuai.avatar.capacity.rule;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.calculator.ConditionResult;
import com.sankuai.avatar.capacity.calculator.RuleResult;
import com.sankuai.avatar.capacity.constant.CompareType;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.Host;
import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.property.Property;
import com.sankuai.avatar.capacity.property.RegionBjHostIDCLengthProperty;
import com.sankuai.avatar.capacity.property.RegionShHostIDCLengthProperty;
import com.sankuai.avatar.capacity.util.HulkUtils;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
@Getter
public class Rule {
    private List<Condition> conditionList;
    /**
     * 匹配原因
     */
    private String reason;

    public Rule(Condition ...conditionList) {
        this.conditionList = Arrays.asList(conditionList);
    }

    public Rule(String reason, Condition... conditionList) {
        this.conditionList = Arrays.asList(conditionList);
        this.reason = reason;
    }

    public String getReason(AppKeyNode node){
        for (Condition condition : conditionList) {
            Class clz = condition.getClazz();
            if (clz.isAssignableFrom(RegionBjHostIDCLengthProperty.class)
            || clz.isAssignableFrom(RegionShHostIDCLengthProperty.class)) {
                StringBuilder builder = new StringBuilder(reason);
                builder.append("，机器分布为：");
                Map<String, List<Host>> idcHostMap = node.getIdcHostMap();
                for (String idc : idcHostMap.keySet()) {
                    builder.append(String.format("[%s: %s台] ", idc, idcHostMap.get(idc).size()));
                }
                return builder.toString();
            }
        }
        return reason;
    }

    private ConditionResult hitCondition(Node node, Condition condition) throws IllegalAccessException, InstantiationException {
        ConditionResult conditionResult = new ConditionResult();
        String description = "";
        boolean hit;
        Property property = (Property) condition.getClazz().newInstance();
        Object result = property.execute(node);
        if (condition.getCompareType() == null || condition.getCompareType() == CompareType.EQUALS) {
            hit = result.equals(condition.getExpect());
        } else if (condition.getCompareType() == CompareType.GREATER_THAN) {
            assert result instanceof Number;
            assert condition.getExpect() instanceof Number;
            hit = ((Number) result).doubleValue() > ((Number) condition.getExpect()).doubleValue();
        } else if (condition.getCompareType() == CompareType.LESS_THAN) {
            assert result instanceof Number;
            assert condition.getExpect() instanceof Number;
            hit = ((Number) result).doubleValue() < ((Number) condition.getExpect()).doubleValue();
        } else if (condition.getCompareType() == CompareType.CONTAINS) {
            try {
                hit = (Boolean) result.getClass().getMethod("contains", Object.class).invoke(result, condition.getExpect());
            } catch (NoSuchMethodException | InvocationTargetException e) {
                hit = false;
                e.printStackTrace();
            }
        } else {
            hit = false;
        }
        conditionResult.setProperty(property);
        conditionResult.setHit(hit);
        conditionResult.setCondition(condition);
        conditionResult.setSuggestion(property.getSuggestion());
        String desc = property.getDescription();
        conditionResult.setDescription(StringUtils.isNotBlank(desc) ? desc : formatDesc(property.getName(), condition.getCompareType(), condition, (AppKeyNode) node));
        return conditionResult;
    }

    public RuleResult hit(Node node) throws Exception {
        RuleResult ruleResult = new RuleResult();
        List<ConditionResult> conditionResultList = Lists.newArrayList();
        boolean hit = true;
        for (Condition condition : getConditionList()) {
            ConditionResult conditionResult = hitCondition(node, condition);
            conditionResultList.add(conditionResult);
            if (conditionResult.getHit().equals(Boolean.TRUE)) {
                continue;
            }
            hit = false;
            break;
        }
        ruleResult.setHit(hit);
        ruleResult.setConditionList(conditionResultList);
        return ruleResult;
    }

    private String formatDesc(String desc, CompareType compareType, Condition condition, AppKeyNode node){
        String description = String.format("%s %s %s", desc, compareType.getName(), condition.getExpect());
        try {
            boolean flag = (boolean) condition.getExpect();
            switch (desc){
                case RuleConstants.ELASTIC_RULE:
                    return flag ? "该服务已接入弹性伸缩，且满足3级容灾，判定容灾等级为5" : "该服务未接入弹性伸缩：" + HulkUtils.getNoElasticMsg(node.getAppkey(),node.getSetName().getSetName())
                            ;
                case RuleConstants.NEST_RULE:
                    return flag ? "该服务已接入Nest，判定容灾等级为5" : "该服务未接入Nest";
                case RuleConstants.PAAS_RULE:
                    return flag ? "该服务属于paas服务，avatar不予计算容灾等级" : "该服务不属于paas服务";
                case RuleConstants.SINGLE_RESTART_RULE:
                    return flag ? "该服务属于可单机重启" : "该服务属于不可单机重启，判定容灾等级为0";
                default:
                    return description;
            }
        } catch (Exception e) {
        }
        return description;
    }
}
