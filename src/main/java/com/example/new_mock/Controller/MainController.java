package com.example.new_mock.Controller;

import com.example.new_mock.Model.RequestDTO;
import com.example.new_mock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

@RestController
public class MainController {
    private Logger log = LoggerFactory.getLogger(MainController.class);
    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "/info/postBalances", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clientID = requestDTO.getClientId();
            char firstDigit = clientID.charAt(0);
            String rqUID = requestDTO.getRqUID();
            String ClientId = requestDTO.getClientId();

            BigDecimal maxlimit;
            BigDecimal min = new BigDecimal(0);
            String currency;
            if (firstDigit == '8') {
                currency = "US";
                maxlimit = new BigDecimal(2000);
            } else if (firstDigit == '9') {
                currency = "EU";
                maxlimit = new BigDecimal(1000);
            } else {
                currency = "RUB";
                maxlimit = new BigDecimal(10000);
            }
            BigDecimal random_balance = new BigDecimal(new Random().nextInt(maxlimit.intValue() + 1));
            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(rqUID);
            responseDTO.setClientId(ClientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(random_balance);
            responseDTO.setMaxLimit(maxlimit);

            log.error("************ RequestDTO ***********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("************ ResponseDTO ***********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }
}
