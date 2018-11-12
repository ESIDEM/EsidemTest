package ng.com.techdepo.esidemtest.utils;

import android.os.AsyncTask;

import ng.com.techdepo.esidemtest.database.AppDatabase;

public class DeleteFromDb extends AsyncTask<Void,Void,Void> {

    AppDatabase appDatabase;

    public DeleteFromDb(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        appDatabase.databaseDAO().deleteAllQuestions();
        return null;
    }

    public static void deleteAllMovies(AppDatabase appDatabase){

        DeleteFromDb deleteFromDb = new DeleteFromDb(appDatabase);
        deleteFromDb.execute();
    }
}
