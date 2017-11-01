package luongduongquan.com.listrtsp.Model;

/**
 * Created by luong.duong.quan on 11/1/2017.
 */

public class StreamItem {

	int id;
	String URL;
	String name;

	public StreamItem(){

	}

	public StreamItem(int id, String URL, String name) {
		this.id = id;
		this.URL = URL;
		this.name = name;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
