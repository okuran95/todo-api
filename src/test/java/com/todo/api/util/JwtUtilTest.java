package com.todo.api.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.todo.api.constant.Constants;
import com.todo.api.constant.TokenProperties;
import com.todo.api.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

public class JwtUtilTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private JwtUtil jwtUtil;

    @InjectMocks
    private TokenProperties tokenProperties;

    @BeforeEach
    public void setUp() {
        tokenProperties = new TokenProperties();
        tokenProperties.setSecret("mysecretkey");
        jwtUtil = new JwtUtil(tokenProperties);
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreateToken_Success() {
        User user = new User("test@example.com", "John", "Doe", "");

        String token = jwtUtil.createToken(user);

        assertNotNull(token);

        Claims claims = Jwts.parser().setSigningKey(tokenProperties.getSecret()).parseClaimsJws(token).getBody();
        assertEquals("test@example.com", claims.getSubject());
        assertEquals("John", claims.get("firstName"));
        assertEquals("Doe", claims.get("lastName"));
    }

    @Test
    public void testResolveToken_Success() {
        when(request.getHeader(Constants.TOKEN_HEADER)).thenReturn("Bearer testtoken");
        String token = jwtUtil.resolveToken(request);
        assertEquals("testtoken", token);
    }

    @Test
    public void testResolveToken_NoToken() {
        when(request.getHeader(Constants.TOKEN_HEADER)).thenReturn(null);
        String token = jwtUtil.resolveToken(request);
        assertNull(token);
    }

    @Test
    public void testValidateClaims_ValidExpiration() {
        Claims claims = mock(Claims.class);
        Date futureDate = new Date(System.currentTimeMillis() + 1000);
        when(claims.getExpiration()).thenReturn(futureDate);

        boolean isValid = jwtUtil.validateClaims(claims);

        assertTrue(isValid);
    }

    @Test
    public void testValidateClaims_ExpiredExpiration() {
        Claims claims = mock(Claims.class);
        Date pastDate = new Date(System.currentTimeMillis() - 1000);
        when(claims.getExpiration()).thenReturn(pastDate);

        boolean isValid = jwtUtil.validateClaims(claims);

        assertFalse(isValid);
    }

    @Test
    public void testGetEmail() {

        Claims claims = Jwts.claims().setSubject("test@example.com");

        String email = jwtUtil.getEmail(claims);

        assertEquals("test@example.com", email);
    }

    @Test
    public void testGetEmailFromToken_InvalidToken() {
        String token = "InvalidToken";
        token = jwtUtil.getEmailFromToken(token);
        assertNull(token);
    }

    @Test
    public void testGetEmailFromToken_NullToken() {
        String token = null;
        token = jwtUtil.getEmailFromToken(token);
        assertNull(token);
    }

}
