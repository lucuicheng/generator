package ${packageName};

import ${model};
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ${className} {

    int deleteByPrimaryKey(Integer id);

    int insert(${modelName} record);

    int insertSelective(${modelName} record);

    ${modelName} selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(@Param("record") ${modelName} record);

    int updateByPrimaryKey(@Param("record") ${modelName} record);
}