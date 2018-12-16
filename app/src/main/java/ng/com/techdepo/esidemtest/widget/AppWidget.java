package ng.com.techdepo.esidemtest.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.RemoteViews;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.activities.MainActivity;
import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.repository.AppRepository;
import ng.com.techdepo.esidemtest.utils.QuestionsApplication;
import ng.com.techdepo.esidemtest.utils.ToastMaker;

public class AppWidget extends AppWidgetProvider {



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        context.startService(new Intent(context,UpdateService.class));
           }



    public static class UpdateService extends Service
    {
        @Override
        public void onCreate() {
            super.onCreate();

            ((QuestionsApplication) this.getApplicationContext()).getAppComponent().inject(this);
        }

        @Inject
        AppRepository appRepository;
        List<QuestionEntity> questionEntities;
        QuestionEntity questionEntity;

        @Override
        public void onStart(Intent intent, int startId)
        {
            //build widget update for today
            RemoteViews views=buildUpdate(this);

            //push update for widget to the Home activity
            ComponentName widget = new ComponentName(this,AppWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(widget, views);

        }

        public RemoteViews buildUpdate(Context ctx)
        {
         //   appRepository = new AppRepository(ctx);
            Random random = new Random();

            questionEntities = appRepository.getWidgetQuestions();

             questionEntity =questionEntities.get(random.nextInt(questionEntities.size()));
             String question = questionEntity.getQuestion();

            RemoteViews view = new RemoteViews(ctx.getPackageName(),R.layout.widget_layout);
            view.setTextViewText(R.id.widget_list, question);
            view.setTextViewText(R.id.widget_a, questionEntity.getOptionDb().getOptionA());
            view.setTextViewText(R.id.widget_b,questionEntity.getOptionDb().getOptionB());
            view.setTextViewText(R.id.widget_c,questionEntity.getOptionDb().getOptionC());
            view.setTextViewText(R.id.widget_d,questionEntity.getOptionDb().getOptionD());

           Intent intent= new Intent(ctx, AppWidget.class);
           intent.putExtra("answer",questionEntity.getAnswer());
            intent.putExtra("click","a");
            Intent intent1= new Intent(ctx, AppWidget.class);
            intent1.putExtra("answer",questionEntity.getAnswer());
            intent1.putExtra("click","b");
            Intent intent2= new Intent(ctx, AppWidget.class);
            intent2.putExtra("answer",questionEntity.getAnswer());
            intent2.putExtra("click","c");
            Intent intent3= new Intent(ctx, AppWidget.class);
            intent3.putExtra("answer",questionEntity.getAnswer());
            intent3.putExtra("click","d");
           PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(ctx,1,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(ctx,2,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent3 = PendingIntent.getBroadcast(ctx,3,intent3,PendingIntent.FLAG_UPDATE_CURRENT);
            view.setOnClickPendingIntent(R.id.widget_a,pendingIntent);
           view.setOnClickPendingIntent(R.id.widget_b,pendingIntent1);
            view.setOnClickPendingIntent(R.id.widget_c,pendingIntent2);
            view.setOnClickPendingIntent(R.id.widget_d,pendingIntent3);

           return view;
        }

        @Override
        public IBinder onBind(Intent arg0)
        {
            return null;
        }



    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String click = intent.getStringExtra("click");
        String answer =intent.getStringExtra("answer");
        if (click==null){
            return;
        }else if (click.equals(answer)){
            ToastMaker.makeShortToast(context,context.getString(R.string.yes_option)+" " + answer.toUpperCase()+ " "+context.getString(R.string.was_coorect));
            deLay(context);
        }else {
            ToastMaker.makeShortToast(context,context.getString(R.string.no_option)+" "+click.toUpperCase() +" "+ context.getString(R.string.is_wrong));
        }
    }
    private void deLay(final Context c) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                c.startService(new Intent(c,UpdateService.class));

            }
        }, 1000);
    }


}
