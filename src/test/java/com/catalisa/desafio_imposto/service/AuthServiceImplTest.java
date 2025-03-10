import static org.junit.jupiter.api.Assertions.*;
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Authentication authentication;


    @Test
    void testLoginSuccess() {
        // Arrange
        LoginDto loginDto = new LoginDto("testUser","testPassword");

        Usuario usuario = new Usuario();
        usuario.setUsername("testUser");

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        Mockito.when(usuarioRepository.findByUsername("testUser"))
                .thenReturn(Optional.of(usuario));
        Mockito.when(jwtTokenProvider.generateToken(authentication))
                .thenReturn("mockedToken");

        // Act
        String token = authServiceImpl.login(loginDto);

        // Assert
        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(usuarioRepository).findByUsername("testUser");
        Mockito.verify(jwtTokenProvider).generateToken(authentication);
        Assertions.assertEquals("mockedToken", token, "O token gerado deve ser igual ao esperado.");
    }

    @Test
    void testLoginUserNotFound() {
        // Arrange
        LoginDto loginDto = new LoginDto("nonExistentUser","testPassword");


        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        Mockito.when(usuarioRepository.findByUsername("nonExistentUser"))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            authServiceImpl.login(loginDto);
        });

        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(usuarioRepository).findByUsername("nonExistentUser");
        Assertions.assertEquals("Usuário não encontrado", exception.getMessage(), "A mensagem de erro deve ser 'Usuário não encontrado'.");
    }

    @Test
    void testLoginAuthenticationError() {
        // Arrange
        LoginDto loginDto = new LoginDto("testUser", "wrongPassword");
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Erro ao autenticar"));

        // Act & Assert
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            authServiceImpl.login(loginDto);
        });

        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Assertions.assertEquals("Erro ao autenticar", exception.getMessage(), "A mensagem de erro deve ser 'Erro ao autenticar'.");
    }
}