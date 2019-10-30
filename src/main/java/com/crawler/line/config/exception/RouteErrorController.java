package com.crawler.line.config.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//Front Vue router의 직접링크 시 RequestMapping에서 찾아 에러페이지로 가기 때문에
//index로 리다이렉트 해준다.
@Controller
public class RouteErrorController implements ErrorController {

    @GetMapping("/error")
    public String redirectRoot() {
        return "index.html";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
