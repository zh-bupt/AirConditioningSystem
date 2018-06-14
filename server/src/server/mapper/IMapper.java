package server.mapper;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.util.List;

public interface IMapper {

    boolean insert(Object o) throws SQLServerException;
    boolean delete(String condition);
    boolean update(Object o);
    Object get(String condition);
    List gets(String condition);
}
