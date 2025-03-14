package com.catalisa.desafio_imposto.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationExceptionTest {

    @Test
    public void testAuthenticationExceptionMessage() {
        // Arrange
        String expectedMessage = "Authentication failed";

        // Act
        AuthenticationException exception = new AuthenticationException(expectedMessage);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }
}