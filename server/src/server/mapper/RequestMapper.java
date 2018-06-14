package server.mapper;

import java.util.List;

public class RequestMapper implements IMapper {
    @Override
    public boolean insert(Object o) {
        return false;
    }

    @Override
    public boolean delete(String condition) {
        return false;
    }

    @Override
    public boolean update(Object o) {
        return false;
    }

    @Override
    public Object get(String condition) {
        return null;
    }

    @Override
    public List gets(String condition) {
        return null;
    }
}
