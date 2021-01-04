package hussainshaikh.com.hussainpdfreader;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);

        //Call runtimepermission
        runtimePermission();

    }

    private void runtimePermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displayPdfs();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


    }

    //Adapter
    public ArrayList<File> findFile (File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        for (File pdffile: files){
            if (pdffile.isDirectory() && !pdffile.isHidden()){
                arrayList.addAll(findFile(pdffile));

            }else {
                if (pdffile.getName().endsWith(".pdf") || pdffile.getName().endsWith(".wav")) {
                    arrayList.add(pdffile);

                }

            }

        }
        return arrayList;
    }
    public void displayPdfs(){
        final ArrayList<File> mypdfs = findFile(Environment.getExternalStorageDirectory());

        items = new String[mypdfs.size()];
        for (int i = 0; i<mypdfs.size(); i++){
            items[i] = mypdfs.get(i).getName().toString().replace(".pdf","").replace(".wav","");

        }
//        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
//        listView.setAdapter(myAdapter);

        custemAdapter custemAdapter = new custemAdapter();
        listView.setAdapter(custemAdapter);

    }

        class custemAdapter extends BaseAdapter{

            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View myview = getLayoutInflater().inflate(R.layout.list_pdfs,null);
                TextView pdfName = myview.findViewById(R.id.pdf_Name);
                pdfName.setSelected(true);
                pdfName.setText(items[position]);

                return myview;
            }
        }
}