package es.atz.software.products.service.impl;

import es.atz.software.products.dto.UserDTO;
import es.atz.software.products.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Value("${app.url.email-verify}")
    private String urlEmailVerify;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void sendEmailVerify(UserDTO userDTO) {
        try {
            restTemplate.postForEntity(urlEmailVerify + userDTO.getEmail(), null, String.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
