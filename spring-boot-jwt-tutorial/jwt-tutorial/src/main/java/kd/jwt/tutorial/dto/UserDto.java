package kd.jwt.tutorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import kd.jwt.tutorial.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  @NotNull
  @Size(min = 3, max = 50)
  private String username;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotNull
  @Size(min = 3, max = 100)
  private String password;

  @NotNull
  @Size(min = 3, max = 50)
  private String nickname;

  private Set<AuthorityDto> authorityDtoSet;

  public static UserDto from(User user) {
    if(user == null) return null;

    return UserDto.builder()
        .username(user.getUsername())
        .nickname(user.getNickname())
        .authorityDtoSet(user.getAuthorities().stream()
            .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
            .collect(Collectors.toSet()))
        .build();
  }

}