package com.sankuai.avatar.web.dal.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CapacityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offset;

    protected Integer rows;

    public CapacityExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
        rows = null;
        offset = null;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getRows() {
        return this.rows;
    }

    public CapacityExample limit(Integer rows) {
        this.rows = rows;
        return this;
    }

    public CapacityExample limit(Integer offset, Integer rows) {
        this.offset = offset;
        this.rows = rows;
        return this;
    }

    public CapacityExample page(Integer page, Integer pageSize) {
        this.offset = page * pageSize;
        this.rows = pageSize;
        return this;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAppkeyIsNull() {
            addCriterion("appkey is null");
            return (Criteria) this;
        }

        public Criteria andAppkeyIsNotNull() {
            addCriterion("appkey is not null");
            return (Criteria) this;
        }

        public Criteria andAppkeyEqualTo(String value) {
            addCriterion("appkey =", value, "appkey");
            return (Criteria) this;
        }

        public Criteria andAppkeyNotEqualTo(String value) {
            addCriterion("appkey <>", value, "appkey");
            return (Criteria) this;
        }

        public Criteria andAppkeyGreaterThan(String value) {
            addCriterion("appkey >", value, "appkey");
            return (Criteria) this;
        }

        public Criteria andAppkeyGreaterThanOrEqualTo(String value) {
            addCriterion("appkey >=", value, "appkey");
            return (Criteria) this;
        }

        public Criteria andAppkeyLessThan(String value) {
            addCriterion("appkey <", value, "appkey");
            return (Criteria) this;
        }

        public Criteria andAppkeyLessThanOrEqualTo(String value) {
            addCriterion("appkey <=", value, "appkey");
            return (Criteria) this;
        }

        public Criteria andAppkeyLike(String value) {
            addCriterion("appkey like", value, "appkey");
            return (Criteria) this;
        }

        public Criteria andAppkeyNotLike(String value) {
            addCriterion("appkey not like", value, "appkey");
            return (Criteria) this;
        }

        public Criteria andAppkeyIn(List<String> values) {
            addCriterion("appkey in", values, "appkey");
            return (Criteria) this;
        }

        public Criteria andAppkeyNotIn(List<String> values) {
            addCriterion("appkey not in", values, "appkey");
            return (Criteria) this;
        }

        public Criteria andAppkeyBetween(String value1, String value2) {
            addCriterion("appkey between", value1, value2, "appkey");
            return (Criteria) this;
        }

        public Criteria andAppkeyNotBetween(String value1, String value2) {
            addCriterion("appkey not between", value1, value2, "appkey");
            return (Criteria) this;
        }

        public Criteria andSetNameIsNull() {
            addCriterion("set_name is null");
            return (Criteria) this;
        }

        public Criteria andSetNameIsNotNull() {
            addCriterion("set_name is not null");
            return (Criteria) this;
        }

        public Criteria andSetNameEqualTo(String value) {
            addCriterion("set_name =", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameNotEqualTo(String value) {
            addCriterion("set_name <>", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameGreaterThan(String value) {
            addCriterion("set_name >", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameGreaterThanOrEqualTo(String value) {
            addCriterion("set_name >=", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameLessThan(String value) {
            addCriterion("set_name <", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameLessThanOrEqualTo(String value) {
            addCriterion("set_name <=", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameLike(String value) {
            addCriterion("set_name like", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameNotLike(String value) {
            addCriterion("set_name not like", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameIn(List<String> values) {
            addCriterion("set_name in", values, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameNotIn(List<String> values) {
            addCriterion("set_name not in", values, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameBetween(String value1, String value2) {
            addCriterion("set_name between", value1, value2, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameNotBetween(String value1, String value2) {
            addCriterion("set_name not between", value1, value2, "setName");
            return (Criteria) this;
        }

        public Criteria andCapacityLevelIsNull() {
            addCriterion("capacity_level is null");
            return (Criteria) this;
        }

        public Criteria andCapacityLevelIsNotNull() {
            addCriterion("capacity_level is not null");
            return (Criteria) this;
        }

        public Criteria andCapacityLevelEqualTo(Integer value) {
            addCriterion("capacity_level =", value, "capacityLevel");
            return (Criteria) this;
        }

        public Criteria andCapacityLevelNotEqualTo(Integer value) {
            addCriterion("capacity_level <>", value, "capacityLevel");
            return (Criteria) this;
        }

        public Criteria andCapacityLevelGreaterThan(Integer value) {
            addCriterion("capacity_level >", value, "capacityLevel");
            return (Criteria) this;
        }

        public Criteria andCapacityLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("capacity_level >=", value, "capacityLevel");
            return (Criteria) this;
        }

        public Criteria andCapacityLevelLessThan(Integer value) {
            addCriterion("capacity_level <", value, "capacityLevel");
            return (Criteria) this;
        }

        public Criteria andCapacityLevelLessThanOrEqualTo(Integer value) {
            addCriterion("capacity_level <=", value, "capacityLevel");
            return (Criteria) this;
        }

        public Criteria andCapacityLevelIn(List<Integer> values) {
            addCriterion("capacity_level in", values, "capacityLevel");
            return (Criteria) this;
        }

        public Criteria andCapacityLevelNotIn(List<Integer> values) {
            addCriterion("capacity_level not in", values, "capacityLevel");
            return (Criteria) this;
        }

        public Criteria andCapacityLevelBetween(Integer value1, Integer value2) {
            addCriterion("capacity_level between", value1, value2, "capacityLevel");
            return (Criteria) this;
        }

        public Criteria andCapacityLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("capacity_level not between", value1, value2, "capacityLevel");
            return (Criteria) this;
        }

        public Criteria andStandardLevelIsNull() {
            addCriterion("standard_level is null");
            return (Criteria) this;
        }

        public Criteria andStandardLevelIsNotNull() {
            addCriterion("standard_level is not null");
            return (Criteria) this;
        }

        public Criteria andStandardLevelEqualTo(Integer value) {
            addCriterion("standard_level =", value, "standardLevel");
            return (Criteria) this;
        }

        public Criteria andStandardLevelNotEqualTo(Integer value) {
            addCriterion("standard_level <>", value, "standardLevel");
            return (Criteria) this;
        }

        public Criteria andStandardLevelGreaterThan(Integer value) {
            addCriterion("standard_level >", value, "standardLevel");
            return (Criteria) this;
        }

        public Criteria andStandardLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("standard_level >=", value, "standardLevel");
            return (Criteria) this;
        }

        public Criteria andStandardLevelLessThan(Integer value) {
            addCriterion("standard_level <", value, "standardLevel");
            return (Criteria) this;
        }

        public Criteria andStandardLevelLessThanOrEqualTo(Integer value) {
            addCriterion("standard_level <=", value, "standardLevel");
            return (Criteria) this;
        }

        public Criteria andStandardLevelIn(List<Integer> values) {
            addCriterion("standard_level in", values, "standardLevel");
            return (Criteria) this;
        }

        public Criteria andStandardLevelNotIn(List<Integer> values) {
            addCriterion("standard_level not in", values, "standardLevel");
            return (Criteria) this;
        }

        public Criteria andStandardLevelBetween(Integer value1, Integer value2) {
            addCriterion("standard_level between", value1, value2, "standardLevel");
            return (Criteria) this;
        }

        public Criteria andStandardLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("standard_level not between", value1, value2, "standardLevel");
            return (Criteria) this;
        }

        public Criteria andIsCapacityStandardIsNull() {
            addCriterion("is_capacity_standard is null");
            return (Criteria) this;
        }

        public Criteria andIsCapacityStandardIsNotNull() {
            addCriterion("is_capacity_standard is not null");
            return (Criteria) this;
        }

        public Criteria andIsCapacityStandardEqualTo(Boolean value) {
            addCriterion("is_capacity_standard =", value, "isCapacityStandard");
            return (Criteria) this;
        }

        public Criteria andIsCapacityStandardNotEqualTo(Boolean value) {
            addCriterion("is_capacity_standard <>", value, "isCapacityStandard");
            return (Criteria) this;
        }

        public Criteria andIsCapacityStandardGreaterThan(Boolean value) {
            addCriterion("is_capacity_standard >", value, "isCapacityStandard");
            return (Criteria) this;
        }

        public Criteria andIsCapacityStandardGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_capacity_standard >=", value, "isCapacityStandard");
            return (Criteria) this;
        }

        public Criteria andIsCapacityStandardLessThan(Boolean value) {
            addCriterion("is_capacity_standard <", value, "isCapacityStandard");
            return (Criteria) this;
        }

        public Criteria andIsCapacityStandardLessThanOrEqualTo(Boolean value) {
            addCriterion("is_capacity_standard <=", value, "isCapacityStandard");
            return (Criteria) this;
        }

        public Criteria andIsCapacityStandardIn(List<Boolean> values) {
            addCriterion("is_capacity_standard in", values, "isCapacityStandard");
            return (Criteria) this;
        }

        public Criteria andIsCapacityStandardNotIn(List<Boolean> values) {
            addCriterion("is_capacity_standard not in", values, "isCapacityStandard");
            return (Criteria) this;
        }

        public Criteria andIsCapacityStandardBetween(Boolean value1, Boolean value2) {
            addCriterion("is_capacity_standard between", value1, value2, "isCapacityStandard");
            return (Criteria) this;
        }

        public Criteria andIsCapacityStandardNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_capacity_standard not between", value1, value2, "isCapacityStandard");
            return (Criteria) this;
        }

        public Criteria andResourceUtilIsNull() {
            addCriterion("resource_util is null");
            return (Criteria) this;
        }

        public Criteria andResourceUtilIsNotNull() {
            addCriterion("resource_util is not null");
            return (Criteria) this;
        }

        public Criteria andResourceUtilEqualTo(String value) {
            addCriterion("resource_util =", value, "resourceUtil");
            return (Criteria) this;
        }

        public Criteria andResourceUtilNotEqualTo(String value) {
            addCriterion("resource_util <>", value, "resourceUtil");
            return (Criteria) this;
        }

        public Criteria andResourceUtilGreaterThan(String value) {
            addCriterion("resource_util >", value, "resourceUtil");
            return (Criteria) this;
        }

        public Criteria andResourceUtilGreaterThanOrEqualTo(String value) {
            addCriterion("resource_util >=", value, "resourceUtil");
            return (Criteria) this;
        }

        public Criteria andResourceUtilLessThan(String value) {
            addCriterion("resource_util <", value, "resourceUtil");
            return (Criteria) this;
        }

        public Criteria andResourceUtilLessThanOrEqualTo(String value) {
            addCriterion("resource_util <=", value, "resourceUtil");
            return (Criteria) this;
        }

        public Criteria andResourceUtilLike(String value) {
            addCriterion("resource_util like", value, "resourceUtil");
            return (Criteria) this;
        }

        public Criteria andResourceUtilNotLike(String value) {
            addCriterion("resource_util not like", value, "resourceUtil");
            return (Criteria) this;
        }

        public Criteria andResourceUtilIn(List<String> values) {
            addCriterion("resource_util in", values, "resourceUtil");
            return (Criteria) this;
        }

        public Criteria andResourceUtilNotIn(List<String> values) {
            addCriterion("resource_util not in", values, "resourceUtil");
            return (Criteria) this;
        }

        public Criteria andResourceUtilBetween(String value1, String value2) {
            addCriterion("resource_util between", value1, value2, "resourceUtil");
            return (Criteria) this;
        }

        public Criteria andResourceUtilNotBetween(String value1, String value2) {
            addCriterion("resource_util not between", value1, value2, "resourceUtil");
            return (Criteria) this;
        }

        public Criteria andUtilizationStandardIsNull() {
            addCriterion("utilization_standard is null");
            return (Criteria) this;
        }

        public Criteria andUtilizationStandardIsNotNull() {
            addCriterion("utilization_standard is not null");
            return (Criteria) this;
        }

        public Criteria andUtilizationStandardEqualTo(String value) {
            addCriterion("utilization_standard =", value, "utilizationStandard");
            return (Criteria) this;
        }

        public Criteria andUtilizationStandardNotEqualTo(String value) {
            addCriterion("utilization_standard <>", value, "utilizationStandard");
            return (Criteria) this;
        }

        public Criteria andUtilizationStandardGreaterThan(String value) {
            addCriterion("utilization_standard >", value, "utilizationStandard");
            return (Criteria) this;
        }

        public Criteria andUtilizationStandardGreaterThanOrEqualTo(String value) {
            addCriterion("utilization_standard >=", value, "utilizationStandard");
            return (Criteria) this;
        }

        public Criteria andUtilizationStandardLessThan(String value) {
            addCriterion("utilization_standard <", value, "utilizationStandard");
            return (Criteria) this;
        }

        public Criteria andUtilizationStandardLessThanOrEqualTo(String value) {
            addCriterion("utilization_standard <=", value, "utilizationStandard");
            return (Criteria) this;
        }

        public Criteria andUtilizationStandardLike(String value) {
            addCriterion("utilization_standard like", value, "utilizationStandard");
            return (Criteria) this;
        }

        public Criteria andUtilizationStandardNotLike(String value) {
            addCriterion("utilization_standard not like", value, "utilizationStandard");
            return (Criteria) this;
        }

        public Criteria andUtilizationStandardIn(List<String> values) {
            addCriterion("utilization_standard in", values, "utilizationStandard");
            return (Criteria) this;
        }

        public Criteria andUtilizationStandardNotIn(List<String> values) {
            addCriterion("utilization_standard not in", values, "utilizationStandard");
            return (Criteria) this;
        }

        public Criteria andUtilizationStandardBetween(String value1, String value2) {
            addCriterion("utilization_standard between", value1, value2, "utilizationStandard");
            return (Criteria) this;
        }

        public Criteria andUtilizationStandardNotBetween(String value1, String value2) {
            addCriterion("utilization_standard not between", value1, value2, "utilizationStandard");
            return (Criteria) this;
        }

        public Criteria andWhitelistsIsNull() {
            addCriterion("whitelists is null");
            return (Criteria) this;
        }

        public Criteria andWhitelistsIsNotNull() {
            addCriterion("whitelists is not null");
            return (Criteria) this;
        }

        public Criteria andWhitelistsEqualTo(String value) {
            addCriterion("whitelists =", value, "whitelists");
            return (Criteria) this;
        }

        public Criteria andWhitelistsNotEqualTo(String value) {
            addCriterion("whitelists <>", value, "whitelists");
            return (Criteria) this;
        }

        public Criteria andWhitelistsGreaterThan(String value) {
            addCriterion("whitelists >", value, "whitelists");
            return (Criteria) this;
        }

        public Criteria andWhitelistsGreaterThanOrEqualTo(String value) {
            addCriterion("whitelists >=", value, "whitelists");
            return (Criteria) this;
        }

        public Criteria andWhitelistsLessThan(String value) {
            addCriterion("whitelists <", value, "whitelists");
            return (Criteria) this;
        }

        public Criteria andWhitelistsLessThanOrEqualTo(String value) {
            addCriterion("whitelists <=", value, "whitelists");
            return (Criteria) this;
        }

        public Criteria andWhitelistsLike(String value) {
            addCriterion("whitelists like", value, "whitelists");
            return (Criteria) this;
        }

        public Criteria andWhitelistsNotLike(String value) {
            addCriterion("whitelists not like", value, "whitelists");
            return (Criteria) this;
        }

        public Criteria andWhitelistsIn(List<String> values) {
            addCriterion("whitelists in", values, "whitelists");
            return (Criteria) this;
        }

        public Criteria andWhitelistsNotIn(List<String> values) {
            addCriterion("whitelists not in", values, "whitelists");
            return (Criteria) this;
        }

        public Criteria andWhitelistsBetween(String value1, String value2) {
            addCriterion("whitelists between", value1, value2, "whitelists");
            return (Criteria) this;
        }

        public Criteria andWhitelistsNotBetween(String value1, String value2) {
            addCriterion("whitelists not between", value1, value2, "whitelists");
            return (Criteria) this;
        }

        public Criteria andMiddlewareIsNull() {
            addCriterion("middleware is null");
            return (Criteria) this;
        }

        public Criteria andMiddlewareIsNotNull() {
            addCriterion("middleware is not null");
            return (Criteria) this;
        }

        public Criteria andMiddlewareEqualTo(String value) {
            addCriterion("middleware =", value, "middleware");
            return (Criteria) this;
        }

        public Criteria andMiddlewareNotEqualTo(String value) {
            addCriterion("middleware <>", value, "middleware");
            return (Criteria) this;
        }

        public Criteria andMiddlewareGreaterThan(String value) {
            addCriterion("middleware >", value, "middleware");
            return (Criteria) this;
        }

        public Criteria andMiddlewareGreaterThanOrEqualTo(String value) {
            addCriterion("middleware >=", value, "middleware");
            return (Criteria) this;
        }

        public Criteria andMiddlewareLessThan(String value) {
            addCriterion("middleware <", value, "middleware");
            return (Criteria) this;
        }

        public Criteria andMiddlewareLessThanOrEqualTo(String value) {
            addCriterion("middleware <=", value, "middleware");
            return (Criteria) this;
        }

        public Criteria andMiddlewareLike(String value) {
            addCriterion("middleware like", value, "middleware");
            return (Criteria) this;
        }

        public Criteria andMiddlewareNotLike(String value) {
            addCriterion("middleware not like", value, "middleware");
            return (Criteria) this;
        }

        public Criteria andMiddlewareIn(List<String> values) {
            addCriterion("middleware in", values, "middleware");
            return (Criteria) this;
        }

        public Criteria andMiddlewareNotIn(List<String> values) {
            addCriterion("middleware not in", values, "middleware");
            return (Criteria) this;
        }

        public Criteria andMiddlewareBetween(String value1, String value2) {
            addCriterion("middleware between", value1, value2, "middleware");
            return (Criteria) this;
        }

        public Criteria andMiddlewareNotBetween(String value1, String value2) {
            addCriterion("middleware not between", value1, value2, "middleware");
            return (Criteria) this;
        }

        public Criteria andAccessComponentIsNull() {
            addCriterion("access_component is null");
            return (Criteria) this;
        }

        public Criteria andAccessComponentIsNotNull() {
            addCriterion("access_component is not null");
            return (Criteria) this;
        }

        public Criteria andAccessComponentEqualTo(String value) {
            addCriterion("access_component =", value, "accessComponent");
            return (Criteria) this;
        }

        public Criteria andAccessComponentNotEqualTo(String value) {
            addCriterion("access_component <>", value, "accessComponent");
            return (Criteria) this;
        }

        public Criteria andAccessComponentGreaterThan(String value) {
            addCriterion("access_component >", value, "accessComponent");
            return (Criteria) this;
        }

        public Criteria andAccessComponentGreaterThanOrEqualTo(String value) {
            addCriterion("access_component >=", value, "accessComponent");
            return (Criteria) this;
        }

        public Criteria andAccessComponentLessThan(String value) {
            addCriterion("access_component <", value, "accessComponent");
            return (Criteria) this;
        }

        public Criteria andAccessComponentLessThanOrEqualTo(String value) {
            addCriterion("access_component <=", value, "accessComponent");
            return (Criteria) this;
        }

        public Criteria andAccessComponentLike(String value) {
            addCriterion("access_component like", value, "accessComponent");
            return (Criteria) this;
        }

        public Criteria andAccessComponentNotLike(String value) {
            addCriterion("access_component not like", value, "accessComponent");
            return (Criteria) this;
        }

        public Criteria andAccessComponentIn(List<String> values) {
            addCriterion("access_component in", values, "accessComponent");
            return (Criteria) this;
        }

        public Criteria andAccessComponentNotIn(List<String> values) {
            addCriterion("access_component not in", values, "accessComponent");
            return (Criteria) this;
        }

        public Criteria andAccessComponentBetween(String value1, String value2) {
            addCriterion("access_component between", value1, value2, "accessComponent");
            return (Criteria) this;
        }

        public Criteria andAccessComponentNotBetween(String value1, String value2) {
            addCriterion("access_component not between", value1, value2, "accessComponent");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("update_by like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("update_by not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andIsPaasIsNull() {
            addCriterion("is_paas is null");
            return (Criteria) this;
        }

        public Criteria andIsPaasIsNotNull() {
            addCriterion("is_paas is not null");
            return (Criteria) this;
        }

        public Criteria andIsPaasEqualTo(Boolean value) {
            addCriterion("is_paas =", value, "isPaas");
            return (Criteria) this;
        }

        public Criteria andIsPaasNotEqualTo(Boolean value) {
            addCriterion("is_paas <>", value, "isPaas");
            return (Criteria) this;
        }

        public Criteria andIsPaasGreaterThan(Boolean value) {
            addCriterion("is_paas >", value, "isPaas");
            return (Criteria) this;
        }

        public Criteria andIsPaasGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_paas >=", value, "isPaas");
            return (Criteria) this;
        }

        public Criteria andIsPaasLessThan(Boolean value) {
            addCriterion("is_paas <", value, "isPaas");
            return (Criteria) this;
        }

        public Criteria andIsPaasLessThanOrEqualTo(Boolean value) {
            addCriterion("is_paas <=", value, "isPaas");
            return (Criteria) this;
        }

        public Criteria andIsPaasIn(List<Boolean> values) {
            addCriterion("is_paas in", values, "isPaas");
            return (Criteria) this;
        }

        public Criteria andIsPaasNotIn(List<Boolean> values) {
            addCriterion("is_paas not in", values, "isPaas");
            return (Criteria) this;
        }

        public Criteria andIsPaasBetween(Boolean value1, Boolean value2) {
            addCriterion("is_paas between", value1, value2, "isPaas");
            return (Criteria) this;
        }

        public Criteria andIsPaasNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_paas not between", value1, value2, "isPaas");
            return (Criteria) this;
        }

        public Criteria andCanSingleHostRestartIsNull() {
            addCriterion("can_single_host_restart is null");
            return (Criteria) this;
        }

        public Criteria andCanSingleHostRestartIsNotNull() {
            addCriterion("can_single_host_restart is not null");
            return (Criteria) this;
        }

        public Criteria andCanSingleHostRestartEqualTo(Boolean value) {
            addCriterion("can_single_host_restart =", value, "canSingleHostRestart");
            return (Criteria) this;
        }

        public Criteria andCanSingleHostRestartNotEqualTo(Boolean value) {
            addCriterion("can_single_host_restart <>", value, "canSingleHostRestart");
            return (Criteria) this;
        }

        public Criteria andCanSingleHostRestartGreaterThan(Boolean value) {
            addCriterion("can_single_host_restart >", value, "canSingleHostRestart");
            return (Criteria) this;
        }

        public Criteria andCanSingleHostRestartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("can_single_host_restart >=", value, "canSingleHostRestart");
            return (Criteria) this;
        }

        public Criteria andCanSingleHostRestartLessThan(Boolean value) {
            addCriterion("can_single_host_restart <", value, "canSingleHostRestart");
            return (Criteria) this;
        }

        public Criteria andCanSingleHostRestartLessThanOrEqualTo(Boolean value) {
            addCriterion("can_single_host_restart <=", value, "canSingleHostRestart");
            return (Criteria) this;
        }

        public Criteria andCanSingleHostRestartIn(List<Boolean> values) {
            addCriterion("can_single_host_restart in", values, "canSingleHostRestart");
            return (Criteria) this;
        }

        public Criteria andCanSingleHostRestartNotIn(List<Boolean> values) {
            addCriterion("can_single_host_restart not in", values, "canSingleHostRestart");
            return (Criteria) this;
        }

        public Criteria andCanSingleHostRestartBetween(Boolean value1, Boolean value2) {
            addCriterion("can_single_host_restart between", value1, value2, "canSingleHostRestart");
            return (Criteria) this;
        }

        public Criteria andCanSingleHostRestartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("can_single_host_restart not between", value1, value2, "canSingleHostRestart");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}