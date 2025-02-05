package org.launchcode.codingevents;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.launchcode.codingevents.controllers.AuthenticationController;
import org.launchcode.codingevents.data.UserRepository;
import org.launchcode.codingevents.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import java.io.IOException;
import java.util.Arrays;

public class AuthenticationFilter implements HandlerInterceptor {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    private static final List<String> whitelist = Arrays.asList("login", "register", "logout", "css");

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {

        if(isWhitelisted(request.getRequestURI())) {
            return true;

        }
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if(user != null) {
            return true;
        }

        response.sendRedirect("login");
        return false;
    }

    private static boolean isWhitelisted(String path) {
        for(String pathRoot : whitelist) {
            if(path.startsWith(pathRoot)) {
                return true;
            }
        }
        return false;
    }

    @Configuration
    public class WebApplicationConfig implements WebMvcConfigurer {

        @Bean
        public AuthenticationFilter authenticationFilter() {
            return new AuthenticationFilter();
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor( authenticationFilter() );
        }
    }

}
