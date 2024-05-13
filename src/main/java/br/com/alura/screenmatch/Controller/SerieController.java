package br.com.alura.screenmatch.Controller;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController // Combina as anotações @Controller e @ResponseBody, indicando que cada método retorna um objeto serializado diretamente em JSON ou XML como resposta.
@RequestMapping("/series")
public class SerieController {

    @Autowired // injeção de dependencia da classe "SerieService" dentro do controller
    private SerieService servico;

    @GetMapping
    public List<SerieDTO> obterSeries() { // devolve um SerieDTO ao invés da classe Serie (Evita o loop) de uma classe chamando a outra no case das relações "@OneToMany"
        return servico.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series() {
        return servico.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return servico.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id) {
        return servico.obterPorId(id);
    }
}
