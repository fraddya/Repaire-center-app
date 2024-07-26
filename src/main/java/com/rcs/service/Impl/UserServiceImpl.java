package com.rcs.service.Impl;


import com.querydsl.core.BooleanBuilder;
import com.rcs.domain.User;
import com.rcs.domain.base.ComplexValidationException;
import com.rcs.domain.criteria.UserCriteria;
import com.rcs.enums.Status;
import com.rcs.repository.UserRepository;
import com.rcs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public User save(User user) {
        user.setStatus(Status.ACTIVE);
        user.setDateJoin(LocalDate.now().toString());
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<User> search(UserCriteria criteria) {

        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.by(Sort.Direction.fromOptionalString(criteria.getSortDirection()).orElse(Sort.Direction.DESC),
                        criteria.getSortProperty()));

        Page<User> users = null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        /*if (StringUtils.isNotBlank(criteria.getEmail())) {
            booleanBuilder.and(QEmployee.employee.email.containsIgnoreCase(criteria.getEmail()));
        }
        if (StringUtils.isNotBlank(criteria.getPassWord())) {
            booleanBuilder.and(QEmployee.employee.passWord.containsIgnoreCase(criteria.getPassWord()));
        }*/

        if (booleanBuilder.hasValue()){
            users = userRepository.findAll(booleanBuilder, page);
        } else {
            users = userRepository.findAll(page);
        }
        return users;
    }

    @Transactional(readOnly = true)
    @Override
    public User retrieve(Long id) {
        return userRepository.findById(id).orElseThrow(()->
                new ComplexValidationException("Employee id (%s) not found", id.toString()));
    }

    @Transactional
    @Override
    public User update(User user) {
        user.setStatus(Status.ACTIVE);
        Optional<User> userPersisted = userRepository.findById(user.getId());
        if (userPersisted.isPresent()) {
            if (user.getAge() != null) {
                userPersisted.get().setAge(user.getAge());
            }
            if (user.getFirstName() != null) {
                userPersisted.get().setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                userPersisted.get().setLastName(user.getLastName());
            }
            if (user.getContactNo() != null) {
                userPersisted.get().setContactNo(user.getContactNo());
            }
            if (user.getGenderType() != null) {
                userPersisted.get().setGenderType(user.getGenderType());
            }
            if (user.getNic() != null) {
                userPersisted.get().setNic(user.getNic());
            }
            if (user.getNationality() != null) {
                userPersisted.get().setNationality(user.getNationality());
            }
            if (user.getReligion() != null) {
                userPersisted.get().setReligion(user.getReligion());
            }
            if (user.getEmail() != null) {
                userPersisted.get().setEmail(user.getEmail());
            }
            if (user.getUserLogging() != null) {
                userPersisted.get().setUserLogging(user.getUserLogging());
            }
            return userRepository.save(userPersisted.get());
        } else {
            throw new ComplexValidationException("Employee id (%s) not found", userPersisted.get().getId().toString());
        }
    }



    @Transactional
    @Override
    public User delete(Long id) {
        User user = userRepository.getReferenceById(id);
        if (user != null) {
            user.setStatus(Status.DELETED);
            return userRepository.save(user);
        }
        return null;
    }
}
