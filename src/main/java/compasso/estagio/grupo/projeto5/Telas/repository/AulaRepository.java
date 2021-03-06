package compasso.estagio.grupo.projeto5.Telas.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import compasso.estagio.grupo.projeto5.Telas.model.Aula;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.model.Tipo;

public interface AulaRepository extends JpaRepository<Aula, Long> {

	Page<Aula> findAll(Pageable page);

	@Query(value = "select * from aula a inner join perfil_aulas p on p.aulas_id = a.id inner join perfil pe on pe.id = p.perfil_id where email = :email and a.tipo = :tipo", nativeQuery = true)
	List<Aula> findByTipo(int tipo, String email);

	@Query("SELECT a FROM Aula a INNER JOIN a.alunos al WHERE al IN (:aluno) and a.tipo = :tipo order by a.id desc")
	List<Aula> findByAlunoAndTipo(@Param("aluno") Perfil perfil,@Param("tipo") Tipo tipo);

	@Query("select a from Aula a where tipo = :tipo and id = :id")
	Aula findByAula(@Param("id") Long id, @Param("tipo") Tipo tipo);

	@Query(value = "select * from aula a inner join perfil_aulas p on p.aulas_id = a.id inner join perfil pe on pe.id = p.perfil_id where email = :email", nativeQuery = true)
	List<Aula> getAulaCadastrada(String email);
	
	@Query(value = "select * from aula a inner join perfil_aulas p on p.aulas_id = a.id inner join perfil pe on pe.id = p.perfil_id where a.titulo = :titulo", nativeQuery = true)
	Aula getAulaCadastradaTitulo(String titulo);

	Aula findByTitulo(String titulo);

	@Query("SELECT a FROM Aula a INNER JOIN a.alunos al WHERE al IN (:aluno) order by a.id desc")
	List<Aula> findByAlunos(@Param("aluno") Perfil perfil);

	void deleteByTitulo(String titulo);

}
