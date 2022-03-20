package com.example.json_ex_products_shop.entities.users;

import java.util.List;

public class UserProductsSoldMainDTO {
    private int usersCount;
    private List<UserProductSoldDTO> users;

    public int getUsersCount() {
        return usersCount;
    }

    public UserProductsSoldMainDTO(List<UserProductSoldDTO> users) {
        this.usersCount = users.size();
        this.users = users;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserProductSoldDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserProductSoldDTO> users) {
        this.users = users;
    }
}
