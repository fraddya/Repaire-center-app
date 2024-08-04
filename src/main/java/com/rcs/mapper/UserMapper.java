package com.rcs.mapper;


import com.rcs.domain.User;
import com.rcs.domain.criteria.UserCriteria;
import com.rcs.dto.request.user.UserCreateRequest;
import com.rcs.dto.request.user.UserSearchRequest;
import com.rcs.dto.request.user.UserUpdateRequest;
import com.rcs.dto.response.user.UserCreateResponse;
import com.rcs.dto.response.user.UserSearchResponse;
import com.rcs.enums.Status;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",imports = {Status.class})
public interface UserMapper {

    User mapToUser(UserCreateRequest request);

    User mapToUserUpdate(UserUpdateRequest request);

    UserCriteria mapToCriteria(UserSearchRequest request);

    List<UserSearchResponse> mapToSearchResponse(List<User> content);

    UserSearchResponse mapToUserViewResponse(User user);

    UserCreateResponse mapToUpdateResponse(User userUpdate);

}
