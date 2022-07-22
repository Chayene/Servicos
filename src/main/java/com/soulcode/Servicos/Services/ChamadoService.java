package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Models.StatusChamado;
import com.soulcode.Servicos.Repositories.ChamadoRepository;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    ChamadoRepository chamadoRepository;

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    FuncionarioRepository funcionarioRepository;

    // findAll (método da Spring Data) - busca todos os registros
    public List<Chamado> mostrarTodosChamados(){
        return chamadoRepository.findAll();	}

    // findById - busca um registro pela sua chave primária
    public Chamado mostrarUmChamado(Integer idChamado) {
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        return chamado.orElseThrow();
    }
    public List<Chamado> buscarChamadosPeloCliente(Integer idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        return chamadoRepository.findByCliente(cliente);
    }
    public List<Chamado> buscarChamadosPeloFuncionario(Integer idFuncionario){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return chamadoRepository.findByFuncionario(funcionario);
    }
    public List<Chamado> buscarChamadosPelosStatus(String status){
        return chamadoRepository.findByStatus(status);
    }

    public List<Chamado> buscarPorIntervaloData(Date data1, Date data2){
        return chamadoRepository.findByIntervaloData(data1,data2);
    }

    // do Crude esse é o creat.
    public Chamado cadastrarChamado(Chamado chamado, Integer idCliente){

        // regra 3 - Atribuição do status recebido pra o chamado que esta sendo cadastrado
        chamado.setStatus(StatusChamado.RECEBIDO);
        // regra 2 - dizer que ainda não atribuimos esse chamado pra nenhum funcionario
        chamado.setFuncionario(null);
        // regra 1 - buscando os dados do cliente dono do chamado
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        chamado.setCliente(cliente.get());
        return chamadoRepository.save(chamado);
    }
    //método para exclusão de um chamado
    public void excluirChamado (Integer idChamado){
        chamadoRepository.deleteById(idChamado);
    }
    // Método para editar um chamado
   // no momento da edição de um chamado devemos preservaroclienteeofuncionário desse chamado
  // vamos editar os dados do chamado,mas contiuamos com os dados do clienteeos dados do funcionário

    public Chamado editarChamado(Chamado chamado, Integer idChamado){
        //instanciamos aqui um objeto do tipo chamado para guardar os dados do chamado
        Chamado chamadoSemAsNovasAlteracoes= mostrarUmChamado(idChamado);
        Funcionario funcionario= chamadoSemAsNovasAlteracoes.getFuncionario();
        Cliente cliente =chamadoSemAsNovasAlteracoes.getCliente();
        chamado.setCliente(cliente);
        chamado.setFuncionario(funcionario);
        return chamadoRepository.save(chamado);
    }

    // metodo para atribuir um funcionario para um determiando chamado
    // ou trocar o funcionario de determinado chamado
    //-> regra no momento em que um determinado chamado é atribuido a um funcionario
    // o status do chamado precisa ser alterado para atribuido
    public Chamado atribuirFuncionario(Integer idChamado, Integer idFuncionario){
        //buscar os dados do funcionario que vai ser atribuido a esse chamado
        Optional<Funcionario> funcionario= funcionarioRepository.findById(idFuncionario);
        //buscar o chamado pelo o qual vai ser especificado o funcionario escolhido
        Chamado chamado=mostrarUmChamado(idChamado);
        chamado.setFuncionario(funcionario.get());
        chamado.setStatus(StatusChamado.ATRIBUIDO);
        return chamadoRepository.save(chamado);
    }
    //metodo para modificar o status de um chamado
    public Chamado modificarStatus(Integer idChamado,String status){
        Chamado chamado = mostrarUmChamado(idChamado);
        if(chamado.getFuncionario()!=null){
            switch (status) {
                case "ATRIBUIDO": {
                    chamado.setStatus(StatusChamado.ATRIBUIDO);
                    break;
                }
                case "CONCLUIDO": {
                    chamado.setStatus(StatusChamado.CONCLUIDO);
                    break;
                }
                case "ARQUIVADO": {
                    chamado.setStatus(StatusChamado.ARQUIVADO);
                    break;
                }
            }
            switch (status) {
                case "RECEBIDO":
                    chamado.setStatus(StatusChamado.RECEBIDO);
                    break;
            }
        }
        return chamadoRepository.save(chamado);
    }
}

