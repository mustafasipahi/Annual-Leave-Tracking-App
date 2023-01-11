package com.dto;

import com.validator.ValidPhone;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    private static final long serialVersionUID = -8857065641344829978L;

    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @ValidPhone
    private String phone;
    private Date createdDate;
}
