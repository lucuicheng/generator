package ${packageName};

import javax.persistence.Table;
import javax.persistence.*;
import java.util.Date;
import java.math.BigDecimal;

/**
*  <#if author??> @author ${author} <#else> add author here </#if>
*/
@Entity
@Table(name = "${tableName}")
public class ${className}{

    <#list fields as a>
    <#if a.key?? && a.key == "PRI">
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ${a.field};
    <#else>
    private ${a.javaType} ${a.field};//${a.comment}
    </#if>
    </#list>

    <#list fields as a>
    <#if a.key?? && a.key == "PRI">
    public void set${a.field?cap_first}(Long ${a.field}){
    this.${a.field} = ${a.field};
    }

    public Long get${a.field?cap_first}(){
    return this.${a.field};
    }
    <#else>
    public void set${a.field?cap_first}(${a.javaType} ${a.field}){
        this.${a.field} = ${a.field};
    }

    public ${a.javaType} get${a.field?cap_first}(){
        return this.${a.field};
    }
    </#if>
    </#list>

}