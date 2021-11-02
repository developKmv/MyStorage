package ru.develop.dao;

import ru.develop.entity.Storage;

import java.util.List;

public interface StorageDAO {
    public void saveStorage(Storage st);
    public List<Storage> getAllStorage();
    public Storage getStorage(int id);
}
