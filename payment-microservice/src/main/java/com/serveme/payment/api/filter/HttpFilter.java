package com.serveme.payment.api.filter;

import com.serveme.payment.api.dto.UserDto;
import com.serveme.payment.exceptions.AccessTokenException;
import com.serveme.payment.service.UserService;
import com.serveme.payment.util.DataUtil;
import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Component
public class HttpFilter implements Filter {

    private final List<String> urlTokenRequire = Arrays.asList(
            "/payment/(\\w+)/card",
            "/payment/customer/(\\w+)/payment_detail",
            "/payment/customer/create/v2"
    );


    private static final Logger logger = Logger.getLogger(HttpFilter.class.getName());

    private final UserService userService;

    @Inject
    public HttpFilter(@Value("${serveme.user-service.url}") String userServiceUrl){
        this.userService = UserService.client(userServiceUrl);
    }


    private boolean isTokenRequire(String path){
        return urlTokenRequire
                .stream()
                .filter(path::matches)
                .count() > 0;
    }


    /**
     * If the path is one of those which requires authentication,
     * this filter will check that the access-token is there and is valid.
     * If it is, will forward the userDto by the attribute 'user'.
     * If not, will return a HTTP 401 and a JSON error
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {

        logger.log(Level.INFO, "Authentication filter");
        HttpServletRequest httpReq = (HttpServletRequest)req;
        printRequest(httpReq);
        if(isTokenRequire(httpReq.getServletPath())){
            String accessToken = httpReq.getHeader("access-token");
            try{
                UserDto user = userService.authenticate(accessToken);
                logger.log(Level.INFO, "User authenticated "+user.toString());
                req.setAttribute("user", user);
            }catch(AccessTokenException ex){
                logger.log(Level.SEVERE, "Authentication exception", ex);
                sendError(res, accessToken);
                return;
            }
        }

        logger.log(Level.INFO, "Carry on the chain");
        filterChain.doFilter(req,res);
    }

    private void sendError(ServletResponse res, String accessToken) throws IOException {

        BaseErrorResponse error = new ErrorResponse("UNAUTHORIZED", "Invalid token")
                .addDetail("token", accessToken);
        ((HttpServletResponse)res).setStatus(401);
        res.setContentType("application/json");
        res.getWriter().write(DataUtil.toJson(error));
    }

    private void printRequest(HttpServletRequest req){
        String method = req.getMethod();
        String path = req.getServletPath();
        String query = req.getQueryString();

        StringBuilder p = new StringBuilder(String.format("\n\t%s: %s%s", method,path,query)).append("\n");
        if(req.getHeaderNames() != null && req.getHeaderNames().hasMoreElements()){
            p.append("\tHeaders:\n");
            Enumeration<String> headerNames = req.getHeaderNames();
            while(headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement();
                p.append("\t\t")
                        .append(headerName)
                        .append(": ")
                        .append(req.getHeader(headerName))
                        .append("\n");
            }
        }
        logger.log(Level.INFO, p.toString());



    }

    @Override
    public void destroy() {

    }



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
