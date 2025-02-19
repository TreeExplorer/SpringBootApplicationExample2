package spring.boot.demo.exception;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/4/13 10:11
 */
public class ExampleException extends RuntimeException{

    /**
     * 添加一个空参数的构造方法
     */
    public ExampleException(){
        super();
    }

    /**
     * 添加一个带异常信息的构造方法
     * 查看源码发现,所有的异常类都会有一个带异常信息的构造方法,方法内部会调用父类带异常信息的构造方法,让父类来处理这个异常信息
     */
    public ExampleException(String message){
        super(message);
    }

}
