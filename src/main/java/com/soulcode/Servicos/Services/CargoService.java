package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {
    @Autowired
    CargoRepository cargoRepository;
    @Cacheable("cargosCache")
    public List<Cargo> mostrarTodosCargos() {
        return cargoRepository.findAll();
    }
    @Cacheable(value = "cargosCache", key = "#idCargo")
    public Cargo mostrarUmCargopeloId(Integer idCargo) {
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return cargo.orElseThrow();
    }
    @CachePut(value="cargoCaChe", key= "#cargo.idCargo")
    public Cargo cadastrarCargo(Cargo cargo) {
        cargo.setIdCargo(null);
        return cargoRepository.save(cargo);
    }

    @CachePut(value = "cargosCache", key = "#cargo.idCargo")
    public Cargo editarCargo(Cargo cargo){
        return cargoRepository.save(cargo);
    }

    @CacheEvict(value="cargoCache",key="#idCargo", allEntries = true)
    public void excluirCargo(Integer idCargo){
        cargoRepository.deleteById(idCargo);
    }

    public Cargo mostrarUmCargoPelaDescricao(String descricao) {
        Optional<Cargo> cargo = cargoRepository.findByDescricao(descricao);
        return cargo.orElseThrow();
    }


    public Cargo mostrarUmCargoPeloSalario(Double salario) {
        Optional<Cargo> cargo = cargoRepository.findBySalario(salario);
        return cargo.orElseThrow();
    }
}

