package com.setser.learningcenter.db;

import com.setser.learningcenter.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostgresAccountService implements UserDetailsService {
    private final DBService dbService;

    public PostgresAccountService(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = dbService.findUserByMail(username);
            List<GrantedAuthority> grantList = new ArrayList<>();
            GrantedAuthority authority;
            if (user.isAdmin()) {
                authority = new SimpleGrantedAuthority("ADMIN");
            } else if (user.isPupil()) {
                authority = new SimpleGrantedAuthority("USER");
            } else if (user.isTeacher()) {
                authority = new SimpleGrantedAuthority("TEACHER");
            } else {
                throw new UsernameNotFoundException("User " + username + " is of incorrect type.");
            }
            grantList.add(authority);
            return new org.springframework.security.core.userdetails.User(user.getMail(),
                    user.getPassHash(), grantList);
        } catch (DBException e) {
            throw new UsernameNotFoundException("Cannot find user " + username);
        }
    }
}
