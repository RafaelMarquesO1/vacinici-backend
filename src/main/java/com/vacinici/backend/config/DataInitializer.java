package com.vacinici.backend.config;

import com.vacinici.backend.entity.*;
import com.vacinici.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private VacinaRepository vacinaRepository;
    @Autowired private LocalVacinacaoRepository localRepository;
    @Autowired private HistoricoVacinacaoRepository historicoRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario funcionario1 = new Usuario();
            funcionario1.setNomeCompleto("Maria Silva");
            funcionario1.setEmail("maria.silva@ubs.gov.br");
            funcionario1.setTelefone("11987654324");
            funcionario1.setCpf("101.101.101-01");
            funcionario1.setDataNascimento(LocalDate.of(1980, 5, 10));
            funcionario1.setGenero("Feminino");
            funcionario1.setTipoUsuario("Funcionario");
            funcionario1.setCargo("Enfermeira");
            funcionario1.setSenha("maria123456");
            usuarioRepository.save(funcionario1);

            Usuario funcionario2 = new Usuario();
            funcionario2.setNomeCompleto("Stephanie Santos");
            funcionario2.setEmail("stephanie.santos@ubs.gov.br");
            funcionario2.setTelefone("11987654325");
            funcionario2.setCpf("102.102.102-02");
            funcionario2.setDataNascimento(LocalDate.of(1985, 9, 15));
            funcionario2.setGenero("Feminino");
            funcionario2.setTipoUsuario("Funcionario");
            funcionario2.setCargo("Enfermeira");
            funcionario2.setSenha("stephanie123456");
            usuarioRepository.save(funcionario2);

            Usuario admin = new Usuario();
            admin.setNomeCompleto("Administrador Sistema");
            admin.setEmail("admin@vacinici.com");
            admin.setTelefone("11999999999");
            admin.setCpf("000.000.000-00");
            admin.setDataNascimento(LocalDate.of(1990, 1, 1));
            admin.setGenero("Masculino");
            admin.setTipoUsuario("Funcionario");
            admin.setCargo("Administrador");
            admin.setSenha("admin123456");
            usuarioRepository.save(admin);

            Usuario paciente1 = new Usuario();
            paciente1.setNomeCompleto("James Moraes");
            paciente1.setEmail("jamesmoraes@gmail.com");
            paciente1.setTelefone("11987654321");
            paciente1.setCpf("111.222.333-44");
            paciente1.setDataNascimento(LocalDate.of(1992, 8, 25));
            paciente1.setGenero("Masculino");
            paciente1.setTipoUsuario("Paciente");
            paciente1.setSenha("james123456");
            usuarioRepository.save(paciente1);

            Usuario paciente2 = new Usuario();
            paciente2.setNomeCompleto("Ana Carolina Silva");
            paciente2.setEmail("ana.silva@email.com");
            paciente2.setTelefone("11987654322");
            paciente2.setCpf("222.333.444-55");
            paciente2.setDataNascimento(LocalDate.of(1990, 5, 15));
            paciente2.setGenero("Feminino");
            paciente2.setTipoUsuario("Paciente");
            paciente2.setSenha("ana123456");
            usuarioRepository.save(paciente2);

            Usuario paciente3 = new Usuario();
            paciente3.setNomeCompleto("Bruno Costa Lima");
            paciente3.setEmail("bruno.lima@email.com");
            paciente3.setTelefone("11987654323");
            paciente3.setCpf("333.444.555-66");
            paciente3.setDataNascimento(LocalDate.of(1985, 11, 20));
            paciente3.setGenero("Masculino");
            paciente3.setTipoUsuario("Paciente");
            paciente3.setSenha("bruno123456");
            usuarioRepository.save(paciente3);
        }

        if (vacinaRepository.count() == 0) {
            Vacina vacina1 = new Vacina();
            vacina1.setNome("COVID-19");
            vacina1.setFabricante("Pfizer");
            vacina1.setDescricao("Vacina contra o coronavírus SARS-CoV-2");
            vacina1.setDosesRecomendadas(2);
            vacina1.setCategoria("obrigatória");
            vacinaRepository.save(vacina1);

            Vacina vacina2 = new Vacina();
            vacina2.setNome("Influenza");
            vacina2.setFabricante("Butantan");
            vacina2.setDescricao("Vacina contra Influenza");
            vacina2.setDosesRecomendadas(1);
            vacina2.setCategoria("sazonal");
            vacinaRepository.save(vacina2);

            Vacina vacina3 = new Vacina();
            vacina3.setNome("Febre Amarela");
            vacina3.setFabricante("Bio-Manguinhos");
            vacina3.setDescricao("Vacina contra Febre Amarela");
            vacina3.setDosesRecomendadas(1);
            vacina3.setCategoria("obrigatória");
            vacinaRepository.save(vacina3);

            Vacina vacina4 = new Vacina();
            vacina4.setNome("Tríplice Viral");
            vacina4.setFabricante("GSK");
            vacina4.setDescricao("Vacina contra Sarampo, Caxumba e Rubéola");
            vacina4.setDosesRecomendadas(2);
            vacina4.setCategoria("obrigatória");
            vacinaRepository.save(vacina4);

            Vacina vacina5 = new Vacina();
            vacina5.setNome("BCG");
            vacina5.setFabricante("Fundação Ataulpho de Paiva");
            vacina5.setDescricao("Previne formas graves de Tuberculose");
            vacina5.setDosesRecomendadas(1);
            vacina5.setCategoria("obrigatória");
            vacinaRepository.save(vacina5);
        }

        if (localRepository.count() == 0) {
            LocalVacinacao local1 = new LocalVacinacao();
            local1.setNome("UBS Central");
            local1.setEndereco("Av. Principal, 123");
            local1.setCidade("São Paulo");
            local1.setEstado("SP");
            local1.setTipo("posto de saúde");
            localRepository.save(local1);

            LocalVacinacao local2 = new LocalVacinacao();
            local2.setNome("Hospital Municipal");
            local2.setEndereco("Rua da Saúde, 456");
            local2.setCidade("Rio de Janeiro");
            local2.setEstado("RJ");
            local2.setTipo("hospital");
            localRepository.save(local2);

            LocalVacinacao local3 = new LocalVacinacao();
            local3.setNome("Clínica Vacina Fácil");
            local3.setEndereco("Rua Comercial, 789");
            local3.setCidade("Belo Horizonte");
            local3.setEstado("MG");
            local3.setTipo("clínica");
            localRepository.save(local3);
        }
        System.out.println("Dados inicializados!");
    }
}