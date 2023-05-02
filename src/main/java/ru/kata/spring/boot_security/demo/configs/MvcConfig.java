package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect: /login");
    }
}

//todo
/*
создай слой сервиса ролей (интерфейс + исплементация)
создай слой сервиса юсера (интерфейс + имплементация)

class AdminController
инжекть бины через интерфейсы
вырежи дублирующие эндпоинты
когда создаешь нового юсера или редактируешь имеющегося отправляй на фронт все роли из БД и предлагай их там. Хардкодить роли на фронте нельзя

class WebSecurityConfig
связывай бины через интерфейсы (UsersDetailsService)
getPasswordEncoder() не должен быть статичным
     */
