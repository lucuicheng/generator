package ${packageName};

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseController implements Serializable{
	
	private static final long serialVersionUID = -3710678227111906070L;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected static final String  JSON_STATUSCODE = "statusCode";

	protected static final String  JSON_RESULT = "result";

	protected static final String  JSON_RESULT_SUCESS = "200";

	protected static final String  JSON_RESULT_FAIL = "300";

	protected static final String  JSON_RESULT_TIMEOUT = "301";

	protected static final String  JSON_MESSAGE = "message";

	protected static final String  JSON_FORWARDURL_URL = "forwardUrl";

	public static final String  JSON_UPLOAD_URL = "url";

	protected static final String  QUERY_PAGENO = "pageNo";

	protected static final String  QUERY_PAGESIZE = "pageSize";

	protected static final String  QUERY_PAGEBACK = "back";

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	protected HttpSession session;

	protected Integer pageno = null;

	protected Integer pagesize;

    protected String param;

	protected String back;

	protected String search;

	protected Map<String, Object> jsonData = new LinkedHashMap<String, Object>();

	protected JsonConfig jsonConfig = new JsonConfig();

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Integer getPageno() {
		return pageno;
	}

	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}

	public JsonConfig getJsonConfig() {
		return jsonConfig;
	}

	public void setJsonConfig(JsonConfig jsonConfig) {
		this.jsonConfig = jsonConfig;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession();
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	protected void handRequest(HttpServletRequest req, HttpServletResponse resp) {
		setServletRequest(req);
		setServletResponse(resp);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
	}

	public void setJsonResult(String statusCode, String message) {
		jsonData.put(JSON_MESSAGE, message);
		jsonData.put(JSON_STATUSCODE, statusCode);
	}
	
	public void setJsonResult(String statusCode, String message, JSONObject result) {
		jsonData.put(JSON_MESSAGE, message);
		jsonData.put(JSON_STATUSCODE, statusCode);
		jsonData.put(JSON_RESULT, result);
	}

	protected void clearQueryMap(){
		if(session != null){
			session.removeAttribute("queryMap");
		}
	}

	public void setCookie(String name,String value){
		Cookie cookieName =new Cookie(name,value);
		response.addCookie(cookieName);
	}

	public String getCookie(String name){
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(name.equals(cookie.getName())){
				return cookie.getValue();
			}
		}
		return null;
	}
}
