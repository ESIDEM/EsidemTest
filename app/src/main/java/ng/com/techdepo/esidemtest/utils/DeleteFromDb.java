package ng.com.techdepo.esidemtest.utils;


import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import ng.com.techdepo.esidemtest.database.AppDatabase;

public class DeleteFromDb  {


    public static void deleteAllMovies(final AppDatabase appDatabase){

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                appDatabase.databaseDAO().deleteAllQuestions();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }
}
