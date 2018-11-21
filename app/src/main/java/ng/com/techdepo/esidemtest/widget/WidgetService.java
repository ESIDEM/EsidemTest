package ng.com.techdepo.esidemtest.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.utils.SharedPreferenceUtil;

public class WidgetService extends RemoteViewsService {
    private Context context;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {

            private String subject;
            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                final long identityToken = Binder.clearCallingIdentity();
                subject = SharedPreferenceUtil.subject(getApplicationContext()).substring(0, 1).toUpperCase() +
                        SharedPreferenceUtil.subject(getApplicationContext()).substring(1);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public RemoteViews getViewAt(int position) {
                final RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_layout);
                views.setTextViewText(R.id.widget_list,subject);
                final Intent fillInIntent = new Intent();
                views.setOnClickFillInIntent(R.id.widget, fillInIntent);
                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_layout);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
