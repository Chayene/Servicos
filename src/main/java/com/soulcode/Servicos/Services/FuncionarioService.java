package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.CargoRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import com.soulcode.Servicos.Services.Exceptions.DataIntegrityViolationException;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

// CRUD (Create, Read, Update, Delete) funcionario, classe de serviço
//quando se fala em servilo estamos falando dos métodos do CRUD da tabela
@Service
public class FuncionarioService {
    // primeirp serviço na tabela de funcionarios vai ser a leitura de todos os funcionarios cadastrados
    //findAll-> método do spring Data JPA-> busca todos os registro de uma tabela
    @Autowired // injeção de depedencia para usar os metodos jpa da outra classe
     FuncionarioRepository funcionarioRepository; //criando um classe atraves de um objeto
    @Autowired
    CargoRepository cargoRepository;

    // metodos
    public List<Funcionario> mostrarTodosFuncionario() {
        return funcionarioRepository.findAll();
    }

    // vamos criar mais um serviço relacionado ao funcionario
    // criar um serviço de buscar apenas 1 funcionario pelo seu id (chave primaria)
    public Funcionario mostrarUmFuncionarioPeloId(Integer idFuncionario) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return funcionario.orElseThrow(
                ()-> new EntityNotFoundException("Funcionario não cadastrado: "+ idFuncionario)
        );
    }

    public Funcionario mostrarUmFuncionarioPeloEmail(String email) {
        Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);
        return funcionario.orElseThrow();
    }

    public List<Funcionario>mostrarTodosFuncionariosDeUmCargo(Integer idCargo){
        Optional<Cargo> cargo= cargoRepository.findById(idCargo);
      return funcionarioRepository.findByCargo(cargo);
 }

//    public Funcionario cadastrarFuncionario(Funcionario funcionario, Integer idCargo) {
//        funcionario.setIdFuncionario(null);
//        Optional<Cargo> cargo= cargoRepository.findById(idCargo);
//        funcionario.setCargo(cargo.get());
//        return funcionarioRepository.save(funcionario);
//    }

    public Funcionario cadastrarFuncionario(Funcionario funcionario, Integer idCargo){
        try {
            Cargo cargo = cargoRepository.findById(idCargo).get();
            funcionario.setCargo(cargo);
            return funcionarioRepository.save(funcionario);
        }catch (Exception e){
            throw new DataIntegrityViolationException("Erro ao cadastrar funcionário");
        }

    }
    public void excluirFuncionario(Integer id_Funcionario) {
// mostrarUmFuncionarioPeloId(id_Funcionario);
        funcionarioRepository.deleteById(id_Funcionario);
    }

    public Funcionario editarFuncionario(Funcionario funcionario) {

        return funcionarioRepository.save(funcionario);
    }

    public Funcionario salvarFoto(Integer idFuncionario, String caminhoFoto) {
        Funcionario funcionario = mostrarUmFuncionarioPeloId(idFuncionario);
        funcionario.setFoto(caminhoFoto);
        return funcionarioRepository.save(funcionario);
    }

//    public Funcionario atribuirCargo(Integer idCargo, Funcionario funcionario) {
//        Optional <Cargo>  cargo=  cargoRepository.findById(idCargo);
//        funcionario.setCargo(cargo.get());
//        return funcionarioRepository.save(funcionario)
//    }



}

