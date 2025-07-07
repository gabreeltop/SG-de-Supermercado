package br.com.supermercado.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.supermercado.dao.FuncionarioDAO;
import br.com.supermercado.model.Funcionario;
import br.com.supermercado.model.Funcionario.Cargo;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {

    @Mock
    private FuncionarioDAO funcionarioDAO;

    @InjectMocks
    private FuncionarioService funcionarioService;

    private Funcionario funcionarioExemplo;

    @BeforeEach
    void setUp() {
        funcionarioExemplo = new Funcionario();
        funcionarioExemplo.setId(1);
        funcionarioExemplo.setNome("Gerente Teste");
        funcionarioExemplo.setLogin("gerente");
        funcionarioExemplo.setSenha("123");
        funcionarioExemplo.setCargo(Cargo.GERENTE);
    }

    @Test
    void deveAutenticarComSucessoQuandoLoginESenhaEstaoCorretos() {
        when(funcionarioDAO.buscarPorLogin("gerente")).thenReturn(funcionarioExemplo);

        Funcionario resultado = funcionarioService.autenticar("gerente", "123");

        assertNotNull(resultado);
        assertEquals("gerente", resultado.getLogin());
    }

    @Test
    void naoDeveAutenticarComSenhaIncorreta() {
        when(funcionarioDAO.buscarPorLogin("gerente")).thenReturn(funcionarioExemplo);

        Funcionario resultado = funcionarioService.autenticar("gerente", "senha_errada");

        assertNull(resultado);
    }

    @Test
    void naoDeveAutenticarUsuarioInexistente() {
        when(funcionarioDAO.buscarPorLogin("usuario_invalido")).thenReturn(null);

        Funcionario resultado = funcionarioService.autenticar("usuario_invalido", "123");

        assertNull(resultado);
    }
}