//package spring.boot.demo.config;
//
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import spring.boot.demo.annotation.LoginValidator;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.annotation.Resource;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
///**
// * @author Tree
// * @version V1.0
// * @Title:
// * @Description: 过滤未登录用户的请求
// *               redis服务器未安装、前端没有添加token故不开放登录校验
// * @date 2022/4/13 10:21
// */
//@Component
//@Aspect
//public class LoginAspect {
//
//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
//
//    /**
//     * 切点，方法上有注解或者类上有注解
//     *  拦截类或者是方法上标注注解的方法
//     */
//    @Pointcut(value = "@annotation(spring.boot.demo.annotation.LoginValidator) || @within(spring.boot.demo.annotation.LoginValidator)")
//    public void pointCut() {}
//
//    @Around("pointCut()")
//    public Object before(ProceedingJoinPoint joinpoint) throws Throwable {
//        // 获取方法方法上的LoginValidator注解
//        MethodSignature methodSignature = (MethodSignature)joinpoint.getSignature();
//        Method method = methodSignature.getMethod();
//        LoginValidator loginValidator = method.getAnnotation(LoginValidator.class);
//        // 如果有，并且值为false，则不校验
//        if (loginValidator != null && !loginValidator.validated()) {
//            return joinpoint.proceed(joinpoint.getArgs());
//        }
//        // 正常校验 获取request和response
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (requestAttributes == null || requestAttributes.getResponse() == null) {
//            // 如果不是从前段过来的，没有request，则直接放行
//            return joinpoint.proceed(joinpoint.getArgs());
//        }
//        HttpServletRequest request = requestAttributes.getRequest();
//        HttpServletResponse response = requestAttributes.getResponse();
//        // 获取token
//        String token = request.getHeader(Constant.TOKEN_HEADER_NAME);
//        if (StringUtils.isBlank(token)) {
//            returnNoLogin(response);
//            return null;
//        }
//        // 从redis中拿token对应user
//        User user = (User) redisTemplate.opsForValue().get(Constant.REDIS_USER_PREFIX + token);
//        if (user == null) {
//            returnNoLogin(response);
//            return null;
//        }
//        // token续期
//        redisTemplate.expire(Constant.REDIS_USER_PREFIX + token, 30, TimeUnit.MINUTES);
//        // 放行
//        return joinpoint.proceed(joinpoint.getArgs());
//    }
//
//    /**
//     * 返回未登录的错误信息
//     * @param response ServletResponse
//     */
//    private void returnNoLogin(HttpServletResponse response) throws IOException {
//        ServletOutputStream outputStream = response.getOutputStream();
//        // 设置返回401 和响应编码
//        response.setStatus(401);
//        response.setContentType("Application/json;charset=utf-8");
//        // 构造返回响应体
//        Result<String> result = Result.<String>builder()
//                .code(HttpStatus.UNAUTHORIZED.value())
//                .errorMsg("未登陆，请先登陆")
//                .build();
//        String resultString = JSONUtil.toJsonStr(result);
//        outputStream.write(resultString.getBytes(StandardCharsets.UTF_8));
//    }
//
//}
