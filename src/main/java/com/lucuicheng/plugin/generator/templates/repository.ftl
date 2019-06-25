package ${packageName};

import ${modelPackageName}.${modelName};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("${modelName?uncap_first}Repository")
    public interface ${modelName}Repository extends JpaRepository<${modelName}, Long>, JpaSpecificationExecutor<${modelName}> {
}