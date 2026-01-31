package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    List<Autor> findByNome(String nome);

    List<Autor> findByNacionalidade(String nacionalidade);

    List<Autor> findByNomeAndNacionalidade(String nome,String nacionalidade);

    @Query(" select a from Autor a where a.id = ?1")
    Autor buscandoPorId (UUID idProcurado);

    @Modifying
    @Transactional
    @Query("""
        update Autor
        set nome = 'Paulinho'
        where id = ?1
       """)
    void updateautor(UUID idProcurar);

    @Modifying
    @Transactional
    @Query(" delete from Autor where id = ?1")
    void deleteautor(UUID idDeletar);
}
