package com.htnova.common.converter;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class ControllerHandler {
    @Resource
    private DateFormatConfig dateFormatConfig;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        GenericConversionService genericConversionService = (GenericConversionService) binder.getConversionService();
        if (genericConversionService != null) {
            genericConversionService.addConverter(dateFormatConfig);
        }
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Result<Void>> handleMultipartException(
        MultipartException e,
        RedirectAttributes redirectAttributes
    ) {
        log.error("文件上传错误：", e);
        return new ResponseEntity<>(Result.build(ResultStatus.UPLOAD_FAILED), HttpStatus.PAYLOAD_TOO_LARGE);
    }
}
