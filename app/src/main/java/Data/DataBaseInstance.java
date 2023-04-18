package Data;

import Model.User;

public class DataBaseInstance
{
   private static DatabaseHelper globalDbInstance;
   private static User currentUser;
   public static DatabaseHelper getDatabaseInstance()
    {
        return globalDbInstance;
    }

    public void setDatabaseInstance(DatabaseHelper db)
    {
       DataBaseInstance.globalDbInstance=db;
    }

    public void setUser(User user)
    {
       DataBaseInstance.currentUser=user;
    }

    public User getUser()
    {
       return DataBaseInstance.currentUser;
    }
}
