package com.iush.ca.transversal.infrastructure.config;

import lombok.Getter;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:CA-sql.properties")
@Getter
public class SqlProperties {

}
