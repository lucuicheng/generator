package ${packageName};

import ${dto};
import ${model};
import ${page};

import java.util.List;

public interface ${interfaceName} {

    Integer insert(${modelName} record);

    Integer delete(Integer id);

    Integer update(${modelName} record);

    Integer save(${modelName} record);

    ${modelName} selectBy(Integer id);

    List<${dtoName}> selectBy(${dtoName} record, Pageable pageable);
}