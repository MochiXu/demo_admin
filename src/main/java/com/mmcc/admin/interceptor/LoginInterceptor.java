package com.mmcc.admin.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**1.执行登陆检查
 * */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 预处理回调方法，实现处理器的预处理（如检查登陆），第三个参数为响应的处理器，自定义Controller
     * 返回值：true表示继续流程（如调用下一个拦截器或处理器）；
     　　　*       false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时我们需要通过response来产生响应；
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI=request.getRequestURI();
        System.out.println("preHandle拦截的请求路径是："+requestURI);

        //登陆检查逻辑
        HttpSession session=request.getSession();
        Object loginUser=session.getAttribute("loginUer");

        if(loginUser!=null) {
            System.out.println("loginUser不是null===" + loginUser);
            return true;
        }
        else {
            //进行拦截，并跳转到登录页
            request.setAttribute("msg","被拦截器拦截");
            //使用请求域进行转发 [无论是请求转发还是请求包含，使用的都是同一个request和response]
            request.getRequestDispatcher("/").forward(request,response);
            return false;

        }


    }

    /**
     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前），此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，modelAndView也可能为null。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，如性能监控中我们可以在此记录结束时间并输出消耗时间，还可以进行一些资源清理，类似于try-catch-finally中的finall
     　　 * 但仅调用处理器执行链中preHandle返回true的拦截器的afterCompletion。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
