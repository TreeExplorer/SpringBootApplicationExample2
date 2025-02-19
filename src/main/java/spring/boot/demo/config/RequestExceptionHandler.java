package spring.boot.demo.config;

import spring.boot.demo.response.ResponseEnum;
import spring.boot.demo.response.ResponseResult;
import spring.boot.demo.util.VarsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/3/3 0:08
 */
@Slf4j
@RestControllerAdvice
public class RequestExceptionHandler{

    @ExceptionHandler(value = Exception.class)
    public ResponseResult defaultExceptionHandler(HttpServletRequest req, Exception e){

        // 根据抓获的异常类型，做逻辑处理，并打印日志信息
        log.error("出现异常：{}", e);

        if (e instanceof IllegalAccessException){
            // 403 拒绝访问异常
            return new ResponseResult<>(ResponseEnum.NO_PERMISSION);
        } else if (e instanceof NoHandlerFoundException){
            // 404 无法找到资源
            return new ResponseResult<>(ResponseEnum.NOT_FOUND);
        } else if (e instanceof HttpRequestMethodNotSupportedException){
            // 405 不支持当前请求方法
            return new ResponseResult<>(ResponseEnum.METHOD_NOT_ALLOWED);
        } else if (e instanceof DataAccessException){
            // org.springframework.dao.DataAccessException
            // 10004 数据访问异常
            return new ResponseResult<>(ResponseEnum.DATA_ACCESS_EXCEPTION);
        } else if (e instanceof MaxUploadSizeExceededException){
            Throwable t1 = e.getCause();
            if (t1 instanceof IllegalStateException){
                String msg;
                Throwable t2 = t1.getCause();
                if (t2 instanceof FileSizeLimitExceededException){
                    // 10006 单个文件大小超过最大值
                    msg = "允许最大单个文件大小：" + VarsUtil.maxUploadSize;
                    return new ResponseResult<>(ResponseEnum.FILE_SIZE_LIMIT_EXCEEDED_EXCEPTION, msg);
                } else if (t2 instanceof SizeLimitExceededException){
                    // 10007 http请求超过最大值
                    msg = "允许最大请求大小：" + VarsUtil.maxRequestSize;
                    return new ResponseResult<>(ResponseEnum.SIZE_LIMIT_EXCEEDED_EXCEPTION, msg);
                }
            }
            // 10005 请求内容过大
            return new ResponseResult<>(ResponseEnum.MAX_REQUEST_EXCEEDED_EXCEPTION);
        } else {
            // 500 其它异常
            return new ResponseResult<>(ResponseEnum.SERVER_ERROR, e.getMessage());
        }

    }

}
