package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // cuida das regras de neócio e conexão com o banco de dados
public class SerieService {

    @Autowired // injeção de dependencia do repositiry dentro do service
    private SerieRepository repositorio;

    public List<SerieDTO> obterTodasAsSeries() {
        return converteDados(repositorio.findAll());

    }

    public List<SerieDTO> obterTop5Series() {
        // vamos mandar ele fazer um return converteDados() do que o repositório enviar para nó
        return converteDados(repositorio.findAll());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(repositorio.encontrarEpisodiosMaisRecentes());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repositorio.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse());
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = repositorio.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()// dentro da classe Sérei, busca pelo atributo episodio que referencia a classe Episodio
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
        return repositorio.obterEpisodiosPorTemporada(id, numero)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterSeriesPorCategoria(String nomeGenero) {
        Categoria categoria = Categoria.fromPortugues(nomeGenero); // converte do portugues para genero original antes de tratar os dados
        return converteDados(repositorio.findByGenero(categoria));
    }


    // Esse método converteDados() vai receber uma lista de objetos do tipo Serie e transformá-la numa lista de objetos do tipo SerieDTO.
    private List<SerieDTO> converteDados(List<Serie> serie) { // devolve um SerieDTO ao invés da classe Serie (Evita o loop) de uma classe chamando a outra no case das relações "@OneToMany"
        return serie.stream()
                // vamos criar uma nova SerieDTO com o ".map()" e o "new SerieDTO", e então passar os atributos da classe série para ele com "SerieDTO(s.getId(), s.get[...])"
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList()); // vamos coletar esses novos dados para outra lista
    }

}
