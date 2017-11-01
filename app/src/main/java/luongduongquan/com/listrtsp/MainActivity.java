package luongduongquan.com.listrtsp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import luongduongquan.com.listrtsp.Adapter.AdapterStream;
import luongduongquan.com.listrtsp.Model.StreamItem;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

	List<StreamItem> streamItems = new ArrayList<>();
	ListView listView1;
	String URL1 = "http://video3.earthcam.com/fecnetwork/4717.flv/chunklist_w265478634.m3u8";
	String URL2 = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";
	String URL3 = "http://samples.mplayerhq.hu/V-codecs/h264/interlaced_crop.mp4";
	String[] listURL = {URL1,URL2,URL3};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		for (int i=0; i<3; i++){
			StreamItem streamItem = new StreamItem(i, listURL[i], "URL "+ String.valueOf(i+1));
			streamItems.add(streamItem);
		}

		AdapterStream adapterStream = new AdapterStream(this, R.layout.item_stream, streamItems);

		listView1 = (ListView) findViewById(R.id.listViewStream);
		listView1.setAdapter(adapterStream);
		adapterStream.notifyDataSetChanged();

		listView1.setOnItemClickListener(this);


	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

		Intent intent = new Intent(MainActivity.this, ShowVideo.class);
		intent.putExtra("URL",streamItems.get(i).getURL());
		startActivity(intent);
	}
}
