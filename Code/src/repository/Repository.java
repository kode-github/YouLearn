package repository;

import java.sql.SQLException;
import java.util.Collection;

public interface Repository<T> {
	
	void add(T item) throws SQLException;

    void add(Iterable<T> items) throws SQLException ;

    void update(T item) throws SQLException;

    void remove(T item) throws SQLException;

    void remove(Specification specification) throws SQLException;

    Collection<T> query(Specification specification) throws SQLException;

}
