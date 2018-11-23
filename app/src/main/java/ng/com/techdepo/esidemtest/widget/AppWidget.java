package ng.com.techdepo.esidemtest.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import java.util.List;
import java.util.Random;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.activities.MainActivity;
import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.repository.AppRepository;

public class AppWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        context.startService(new Intent(context,UpdateService.class));
           }



    public static class UpdateService extends Service
    {
        private AppRepository appRepository;
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
            appRepository = new AppRepository(ctx);
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
            //when a user clicks the widget, show 'em a google search page with will roger results...
            Intent intent = new Intent(ctx,MainActivity.class);

            //no request code and no flags for this example
            PendingIntent pender = PendingIntent.getActivity(ctx, 0, intent, 0);
            view.setOnClickPendingIntent(R.id.all_layout, pender);
            return view;
        }

        @Override
        public IBinder onBind(Intent arg0)
        {
            return null;
        }



    }

}
