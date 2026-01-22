package data;

/**
 * Class DataStorage
 * Singleton accessor untuk DataRepository
 * Menyediakan akses terpusat ke semua operasi data persistence
 */
public class DataStorage {
    private static final DataRepository repository;
    
    static {
        repository = new DataRepository();
    }
    
    private DataStorage() {
        // Private constructor untuk mencegah instantiation
    }
    
    /**
     * Get instance dari DataRepository
     */
    public static DataRepository getRepository() {
        return repository;
    }
    
    /**
     * Load semua data dari file
     */
    public static void loadAllData() {
        repository.loadDataFromFile();
    }
    
    /**
     * Save semua data ke file
     */
    public static void saveAllData() {
        repository.saveDataToFile();
    }
}

