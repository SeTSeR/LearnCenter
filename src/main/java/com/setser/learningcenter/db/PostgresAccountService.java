package com.setser.learningcenter.db;

import com.setser.learningcenter.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(PostgresAccountService.class);

    public PostgresAccountService(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = dbService.findUserByMail(username);
            if (user == null) {
                throw new UsernameNotFoundException("Cannot find user " + username);
            }
            List<GrantedAuthority> grantList = new ArrayList<>();
            GrantedAuthority authority;
            if (user.getIsAdmin()) {
                authority = new SimpleGrantedAuthority("ADMIN");
            } else if (user.getIsPupil()) {
                authority = new SimpleGrantedAuthority("USER");
            } else if (user.getIsTeacher()) {
                authority = new SimpleGrantedAuthority("TEACHER");
            } else {
                throw new UsernameNotFoundException("User " + username + " is of incorrect type.");
            }
            grantList.add(authority);
            return new org.springframework.security.core.userdetails.User(user.getMail(),
                    user.getPassHash(), grantList);
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new UsernameNotFoundException("Cannot find user " + username);
        }
    }
}
