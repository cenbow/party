package com.party.mobile.web.controller;

import com.party.authorization.annotation.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * TokenController
 *
 * @author Wesley
 * @data 16/9/22 20:56 .
 */
@RestController
@RequestMapping("/home")
public class TokenController {

    @RequestMapping(value="h",method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

}
