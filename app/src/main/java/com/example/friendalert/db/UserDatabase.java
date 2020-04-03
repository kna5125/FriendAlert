package com.example.friendalert.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

// Note version should be changed whenever database changes to ensure
// that db is recreated.  You can add a migration to say how the database
// should be changed from version to version.
// Note: If you are changing the schema of your database while debugging,
// you will get an error.  Simply uninstall the app on your phone to
// ensure that the database will be deleted, and then recreated with the
// new schema.
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public interface UserListener {
        void onUserReturned(User user);
    }

    public abstract UserDAO userDAO();

    private static UserDatabase INSTANCE;

    public static synchronized UserDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "user_database")
                            .addCallback(createUserDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Note this call back will be run
    private static RoomDatabase.Callback createUserDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            createUserTable();
        }
    };

    private static void createUserJokeTable() {
        for (int i = 0; i < DefaultContent.phoneNumber.length; i++) {
            insert(new User(0, DefaultContent.phoneNumber[i], DefaultContent.userName[i], false));
        }
    }

    public static void getUser(int id, UserListener listener) {
        new AsyncTask<Integer, Void, User>() {
            protected User doInBackground(Integer... ids) {
                return INSTANCE.userDAO().getById(ids[0]);
            }

            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                listener.onUserReturned(user);
            }
        }.execute(id);
    }

    public static void insert(User user) {
        new AsyncTask<User, Void, Void>() {
            protected Void doInBackground(User... users) {
                INSTANCE.userDAO().insert(users);
                return null;
            }
        }.execute(user);
    }

    public static void delete(int userID) {
        new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... ids) {
                INSTANCE.userDAO().delete(ids[0]);
                return null;
            }
        }.execute(userID);
    }


    public static void update(User user) {
        new AsyncTask<User, Void, Void>() {
            protected Void doInBackground(User... users) {
                INSTANCE.userDAO().update(users);
                return null;
            }
        }.execute(user);
    }
}