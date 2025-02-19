package spring.boot.demo.response;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/02/27 21:35
 */
public enum ResponseEnum {

    SUCCESS("0", "请求成功"),
    ERROR("-1", "系统错误"),
    TIME_OUT("130", "访问超时"),
    NO_PERMISSION("403", "拒绝访问"),
    NO_AUTH("401", "未经授权访问"),
    NOT_FOUND("404", "无法找到资源"),
    METHOD_NOT_ALLOWED("405", "不支持当前请求方法"),
    SERVER_ERROR("500", " 服务器运行异常"),
    NOT_PARAM("10001", "参数不能为空"),
    NOT_EXIST_USER_OR_ERROR_PASSWORD("10002", "该用户不存在或密码错误"),
    NOT_PARAM_USER_OR_ERROR_PASSWORD("10003", "用户名或密码为空"),
    DATA_ACCESS_EXCEPTION("10004", "SQL执行出错"),
    MAX_REQUEST_EXCEEDED_EXCEPTION("10005", "请求内容过大"),
    FILE_SIZE_LIMIT_EXCEEDED_EXCEPTION("10006", "单个文件大小超过最大值"),
    SIZE_LIMIT_EXCEEDED_EXCEPTION("10007", "http请求内容超过最大值"),
    NOT_THIS_USER("10008", "用户名或密码输入错误"),
    HAS_SENSITIVE("10009", "提交内容含有敏感词汇，请修改后提交"),
    ACCOUNT_EXIST("10010", "账号已存在"),
    REGISTER_FAIL("10011", "注册失败"),
    NOT_HAS_SYS_NOTICE("10012", "没有系统公告"),
    SELECT_DATA_FAIL("20001", "查询数据失败"),
    INSERT_DATA_FAIL("20002", "添加数据失败"),
    UPDATE_DATA_FAIL("20003", "更新数据失败"),
    DELETE_DATA_FAIL("20004", "删除数据失败"),
    SELECT_FILE_FAIL("30001", "查询文件失败"),
    INSERT_FILE_FAIL("30002", "添加文件失败"),
    UPDATE_FILE_FAIL("30003", "更新文件失败"),
    DELETE_FILE_FAIL("30004", "删除文件失败"),
    ;

    private String code;
    private String message;

    ResponseEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }

    public static void main(String[] args) {
        System.out.println(ResponseEnum.SUCCESS.getCode());
        System.out.println(ResponseEnum.SUCCESS.getMessage());
    }

}
