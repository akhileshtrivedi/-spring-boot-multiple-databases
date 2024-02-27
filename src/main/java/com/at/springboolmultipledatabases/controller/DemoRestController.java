package com.at.springboolmultipledatabases.controller;

import com.at.springboolmultipledatabases.product.model.Product;
import com.at.springboolmultipledatabases.product.repository.ProductRepository;
import com.at.springboolmultipledatabases.user.model.User;
import com.at.springboolmultipledatabases.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoRestController {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/addData")
  public String addData2DB() {
    userRepository.saveAll(Stream.of(new User(744, "John"), new User(455, "Smith")).collect(Collectors.toList()));
    productRepository.saveAll(Stream.of(new Product(111, "Core Java"), new Product(222, "Spring Boot")).collect(Collectors.toList()));
    return "Data Added Successfully";
  }

  @GetMapping("/getUsers")
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/getProducts")
  public List<Product> getProducts() {
    return productRepository.findAll();
  }

}