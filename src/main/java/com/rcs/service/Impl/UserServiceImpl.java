package com.rcs.service.Impl;


import com.querydsl.core.BooleanBuilder;
import com.rcs.domain.QUser;
import com.rcs.domain.User;
import com.rcs.domain.Vehicle;
import com.rcs.domain.base.ComplexValidationException;
import com.rcs.domain.criteria.UserCriteria;
import com.rcs.enums.Status;
import com.rcs.enums.UserType;
import com.rcs.repository.UserRepository;
import com.rcs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User save(User user) {
        User persistedUser = userRepository.findByEmail(user.getEmail());
        if (persistedUser != null) {
            throw new ComplexValidationException("User registration", "this Email already registered");
        }
        user.setStatus(Status.ACTIVE);
        user.setDateJoin(LocalDate.now());
        if (user.getRole() == null) user.setRole(UserType.USER);
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<User> search(UserCriteria criteria) {

        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.by(Sort.Direction.fromOptionalString(criteria.getSortDirection()).orElse(Sort.Direction.DESC),
                        criteria.getSortProperty()));

        Page<User> users = null;

        BooleanBuilder booleanBuilder = new BooleanBuilder(QUser.user.status.ne(Status.DELETED));

        if (StringUtils.isNotBlank(criteria.getEmail())) {
            booleanBuilder.and(QUser.user.email.containsIgnoreCase(criteria.getEmail()));
        }
        if (criteria.getRole() != null ) {
            booleanBuilder.and(QUser.user.role.eq(UserType.valueOf(String.valueOf(criteria.getRole()))));
        }

        if (booleanBuilder.hasValue()){
            users = userRepository.findAll(booleanBuilder, page);
        } else {
            users = userRepository.findAll(page);
        }
        for (User user : users) {
            user.getVehicle().size();
        }
        return users;
    }

    @Transactional(readOnly = true)
    @Override
    public User retrieve(Long id) {
        Optional<User> userPersisted = userRepository.findById(id);
        if (!userPersisted.isPresent()) {
            throw new ComplexValidationException("user retrieval","User (%s) not found  ");
        }
        userPersisted.get().getVehicle().size();
        return userPersisted.get();
    }


    @Transactional
    @Override
    public User update(User user) {
        Optional<User> userPersisted = userRepository.findById(user.getId());
        if (!userPersisted.isPresent()) {
            throw new ComplexValidationException("User retrieval", "Cannot find any User ");
        } else {
            User userDb = userPersisted.get();
            updateFields(user, userDb);
            if (user.getVehicle() != null) {
                updateVehicles(user, userDb);
            }
            return userRepository.save(userDb);
        }
    }

    private void updateFields(User user, User userDb) {
        if (user.getFirstName() != null) userDb.setFirstName(user.getFirstName());
        if (user.getLastName() != null) userDb.setLastName(user.getLastName());
        if (user.getContactNo() != null) userDb.setContactNo(user.getContactNo());
        if (user.getDateJoin() != null) userDb.setDateJoin(user.getDateJoin());
        if (user.getAge() != null) userDb.setAge(user.getAge());
        if (user.getGenderType() != null) userDb.setGenderType(user.getGenderType());
        if (user.getNic() != null) userDb.setNic(user.getNic());
        if (user.getNationality() != null) userDb.setNationality(user.getNationality());
        if (user.getImage() != null) userDb.setImage(user.getImage());
        if (user.getReligion() != null) userDb.setReligion(user.getReligion());
        if (user.getPassWord() != null) userDb.setPassWord(passwordEncoder.encode(user.getPassWord()));
        //if (user.getPassWord() != null) userDb.setPassWord((user.getPassWord()));
        if (user.getRole() != null) userDb.setRole(user.getRole());

    }

    private void updateVehicles(User user, User userDb) {
        List<Vehicle> newVehicles = new ArrayList<>();
        List<Vehicle> deletedVehicles = new ArrayList<>();

        if (user.getVehicle() != null && !user.getVehicle().isEmpty()) {
            if (user.getVehicle().size() >= userDb.getVehicle().size()) {
                for (Vehicle updatedVehicle : user.getVehicle()) {
                    if (updatedVehicle.getId() == null) {
                        updatedVehicle.setUser(userDb);
                        newVehicles.add(updatedVehicle);
                    } else {
                        Optional<Vehicle> vehicleFound = userDb.getVehicle().stream()
                                .filter(vehicle -> vehicle.getId().equals(updatedVehicle.getId())).findFirst();
                        if (vehicleFound.isPresent()) {
                            updateExistingVehicle(vehicleFound.get(), updatedVehicle);
                        } else {
                            throw new ComplexValidationException("Vehicle update", "Invalid vehicle id. " + updatedVehicle.getId());
                        }
                    }
                }
            } else {
                for (Vehicle existingVehicle : userDb.getVehicle()) {
                    Optional<Vehicle> vehicleFound = user.getVehicle().stream()
                            .filter(vehicle -> vehicle.getId().equals(existingVehicle.getId())).findFirst();
                    if (vehicleFound.isPresent()) {
                        updateExistingVehicle(existingVehicle, vehicleFound.get());
                    } else {
                        deletedVehicles.add(existingVehicle);
                    }
                }
            }
            userDb.getVehicle().addAll(newVehicles);
            userDb.getVehicle().removeAll(deletedVehicles);
        }
    }

    private void updateExistingVehicle(Vehicle existingVehicle, Vehicle updatedVehicle) {
        if (updatedVehicle.getVehicleNo() != null) existingVehicle.setVehicleNo(updatedVehicle.getVehicleNo());
        if (updatedVehicle.getModel() != null) existingVehicle.setModel(updatedVehicle.getModel());
        if (updatedVehicle.getYear() != null) existingVehicle.setYear(updatedVehicle.getYear());
        if (updatedVehicle.getColor() != null) existingVehicle.setColor(updatedVehicle.getColor());
        if (updatedVehicle.getType() != null) existingVehicle.setType(updatedVehicle.getType());
        if (updatedVehicle.getDescription() != null) existingVehicle.setDescription(updatedVehicle.getDescription());
        if (updatedVehicle.getStatus() != null) existingVehicle.setStatus(updatedVehicle.getStatus());
        if (updatedVehicle.getBrand() != null) existingVehicle.setBrand(updatedVehicle.getBrand());
        updatedVehicle.setStatus(Status.ACTIVE);
    }

    /*@Transactional
    @Override
    public User update(User user) {
        Optional<User> userPersisted = userRepository.findById(user.getId());
        if (!userPersisted.isPresent()) {
            throw new ComplexValidationException("User update", "Invalid user id");
        } else {
            User userDb = userPersisted.get();

            // Update simple fields if they are not null
            if (user.getFirstName() != null) {
                userDb.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                userDb.setLastName(user.getLastName());
            }
            if (user.getContactNo() != null) {
                userDb.setContactNo(user.getContactNo());
            }
            if (user.getDateJoin() != null) {
                userDb.setDateJoin(user.getDateJoin());
            }
            if (user.getAge() != null) {
                userDb.setAge(user.getAge());
            }
            if (user.getGenderType() != null) {
                userDb.setGenderType(user.getGenderType());
            }
            if (user.getNic() != null) {
                userDb.setNic(user.getNic());
            }
            if (user.getNationality() != null) {
                userDb.setNationality(user.getNationality());
            }
            if (user.getImage() != null) {
                userDb.setImage(user.getImage());
            }
            if (user.getReligion() != null) {
                userDb.setReligion(user.getReligion());
            }
            if (user.getEmail() != null) {
                userDb.setEmail(user.getEmail());
            }

            // Update Vehicles if provided
            if (user.getVehicle() != null) {
                List<Vehicle> newVehicles = new ArrayList<>();
                List<Vehicle> deletedVehicles = new ArrayList<>();

                if (user.getVehicle().size() >= userDb.getVehicle().size()) {
                    for (Vehicle updatedVehicle : user.getVehicle()) {
                        if (updatedVehicle.getId() == null) {
                            newVehicles.add(updatedVehicle);
                        } else {
                            Optional<Vehicle> vehicleFound = userDb.getVehicle().stream()
                                    .filter(vehicle -> vehicle.getId().equals(updatedVehicle.getId()))
                                    .findFirst();
                            if (vehicleFound.isPresent()) {
                                updateExistingVehicle(vehicleFound.get(), updatedVehicle);
                            } else {
                                throw new ComplexValidationException("User update", "Invalid vehicle id: " + updatedVehicle.getId());
                            }
                        }
                    }
                } else {
                    for (Vehicle existingVehicle : userDb.getVehicle()) {
                        Optional<Vehicle> vehicleFound = user.getVehicle().stream()
                                .filter(vehicle -> vehicle.getId().equals(existingVehicle.getId()))
                                .findFirst();
                        if (vehicleFound.isPresent()) {
                            updateExistingVehicle(existingVehicle, vehicleFound.get());
                        } else {
                            deletedVehicles.add(existingVehicle);
                        }
                    }
                }

                if (!newVehicles.isEmpty()) {
                    if (userDb.getVehicle() != null) {
                        userDb.getVehicle().addAll(newVehicles);
                    } else {
                        userDb.setVehicle(newVehicles);
                    }
                }

                if (!deletedVehicles.isEmpty()) {
                    userDb.getVehicle().removeAll(deletedVehicles);
                }
            } else {
                userDb.getVehicle().clear();
            }

            return userRepository.save(userDb);
        }
    }

    // Helper method to update existing vehicle
    private void updateExistingVehicle(Vehicle existingVehicle, Vehicle updatedVehicle) {
        if (updatedVehicle.getVehicleNo() != null) {
            existingVehicle.setVehicleNo(updatedVehicle.getVehicleNo());
        }
        if (updatedVehicle.getModel() != null) {
            existingVehicle.setModel(updatedVehicle.getModel());
        }
        if (updatedVehicle.getYear() != null) {
            existingVehicle.setYear(updatedVehicle.getYear());
        }
        if (updatedVehicle.getColor() != null) {
            existingVehicle.setColor(updatedVehicle.getColor());
        }
        if (updatedVehicle.getType() != null) {
            existingVehicle.setType(updatedVehicle.getType());
        }
        if (updatedVehicle.getDescription() != null) {
            existingVehicle.setDescription(updatedVehicle.getDescription());
        }
        if (updatedVehicle.getBrand() != null) {
            existingVehicle.setBrand(updatedVehicle.getBrand());
        }
    }*/


    @Transactional
    @Override
    public User delete(Long id) {
        User user = userRepository.getReferenceById(id);
        if (user != null) {
            user.setStatus(Status.DELETED);
            return userRepository.save(user);
        }else {
            throw new ComplexValidationException("User retrieval", "Cannot find any User ");
        }
    }

    @Override
    public User logIn(User user) {
        User userPersisted = userRepository.findByEmail(user.getEmail());
        if (userPersisted != null) {
            if (passwordEncoder.matches(user.getPassWord(), userPersisted.getPassWord())) {
                userPersisted.setUserLogging(LocalDateTime.now());
                userRepository.save(userPersisted);
                userPersisted.getVehicle().size();
                return userPersisted;
            }
        }
        throw new ComplexValidationException(user.getEmail(), "User credentials Invalid");
    }

}
