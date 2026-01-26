package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){

        Autor autor = new Autor();

        autor.setNome("Souza");
        autor.setNacionalidade("brasileiro");
        autor.setDataNascimento(LocalDate.of(2005,1,3));

        repository.save(autor);

    }

    @Test
    void listarLivrosAutor(){
        var id = UUID.fromString("4ed5ed93-34a5-4666-8038-d1b0aecea9d4");
        var autor = repository.findById(id).get();

        // buscar os livros do autor

        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);

    }
    @Test
    void salvarAutorComLivrosTest(){

        Autor a1 = new Autor();

        a1.setNome("Ana");
        a1.setNacionalidade("Americana");
        a1.setDataNascimento(LocalDate.of(2000,1,8));

        Livro livro = new Livro();

        livro.setTitulo("A casa de palha ");
        livro.setIsbn("8347427");
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setPreco(BigDecimal.valueOf(900));
        livro.setDataPublicacao(LocalDate.of(1950,11,10));
        livro.setAutor(a1);

        Livro l1 = new Livro();

        l1.setTitulo("Guerra Fria");
        l1.setIsbn("8888");
        l1.setGenero(GeneroLivro.CIENCIA);
        l1.setPreco(BigDecimal.valueOf(200));
        l1.setDataPublicacao(LocalDate.of(2000,10,10));
        l1.setAutor(a1);

        a1.setLivros(new ArrayList<>());
        a1.getLivros().add(livro);
        a1.getLivros().add(l1);

        repository.save(a1);

       // livroRepository.saveAll(a1.getLivros());

    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }


    @Test
    void atualizarAutorPorIdTest(){
        var id = UUID.fromString("1d325b74-4723-4783-9d41-6791b44cf0be");

        repository.updateautor(id);
    }

    @Test
    void deletarAutorPeloId(){
        var id = UUID.fromString("eebcf82f-b2c6-48ad-af30-311d66614ee5");

        repository.deleteautor(id);
    }

    @Test
    void buscarPorIdQueryTest(){
        var idEncontrado = UUID.fromString("1d325b74-4723-4783-9d41-6791b44cf0be");
        System.out.println(repository.buscandoPorId(idEncontrado));
    }

    @Test
    void buscarPorIdTest(){

        UUID idEncontrado = UUID.fromString
                ("754ce904-5d4f-4e3a-8520-12b92deb99f9");

        Autor autor = repository.findById(idEncontrado)
                .orElseThrow(
                        () -> new IllegalArgumentException("Autor n encontrado"));

        System.out.println(autor);

    }

    @Test
    public void atualizarTest(){

        UUID id = UUID.fromString("58843e8a-a0ac-43b0-aadb-6de22d097558");

        Autor autor = repository.findById(id).
                orElseThrow(() ->
                 new IllegalArgumentException("Autor n encontrado"));

        autor.setNacionalidade("holandes");

        repository.save(autor);

    }

    @Test
    public void deletarPorIdTest(){

        UUID id = UUID.fromString("58843e8a-a0ac-43b0-aadb-6de22d097558");

        repository.deleteById(id);
    }

    @Test
    void deletarTest(){

        var id = UUID.fromString("754ce904-5d4f-4e3a-8520-12b92deb99f9");

        Autor autor = repository.findById(id).
        orElseThrow(() -> new RuntimeException("Autor n encontrado"));

        repository.delete(autor);

    }

    @Test
    void contarTest(){
        System.out.println("Contagem autores "+ repository.count());
    }

}
