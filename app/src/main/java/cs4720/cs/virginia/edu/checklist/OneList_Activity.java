package cs4720.cs.virginia.edu.checklist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Rock Beom Kim rk5dy
 * Peter Bahng pb5te
 */
public class OneList_Activity extends AppCompatActivity {

    ArrayList<Task> taskList = new ArrayList<Task>();
    ArrayList<Task> completedList = new ArrayList<Task>();
    TaskAdapter tAdapter = null;
    TaskAdapter cAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_list);

        ListView listView = (ListView)findViewById(R.id.listView);
        ListView completeListView = (ListView) findViewById(R.id.complist_view);
        tAdapter = new TaskAdapter(this, R.layout.listitem, taskList);
        cAdapter = new TaskAdapter(this, R.layout.listitem, completedList);

        listView.setAdapter(tAdapter);
        completeListView.setAdapter(cAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //saves instance state so the list isn't destroyed upon calling the one task activity
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelableArrayList("tList", taskList);
        savedInstanceState.putParcelableArrayList("cList", completedList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.taskList = savedInstanceState.getParcelableArrayList("cList");
        this.taskList = savedInstanceState.getParcelableArrayList("tList");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_add_task:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add a task");
                builder.setMessage("What do you want to do?");
                final EditText inputField = new EditText(this);
                builder.setView(inputField);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Task tmp = new Task(inputField.getText().toString());
                        taskList.add(tmp);
                        tAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel",null);

                builder.create().show();
                return true;

            default:
                return false;
        }
    }

    public void setAsComplete(View view) {
        ArrayList<Task> tmp = new ArrayList<Task>();
        for (Task t : taskList) {
            if (t.getChecked()) tmp.add(t);
        }

        completedList.addAll(tmp);
        cAdapter.notifyDataSetChanged();
        taskList.removeAll(tmp);
        tAdapter.notifyDataSetChanged();
    }

    public void setAsIncomplete(View view) {
        ArrayList<Task> tmp = new ArrayList<Task>();
        for (Task t : completedList) {
            if (t.getChecked()) tmp.add(t);
        }

        completedList.removeAll(tmp);
        cAdapter.notifyDataSetChanged();
        taskList.addAll(tmp);
        tAdapter.notifyDataSetChanged();
    }
}
