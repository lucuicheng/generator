<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${namespace}">

    <sql id="Base_Column_List">
        <#list fields as a>
            ${a.column} AS ${a.field}<#if a_has_next>, </#if>
        </#list>
    </sql>

    <!-- 查询z自定义内容  -->
    <select id="selectBy" resultType="${dto}" parameterType="${dto}">
        select
        <include refid="Base_Column_List" />
        from ${tableName}
        <where>
            <if test="${key.field} != null ">${key.column}=${r'#{' + key.field + '}'}</if>
            <#list fields as a>
            <#if !(a.field == key.field) && a.field != 'id'>
            <if test="${a.field} != null">
                AND ${a.column} = ${r'#{' + a.field + '}'}
            </if>
            </#if>
            </#list>
            <if test="searchStr != null">
                AND name_ like concat(concat('%',${r'#{searchStr}'}),'%')
            </if>
        </where>
    </select>

</mapper>