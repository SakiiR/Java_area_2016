package com.epitech;

import com.epitech.model.User;
import com.epitech.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * Created by anakin on 14/12/16.
 */

@RunWith(SpringRunner.class)
public class                UserTests {
    @Autowired
    private UserRepository  repository;

    @Test
    public void             createUsers() {
        repository.deleteAll();

        //save a couple of customers
        repository.save(new User());
    }

}
