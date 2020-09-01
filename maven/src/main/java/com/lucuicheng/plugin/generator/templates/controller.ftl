package ${packageName};

import com.github.pagehelper.PageInfo;
import ${dto};
import ${exceptionPackage}.BusinessException;
import ${exceptionPackage}.ResponseCode;
import ${exceptionPackage}.RestfulAPIException;
import ${dataTablePage};
import ${page};
import ${model};
import ${service};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/wechat")
public class ${className} extends BaseController {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ${"I" + serviceName} ${serviceName?uncap_first};

    //@Autowired
    //private HelloSender helloSender;

    /**
    * @param req
    * @param res
    * @return
    */
    @ResponseBody
    @PostMapping(value = "/${modelName?uncap_first}")
    public Integer createUser(HttpServletRequest req, HttpServletResponse res, ${modelName} record) throws RestfulAPIException {
        try {
            return ${serviceName?uncap_first}.save(record);
        } catch (BusinessException e) {
            throw new RestfulAPIException(e.getCode(), e.getMsg());
        } catch (Exception e) {
            throw new RestfulAPIException(ResponseCode.MISSING_REQUIRED_PARAMS.getCode(), e.getClass() + " ： " + e.getMessage());
        }
    }

    /**
    *
    * @param req
    * @param res
    * @param id
    * @return
    * @throws Exception
    */
    @ResponseBody
    @DeleteMapping(value = "/${modelName?uncap_first}/{id}")
    public Integer deleteUser(HttpServletRequest req, HttpServletResponse res, @PathVariable String id) throws RestfulAPIException {
        try {
            return ${serviceName?uncap_first}.delete(Integer.parseInt(id));
        } catch (BusinessException e) {
            throw new RestfulAPIException(e.getCode(), e.getMsg());
        } catch (Exception e) {
            throw new RestfulAPIException(ResponseCode.MISSING_REQUIRED_PARAMS.getCode(), e.getClass() + " ： " + e.getMessage());
        }
    }


    /**
    *
    * @param req
    * @param res
    * @param id
    * @param record
    * @return
    * @throws Exception
    */
    @ResponseBody
    @PutMapping(value = "/${modelName?uncap_first}/{id}")
    public Integer updateUser(HttpServletRequest req, HttpServletResponse res, @PathVariable String id, ${modelName} record) throws RestfulAPIException {
        try {
            return ${serviceName?uncap_first}.save(record);
        } catch (BusinessException e) {
            throw new RestfulAPIException(e.getCode(), e.getMsg());
        } catch (Exception e) {
            throw new RestfulAPIException(ResponseCode.MISSING_REQUIRED_PARAMS.getCode(), e.getClass() + " ： " + e.getMessage());
        }
    }

    /**
    *
    * @param req
    * @param res
    * @param id
    * @return
    * @throws Exception
    */
    @ResponseBody
    @GetMapping(value = "/${modelName?uncap_first}/{id}")
    public ${modelName} queryUser(HttpServletRequest req, HttpServletResponse res,  @PathVariable String id) throws RestfulAPIException {
        try {
            return ${serviceName?uncap_first}.selectBy(Integer.parseInt(id));
        } catch (BusinessException e) {
            throw new RestfulAPIException(e.getCode(), e.getMsg());
        } catch (Exception e) {
            throw new RestfulAPIException(ResponseCode.MISSING_REQUIRED_PARAMS.getCode(), e.getClass() + " ： " + e.getMessage());
        }
    }

    /**
    *
    * @param req
    * @param res
    * @param pageable
    * @return
    * @throws Exception
    */
    @ResponseBody
    @GetMapping(value = "/${modelName?uncap_first}s")
    public DataTablePage<${dtoName}> queryUsers(HttpServletRequest req, HttpServletResponse res, ${dtoName} record, Pageable pageable) {
        try {

            //helloSender.send("this message is from WeChat");
            DataTablePage<${dtoName}> dataTable = new DataTablePage<>(req);

            pageable.setPageNum(dataTable.getPage_num());
            pageable.setPageSize(dataTable.getPage_size());
            PageInfo<${dtoName}> pageInfo = new PageInfo<>(${serviceName?uncap_first}.selectBy(record, pageable));

            dataTable.setDraw(dataTable.getDraw());
            dataTable.setData(pageInfo.getList());
            dataTable.setRecordsTotal((int) pageInfo.getTotal());
            dataTable.setRecordsFiltered(dataTable.getRecordsTotal());

            return dataTable;
        } catch (BusinessException e) {
            throw new RestfulAPIException(e.getCode(), e.getMsg());
        } catch (Exception e) {
            throw new RestfulAPIException(ResponseCode.MISSING_REQUIRED_PARAMS.getCode(), e.getClass() + " ： " + e.getMessage());
        }
    }

}

