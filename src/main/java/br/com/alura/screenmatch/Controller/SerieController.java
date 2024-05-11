package br.com.alura.screenmatch.Controller;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {

    @Autowired
    private SerieRepository repositorio;

    @GetMapping("/series")
    public List<SerieDTO> obterSeries() { // devolve um SerieDTO ao invés da classe Serie (Evita o loop) de uma classe chamando a outra no case das relações "@OneToMany"
        return repositorio.findAll()
                .stream()
                // vamos criar uma nova SerieDTO com o ".map()" e o "new SerieDTO", e então passar os atributos da classe série para ele com "SerieDTO(s.getId(), s.get[...])"
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList()); // vamos coletar esses novos dados para outra lista
    }
}
