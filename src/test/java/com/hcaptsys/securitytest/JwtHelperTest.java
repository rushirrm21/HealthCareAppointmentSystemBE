package com.hcaptsys.securitytest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import com.hcaptsys.security.JwtHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

class JwtHelperTest {

	public JwtHelper jwtHelper;
	public UserDetails userDetails;
	public String token;
	public String username;
	public Date expirationDate;

	@BeforeEach
	void setUp() {
		jwtHelper = new JwtHelper();
		userDetails = mock(UserDetails.class);
		token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJydXNoaXJybTIxQGdtYWlsLmNvbSIsImlhdCI6MTY5NDY1OTA4MSwiZXhwIjoxNjk0Njc3MDgxfQ.7CkvJRMcybPpCNO76wWSCcfGnmSOdpr0lMLCzwMP296f4HRCEGM-fU1YD5WfKoFEAF3DPXEJDG8-P6fK7HMC9A";
		username = "rushirrm21@gmail.com";
		expirationDate = new Date(System.currentTimeMillis() + JwtHelper.JWT_TOKEN_VALIDITY * 1000);
	}

	@Test
	void getUsernameFromToken_ValidToken_ReturnsUsername() {
		String extractedUsername = jwtHelper.getUsernameFromToken(token);
		assertEquals(username, extractedUsername);
	}

	@Test
	void getClaimFromToken_ValidToken_ReturnsClaimValue() {
		String claimName = "sub";
		String claimValue = "rushirrm21@gmail.com";
		String extractedClaimValue = jwtHelper.getClaimFromToken(token, claims -> claims.get(claimName, String.class));
		assertEquals(claimValue, extractedClaimValue);
	}

	@Test
    void validateToken_ValidTokenAndUserDetails_ReturnsTrue() {
        when(userDetails.getUsername()).thenReturn(username);
        assertTrue(jwtHelper.validateToken(token, userDetails));
    }

	@Test
	void validateToken_TokenWithDifferentUsername_ReturnsFalse() {
		String differentUsername = "user";
		when(userDetails.getUsername()).thenReturn(differentUsername);
		assertFalse(jwtHelper.validateToken(token, userDetails));
	}

	@Test
    void generateToken_ValidUserDetails_ReturnsToken() {
        when(userDetails.getUsername()).thenReturn(username);
        String generatedToken = jwtHelper.generateToken(userDetails);
        Jws<Claims> parsedToken = parseToken(generatedToken);
        assertEquals(username, parsedToken.getBody().getSubject());
    }

	private Jws<Claims> parseToken(String token) {
		JwtParser parser = Jwts.parser().setSigningKey(jwtHelper.secret);
		return parser.parseClaimsJws(token);
	}
}