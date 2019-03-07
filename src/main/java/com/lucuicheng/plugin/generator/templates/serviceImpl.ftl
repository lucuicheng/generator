package ${packageName};

import com.github.pagehelper.PageHelper;
import ${dto};
import ${model};
import ${mapper};
import ${customMapper};
import ${page};
import ${service};
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ${className} implements ${interfaceName} {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ${mapperName} ${mapperName?uncap_first};

    @Autowired
    private ${customMapperName} ${customMapperName?uncap_first};

    public Integer insert(${modelName} record) {
        ${mapperName?uncap_first}.insertSelective(record);
        return record.getId();
    }

    public Integer delete(Integer id) {
        return ${mapperName?uncap_first}.deleteByPrimaryKey(id);
    }

    public Integer update(${modelName} record) {
        return ${mapperName?uncap_first}.updateByPrimaryKeySelective(record);
    }

    public Integer save(${modelName} record) {
        if(record!=null) {
            if(record.getId() == null) {
                record.setCreateTime(new Date());
                this.insert(record);
            } else {
                record.setLastUpdate(new Date());
                this.update(record);
            }
        }
        return record.getId();
    }

    public ${modelName} selectBy(Integer id) {
        return ${mapperName?uncap_first}.selectByPrimaryKey(id);
    }

    public List<${dtoName}> selectBy(${dtoName} record, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize());
        PageHelper.orderBy(pageable.getPageOrder());

        List<${dtoName}> list = ${customMapperName?uncap_first}.selectBy(record);

        return list;
    }

}