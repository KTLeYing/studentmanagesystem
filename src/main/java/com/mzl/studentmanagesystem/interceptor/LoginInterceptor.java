package com.mzl.studentmanagesystem.interceptor;

import com.mzl.studentmanagesystem.entity.Admin;
import com.mzl.studentmanagesystem.entity.Student;
import com.mzl.studentmanagesystem.entity.Teacher;
import com.mzl.studentmanagesystem.util.Const;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName :   LoginInter登录拦截器TODO
 * @Author: 21989
 * @CreateDate: 2020/7/29 14:53
 * @Version: 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Admin user = (Admin) request.getSession().getAttribute(Const.ADMIN);
        Teacher teacher = (Teacher) request.getSession().getAttribute(Const.TEACHER);
        Student student = (Student) request.getSession().getAttribute(Const.STUDENT);
        if (!StringUtils.isEmpty(user) || !StringUtils.isEmpty(teacher) || !StringUtils.isEmpty(student)){
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/system/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
