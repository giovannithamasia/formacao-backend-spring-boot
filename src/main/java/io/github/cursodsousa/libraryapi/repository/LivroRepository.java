package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo (String titulo);

    List<Livro> findByDataPublicacaoBetween (LocalDate inicio, LocalDate fim);

    List<Livro> findByTituloStartingWith (String parte);

    @Query("select l from Livro l")
    List<Livro> listarLivros();

    /**
     * select l.genero from livro l order by l.preco desc;
     */
    @Query("select l.genero from Livro l order by l.preco desc")
    List<String> listarGeneroPorPreco();

    /**
     * select a.nome , l.titulo from autor a
     * join livro l on a.id = l.id_autor;
     */
    @Query("select a.nome,l.titulo from Livro l join l.autor a")
    List<String> listarAutorAndTitulo();

    /**
     * select l
     * from livro l
     * join l.autor a
     * where a.nome = 'Ana'
     * order by l.preco desc
     */
    @Query("""
        select l
        from Livro l 
        join l.autor a
        where a.nome = 'Ana'
        order by l.preco desc
        """)
    List<Livro> listarLivrosDeNome();

    @Query("select l.genero from Livro l where l.genero = :data")
    List<String> TesteNamed(@Param("data") GeneroLivro generoLivro);

    @Query("select l from Livro l where l.titulo = ?1")
    List<Livro> buscarPorTitulo(String titulo);

    @Modifying
    @Transactional
    @Query(" delete from Livro where isbn = ?1")
    void deleteByIsbn (String isbn );

    @Modifying
    @Transactional
    @Query("""
        update Livro 
        set preco = ?2 
        where id = ?1
        """)
    void updatelivro (UUID id,BigDecimal preco);


    @Query("""
    select l from Livro l
    join  l.autor a 
    where l.genero = ?1 
    and l.dataPublicacao between ?2 and ?3
    and l.preco between ?4 and ?5
    """)
    List<Livro> listarLivrosQuery(GeneroLivro genero,LocalDate inicio,
                                  LocalDate fim , BigDecimal precoInicial ,
                                  BigDecimal precoFinal);

    boolean existsByAutor(Autor autor);
}
