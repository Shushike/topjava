package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("Exception at request " + req.getRequestURL(), e);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        Throwable rootCause = ValidationUtil.getRootCause(e);
        ErrorInfo constraintInfo = ValidationUtil.constraintError(req, e, UserRepository.EMAIL_CONSTRAINT, User.EMAIL_ERR_MSG);
        Map<String, Serializable> props;
        if (constraintInfo != null) {
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            props = Map.of("exception", rootCause, "message", constraintInfo.getDetails().stream().collect(Collectors.joining("<br>")), "status", httpStatus);
        } else {
            props = Map.of("exception", rootCause, "message", rootCause.toString(), "status", httpStatus);
        }

        ModelAndView mav = new ModelAndView("exception", props);
        mav.setStatus(httpStatus);

        // Interceptor is not invoked, put userTo
        AuthorizedUser authorizedUser = SecurityUtil.safeGet();
        if (authorizedUser != null) {
            mav.addObject("userTo", authorizedUser.getUserTo());
        }
        return mav;
    }
}
