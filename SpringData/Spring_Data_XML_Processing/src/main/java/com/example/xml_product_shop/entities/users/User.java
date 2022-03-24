package com.example.xml_product_shop.entities.users;

import com.example.xml_product_shop.entities.products.Product;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private Integer age;

    @OneToMany(targetEntity = Product.class, mappedBy = "seller")
    private List<Product> productsSold;

    @OneToMany(targetEntity = Product.class, mappedBy = "buyer")
    private List<Product> productsBought;

    @ManyToMany
    private Set<User> friends;

    public User() {
        this.productsSold = new LinkedList<>();
        this.productsBought = new LinkedList<>();
        this.friends = new HashSet<>();
    }

    public User(String firstName, String lastName, Integer age) {
        this();

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Product> getProductsSold() {
        return productsSold;
    }

    public void setProductsSold(List<Product> productsSold) {
        this.productsSold = productsSold;
    }

    public List<Product> getProductsBought() {
        return productsBought;
    }

    public void setProductsBought(List<Product> productsBought) {
        this.productsBought = productsBought;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(friends, user.friends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(friends);
    }
}
