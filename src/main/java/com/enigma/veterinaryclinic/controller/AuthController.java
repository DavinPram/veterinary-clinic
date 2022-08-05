package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.entity.*;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.request.LoginRequest;
import com.enigma.veterinaryclinic.request.RegisterRequest;
import com.enigma.veterinaryclinic.response.LoginResponse;
import com.enigma.veterinaryclinic.response.RegisterResponse;
import com.enigma.veterinaryclinic.security.jwt.JwtUtils;
import com.enigma.veterinaryclinic.service.CustomerService;
import com.enigma.veterinaryclinic.service.RoleService;
import com.enigma.veterinaryclinic.service.UserService;
import com.enigma.veterinaryclinic.util.WebResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<WebResponse<?>> registerCustomer (@Valid @RequestBody RegisterRequest request){

            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            Customer customer = new Customer();
            customer.setName(request.getName());

            Set<String> roles = request.getRoles();
            Set<Role> roleSet = new HashSet<>();
            for (String role: roles) {
                Role role1 = roleService.create(role);
                roleSet.add(role1);
            }

            RegisterResponse customerCreate = userService.createCustomer(user,customer,roleSet);
            WebResponse<?> response = new WebResponse<>("Successfully Create New Customer",customerCreate);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<WebResponse<?>> login (@Valid @RequestBody LoginRequest request){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        Set<String> roles = new HashSet<>();
        for (GrantedAuthority authority : userDetail.getAuthorities()) {
            roles.add(authority.getAuthority());
        }

        LoginResponse loginResponse = new LoginResponse(jwt,roles);
        WebResponse<?> response = new WebResponse<>("Login Success",loginResponse);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

  @PostMapping("/register/operator")
  @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE')")
  @SecurityRequirement(name = "vetclinicapi")
  public ResponseEntity<WebResponse<?>> registerOperator(@Valid @RequestBody RegisterRequest request){

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Operator operator = new Operator();
        operator.setName(request.getName());

        Set<String> roles = request.getRoles();
        Set<Role> roleSet = new HashSet<>();
        for (String role: roles) {
          Role role1 = roleService.create(role);
          roleSet.add(role1);
        }

        RegisterResponse registerResponse = userService.createOperator(user, operator, roleSet);
        WebResponse<?> response = new WebResponse<>("Successfully Create New User", registerResponse);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
  }

  @PostMapping("/register/doctor")
  @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE','DOCTOR_ROLE')")
  @SecurityRequirement(name = "vetclinicapi")
  public ResponseEntity<WebResponse<?>> registerDoctor(@Valid @RequestBody RegisterRequest request){

    User user = new User();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    Doctor doctor = new Doctor();
    doctor.setName(request.getName());

    Set<String> roles = request.getRoles();
    Set<Role> roleSet = new HashSet<>();
    for (String role: roles) {
      Role role1 = roleService.create(role);
      roleSet.add(role1);
    }

    RegisterResponse registerResponse = userService.createDoctor(user, doctor, roleSet);
    WebResponse<?> response = new WebResponse<>("Successfully Create New User", registerResponse);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
  }

  @GetMapping("/info")
  @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<?>> loginedUser (Authentication authentication){
      if (authentication == null){
          throw new NotFoundException("No Logined User");
      } else {
          Object principal = authentication.getPrincipal();
          WebResponse<?> response = new WebResponse<>("Logined User : ", principal);

          return ResponseEntity.status(HttpStatus.OK)
                  .body(response);
      }

  }
}
