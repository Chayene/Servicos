package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//interface de Funcionario e subclasse de JPa (traz todos os 13 metodos e os serviços que vaqmos fazer como os funcionariosServices)
// exclui, editar, cria funcionario, faz o crud
public interface FuncionarioRepository extends JpaRepository <Funcionario, Integer>{
    Optional<Funcionario> findByEmail(String email);
    List<Funcionario> findByCargo(Optional<Cargo> cargo);

    // Optional<Funcionario> findByNome (String nome);
    //Optional<Funcionario> findByNomeAndEmail(String nome, String email);
}
