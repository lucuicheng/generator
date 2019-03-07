package ${packageName};

import java.util.Date;
import java.math.BigDecimal;

/**
*  <#if author??> @author ${author} <#else> add author here </#if>
*/
public class ${className}{

    <#list fields as a>
    private ${a.javaType} ${a.field};
    </#list>

    <#list fields as a>
    public void set${a.field?cap_first}(${a.javaType} ${a.field}){
        this.${a.field} = ${a.field};
    }

    public ${a.javaType} get${a.field?cap_first}(){
        return this.${a.field};
    }
    </#list>

}