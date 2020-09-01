package ${packageName};

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
*  <#if author??> @author ${author} <#else> add author here </#if>
*/
public class ${className} {

    protected Pagination pagination;

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ${className}() {
        oredCriteria = new ArrayList<Criteria>();
        pagination = new Pagination();
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public void setPagination(int no, int size) {
        pagination = this.getPagination();
        if(pagination == null) {
            pagination = new Pagination(no, size);
        } else {
            pagination.setNo(no);
            pagination.setSize(size);
        }
        this.setPagination(pagination);
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

        <#list fields as a>
        public Criteria and${a.field?cap_first}IsNull() {
            addCriterion("${a.column} is null");
            return (Criteria) this;
        }

        public Criteria and${a.field?cap_first}IsNotNull() {
            addCriterion("${a.column} is not null");
            return (Criteria) this;
        }

        public Criteria and${a.field?cap_first}EqualTo(Integer value) {
            addCriterion("${a.column} =", value, "${a.column}");
            return (Criteria) this;
        }

        public Criteria and${a.field?cap_first}NotEqualTo(Integer value) {
            addCriterion("${a.column} <>", value, "${a.column}");
            return (Criteria) this;
        }

        public Criteria and${a.field?cap_first}GreaterThan(Integer value) {
            addCriterion("${a.column} >", value, "${a.column}");
            return (Criteria) this;
        }

        public Criteria and${a.field?cap_first}GreaterThanOrEqualTo(Integer value) {
            addCriterion("${a.column} >=", value, "${a.column}");
            return (Criteria) this;
        }

        public Criteria and${a.field?cap_first}LessThan(Integer value) {
            addCriterion("${a.column} <", value, "${a.column}");
            return (Criteria) this;
        }

        public Criteria and${a.field?cap_first}LessThanOrEqualTo(Integer value) {
            addCriterion("${a.column} <=", value, "${a.column}");
            return (Criteria) this;
        }

        public Criteria and${a.field?cap_first}In(List<${a.javaType}> values) {
            addCriterion("${a.column} in", values, "${a.column}");
            return (Criteria) this;
        }

        public Criteria and${a.field?cap_first}NotIn(List<${a.javaType}> values) {
            addCriterion("${a.column} not in", values, "${a.column}");
            return (Criteria) this;
        }

        public Criteria and${a.field?cap_first}Between(Integer value1, Integer value2) {
            addCriterion("${a.column} between", value1, value2, "${a.column}");
            return (Criteria) this;
        }

        public Criteria and${a.field?cap_first}NotBetween(Integer value1, Integer value2) {
            addCriterion("${a.column} not between", value1, value2, "${a.column}");
            return (Criteria) this;
        }
        </#list>
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

	public static class Pagination {
        private int no;
        private int size;

        public Pagination() {
        }

        public Pagination(int no, int size) {
            this.no = no;
            this.size = size;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
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