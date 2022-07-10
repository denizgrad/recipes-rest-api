package com.dozen.recipes.controller;

import com.dozen.recipes.exception.RecipesException;
import com.dozen.recipes.model.RestErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.dozen.recipes.service.impl.JwtUserDetailsService;


import com.dozen.recipes.security.JwtTokenUtil;
import com.dozen.recipes.model.JwtRequest;
import com.dozen.recipes.model.JwtResponse;
import com.dozen.recipes.model.UserDto;

import javax.validation.Valid;

@RestController
@CrossOrigin
@AllArgsConstructor
public class JwtAuthenticationController extends AbstractController{

	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody final JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody @Valid final UserDto user) throws Exception {
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		if(userDetails == null){
			return ResponseEntity.ok(userDetailsService.save(user));
		}
		throw new RecipesException(RecipesException.ALREADY_EXISTS, "User");
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}