package com.farata.course.mwd.auction.service;

import com.farata.course.mwd.auction.entity.User;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ds on 01/02/15.
 */

@Singleton
public class UserService {
    private Map<Integer, User> users = new HashMap<>();

    public User getUserByID(int userId) {
        if (users.containsKey(userId)) {
            return users.get(userId);
        } else {
            synchronized (this) {
                if (users.containsKey(userId)) {
                    return users.get(userId);
                } else {
                    //TODO auth
                    User u = new User(userId, "user " + userId, "user" + userId + "@google.com", false);
                    users.put(userId, u);
                    return u;
                }

            }
        }
    }
}
