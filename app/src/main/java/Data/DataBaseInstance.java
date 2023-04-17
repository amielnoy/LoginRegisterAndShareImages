package Data;

public class DataBaseInstance
{
    private static DatabaseHelper globalDbInstance;
   public static DatabaseHelper getDatabaseInstance()
    {


        return globalDbInstance;
    }
}
