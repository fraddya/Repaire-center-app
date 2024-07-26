package com.rcs.controller;


import com.rcs.domain.User;
import com.rcs.domain.base.PagingListResponseWrapper;
import com.rcs.domain.base.SingleItemResponseWrapper;
import com.rcs.domain.criteria.UserCriteria;
import com.rcs.dto.request.user.UserCreateRequest;
import com.rcs.dto.request.user.UserSearchRequest;
import com.rcs.dto.response.user.UserCreateResponse;
import com.rcs.dto.response.user.UserSearchResponse;
import com.rcs.mapper.UserMapper;
import com.rcs.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@Schema(name = "EmployeeController", description = "create/search/view/update/delete")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("${app.endpoint.userCreate}")
    public ResponseEntity<SingleItemResponseWrapper<UserCreateResponse>> create(
            @Validated @RequestBody UserCreateRequest request) {

        User user = userMapper.mapToUser(request);

        User user1 = userService.save(user);

        UserCreateResponse response = userMapper.mapToUpdateResponse(user1);

        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @PutMapping("${app.endpoint.userUpdate}")
    public ResponseEntity<SingleItemResponseWrapper<UserCreateResponse>> update(
            @PathVariable Long id, @Validated @RequestBody UserCreateRequest request) {

        User employee = userMapper.mapToUser(request);

        employee.setId(id);
        User updateEmployee = userService.update(employee);

        UserCreateResponse response = userMapper.mapToUpdateResponse(updateEmployee);

        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.userSearch}")
    public ResponseEntity<PagingListResponseWrapper<UserSearchResponse>> retrieve(
            @Validated UserSearchRequest request) {

        UserCriteria criteria = userMapper.mapToCriteria(request);

        Page<User> results = userService.search(criteria);

        List<UserSearchResponse> responses = userMapper.mapToSearchResponse(results.getContent());

        PagingListResponseWrapper.Pagination pagination =
                new PagingListResponseWrapper.Pagination(
                        results.getNumber() + 1,
                        results.getSize(),
                        results.getTotalPages(),
                        results.getTotalElements());

        return new ResponseEntity<>(new PagingListResponseWrapper<>(responses, pagination), HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.userView}")
    public ResponseEntity<SingleItemResponseWrapper<UserSearchResponse>> retrieve(
            @PathVariable Long id) {
        User employee = userService.retrieve(id);

        UserSearchResponse response = userMapper.mapToUserViewResponse(employee);

        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

    @DeleteMapping("${app.endpoint.userDelete}")
    public ResponseEntity<SingleItemResponseWrapper<UserCreateResponse>> delete(
            @PathVariable Long id) {

        User employee = userService.delete(id);

        UserCreateResponse response = new UserCreateResponse();

        if (employee != null) {
            response = userMapper.mapToUpdateResponse(employee);
        }
        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }
}
