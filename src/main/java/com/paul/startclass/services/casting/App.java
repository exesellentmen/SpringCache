package com.paul.startclass.services.casting;

import com.paul.startclass.StartclassApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


public class App {

    public static void main(String[] args) {
        MyInterface child = new Children();
        child.test("tess");
        System.out.println("test");
    }
}
