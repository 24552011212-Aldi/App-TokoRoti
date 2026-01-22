package service;

import java.util.List;

public interface IOperasiData<T> {
    // Basic CRUD operations
    void tambah(T data);
    void ubah(T data);
    void hapus(String id);
    T cari(String id);
    List<T> tampilSemua();
    
    // File operations
    void loadDataFromFile();
    void saveDataToFile();
}