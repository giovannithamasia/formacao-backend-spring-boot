package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;


    @Test
    void salvarTest(){

        Livro livro = new Livro();

        livro.setTitulo("Titanic");
        livro.setIsbn("327632");
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setDataPublicacao(LocalDate.of(1990,10,10));

        Autor autor = autorRepository.findById
                (UUID.fromString("1d325b74-4723-4783-9d41-6791b44cf0be"))
                .orElseThrow(() -> new RuntimeException("Sla"));

        livro.setAutor(autor);

        repository.save(livro);

    }
    @Test
    public void listarLivros() {
        List<Livro> lista = repository.findAll();
        lista.forEach(System.out::println);
    }



    @Test
    void salvarCascadeTest(){

        Livro livro = new Livro();

        livro.setTitulo("Branca de neve e os sete anoes ");
        livro.setIsbn("999999");
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setPreco(BigDecimal.valueOf(2000));
        livro.setDataPublicacao(LocalDate.of(1989,3,7));

        Autor autor = new Autor();

        autor.setNome("Disney");
        autor.setNacionalidade( "americano");
        autor.setDataNascimento(LocalDate.of(1970,10,10));

        livro.setAutor(autor);

        repository.save(livro);

    }

    @Test
    void buscarDataPorIntervalo(){

        LocalDate dataInicio = LocalDate.of(1950,11,10);

        LocalDate dataFinal = LocalDate.of(1989, 3, 7);

        List<Livro> query = repository.
                findByDataPublicacaoBetween(dataInicio, dataFinal);

        System.out.println(query);
    }

    @Test
    void EncontrararParte(){

        String parte = "Gu";

        List<Livro> encontrarParte = repository.findByTituloStartingWith(parte);

        System.out.println(encontrarParte);

    }

    @Test
    void listarTodosQueryTest(){
        List<Livro> livros = repository.listarLivros();
        livros.forEach(System.out::println);
    }

    @Test
    void listarGeneroPorPrecoTest(){
        List<String> listaGeneroPorPreco = repository.listarGeneroPorPreco();
        listaGeneroPorPreco.forEach(System.out::println);
    }

    @Test
    void ListarAutoresETitulosTest(){
        var lista = repository.listarAutorAndTitulo();
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosPorNome(){
       var lista =  repository.listarLivrosDeNome();
        lista.forEach(System.out::println);
    }

    @Test
    @Transactional
    public void buscarPorId(){
        
        var id = UUID.fromString("3ac454c4-bf4e-40bb-92ca-5ab757075773");

        Livro livro = repository.findById(id).orElse(null);

        System.out.println("Livro");
        System.out.println(livro);
        System.out.println("Titulo : ");
        System.out.println(livro.getTitulo());

        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void listarPorQueryTest(){
        GeneroLivro genero = GeneroLivro.CIENCIA;

        BigDecimal precoInicial = BigDecimal.valueOf(100);
        BigDecimal precoFinal = BigDecimal.valueOf(200);
        LocalDate dataInicio = LocalDate.of(1990,10,10);
        LocalDate dataFinal = LocalDate.of(2000,10,10);

        System.out.println(repository.listarLivrosQuery(genero,dataInicio,dataFinal,
                precoInicial,precoFinal));
    }

    @Test
    void buscarLivroPorGenero(){
        var busca = repository.TesteNamed(GeneroLivro.CIENCIA);
        busca.forEach(System.out::println);
    }
    @Test
    void buscarLivroPorGeneroPositional(){
        var busca = repository.buscarPorTitulo("Guerra Fria");
        busca.forEach(System.out::println);
    }

    @Test
    void deletarIsbnTest(){
        repository.deleteByIsbn("8347427");
    }



    @Test
    void atualizarPrecoTest(){

        var id = UUID.fromString("a4225bdf-7f81-4503-baa5-f59481deaad1");

        BigDecimal preco = BigDecimal.valueOf(500);

        repository.updatelivro(id,preco);
    }


    @Test
    void atualizarTest(){

        UUID idEncontrado = UUID.fromString
                ("7762ddb4-4e5f-462d-8e18-7f9936f339ac");

        Livro livro = repository.findById(idEncontrado)
                .orElseThrow(() -> new RuntimeException("Livro n encontrado"));


        UUID idAutor = UUID.fromString("68286618-7ff0-4050-9319-bfd5f86fe571");

        Autor autorEncontrado = autorRepository.findById(idAutor)
                .orElseThrow(() -> new RuntimeException("Autor n encontrado"));

        livro.setAutor(autorEncontrado);

        repository.save(livro);
    }

    @Test
    void deletarIdCascade(){

        UUID id = UUID.fromString("56cbd50e-2781-47c2-8293-9f04db1319d1");

        repository.deleteById(id);

    }


    @Test
    void buscarPorTitulo(){

        var titulo = "Guerra Fria";

        List<Livro> autor = repository.findByTitulo(titulo);

        System.out.println(autor);
    }
}