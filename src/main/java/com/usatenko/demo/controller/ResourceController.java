package com.usatenko.demo.controller;

import com.usatenko.demo.controller.api.ControllerAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping(path = ControllerAPI.RESOURCE_CONTROLLER)
public class ResourceController {

    @GetMapping(value = ControllerAPI.RESOURCE_CONTROLLER_INDEX, produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        return "index";
    }
}
