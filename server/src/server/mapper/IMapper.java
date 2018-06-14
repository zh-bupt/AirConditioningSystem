package server.mapper;

import java.util.List;

public interface IMapper {

    boolean insert(Object o);
    boolean delete(String condition);
    boolean update(Object o);
    Object get(String condition);
    List gets(String condition);
}
