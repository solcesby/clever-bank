package space.zmok.repository;

import java.util.List;

public interface BaseRepository<E, ID> {

    E save(E entity);

    E findById(ID entityId);

    List<E> findAll();

}
