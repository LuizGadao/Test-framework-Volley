package com.luizgadao.volley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String [] examples = {"Simple list with image.", "List facebook feed."};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, examples );

        ListView listView = (ListView) findViewById( R.id.example_lists);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Log.d(TAG, "click item: " + adapter.getItem( position ));

                switch ( position )
                {
                    case 0:
                        startActivity( new Intent( getBaseContext(), SimpleListView.class ) );
                        break;

                    case 1:
                        startActivity( new Intent( getBaseContext(), ListViewFacebookWithVolley.class) );
                        break;

                    default:
                        finish();
                }

            }
        });
    }

}
