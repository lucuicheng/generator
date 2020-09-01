package ${packageName};

import ${model};
import java.util.Date;

/**
*  <#if author??> @author ${author} <#else> add author here </#if>
*/
public class ${className} extends ${modelName}{
    private static final long serialVersionUID = 3148176768559230877L;

    private String searchStr;

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }
}