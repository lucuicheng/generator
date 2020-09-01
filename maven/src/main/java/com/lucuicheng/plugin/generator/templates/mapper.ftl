<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${namespace}">

    <resultMap id="BaseResultMap" type="${model}">
        <#list fields as a>
        <#if a.key?? && a.key == "PRI">
        <id column="${a.column}" jdbcType="${a.jdbcType}" property="${a.field}"/>
        <#else>
        <result column="${a.column}" jdbcType="${a.jdbcType}" property="${a.field}"/>
        </#if>
        </#list>
    </resultMap>

    <sql id="Base_Column_List">
        <#list fields as a>
            ${a.column}<#if a_has_next>, </#if>
        </#list>
    </sql>

    <select id="selectByPrimaryKey" parameterType="${key.javaType}" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from ${tableName}
        where ${key.column} = ${r'#{' + key.column + ', jdbcType=' + key.jdbcType + '}'}
    </select>

    <delete id="deleteByPrimaryKey" parameterType="${key.javaType}">
        delete from ${tableName}
        where ${key.column} = ${r'#{' + key.field + ', jdbcType=' + key.jdbcType + '}'}
    </delete>

    <insert id="insert" parameterType="${model}">
        <selectKey keyProperty="${key.column}" order="AFTER" resultType="${key.javaType}">
            SELECT LAST_INSERT_ID()
        </selectKey>
        insert into ${tableName} (
            <#list fields as a>
            ${a.column}<#if a_has_next>, </#if>
            </#list>
        )
        values (
            <#list fields as a>
            ${r'#{' + a.field + ', jdbcType=' + a.jdbcType + '}'}<#if a_has_next>, </#if>
            </#list>
        )
    </insert>

    <insert id="insertSelective" parameterType="${model}">
        <selectKey keyProperty="${key.column}" order="AFTER" resultType="${key.javaType}">
            SELECT LAST_INSERT_ID()
        </selectKey>
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list fields as a>
            <if test="${a.field} != null">
                ${a.column},
            </if>
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#list fields as a>
            <if test="${a.field} != null">
            ${r'#{' + a.field + ', jdbcType=' + a.jdbcType + '}'},
            </if>
        </#list>
        </trim>
    </insert>

    <update id="updateByPrimaryKeySelective" parameterType="${model}">
        update ${tableName}
        <set>
        <#list fields as a>
            <#if !(a.field == key.field)>
            <if test="record.${a.field} != null">
            ${a.column} = ${r'#{record.' + a.field + ', jdbcType=' + a.jdbcType + '}'},
            </if>
            </#if>
        </#list>
        </set>
        where ${key.column} = ${r'#{record.' + key.field + ', jdbcType=' + key.jdbcType + '}'}
    </update>

    <update id="updateByPrimaryKey" parameterType="${model}">
        update ${tableName}
        set
        <#list fields as a>
        <#if !(a.field == key.field)>
        ${a.column} = ${r'#{record.' + a.field + ', jdbcType=' + a.jdbcType + '}'}<#if a_has_next>, </#if>
        </#if>
        </#list>
        where ${key.column} = ${r'#{record.' + key.field + ', jdbcType=' + key.jdbcType + '}'}
    </update>

</mapper>