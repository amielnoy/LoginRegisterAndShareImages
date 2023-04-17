package Data;

public class DataBaseInstance
{
   private static DatabaseHelper globalDbInstance;
   public static DatabaseHelper getDatabaseInstance()
    {
        return globalDbInstance;
    }

    public void setDatabaseInstance(DatabaseHelper db)
    {
        DataBaseInstance.globalDbInstance=db;
    }
}
