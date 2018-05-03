package com.recharge.protal.advice;



import com.recharge.protal.common.ServerResponse;
import com.recharge.protal.exception.BusinessException;
import com.recharge.protal.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Juphoon on 2017/8/3.
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);


    /**
     * 错误处理器
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public String handleIOException(HttpServletRequest request, HttpServletResponse response, Model model, Exception e) {
        logger.info(e.toString());
        logger.error("error", e);
        //请求类型,可以区分对待ajax和普通请求
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");

            PrintWriter writer = response.getWriter();
            //具体操作
            String message = e.getMessage();
            if(message == null)
                message = e.toString();

            if(e instanceof BusinessException) {
                BusinessException b = (BusinessException)e;
                writer.write(JsonUtils.objectToJsonNoNull(new ServerResponse(b.getCode(), null, b.getMessage())));
            } else {
                writer.write(JsonUtils.objectToJsonNoNull(ServerResponse.error(e.toString())));
            }

            writer.flush();
            writer.close();
            return null;
        } catch (IOException e1) {

        }

        return null;
    }

}
