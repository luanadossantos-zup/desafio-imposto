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

    
}