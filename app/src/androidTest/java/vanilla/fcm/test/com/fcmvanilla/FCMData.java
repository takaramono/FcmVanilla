package vanilla.fcm.test.com.fcmvanilla;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by yuanwan on 2/1/18.
 */

public class FCMData implements Serializable {
  private String title;
  private String content;
  private Map<String, String> dataMaps;

  private long receivedTimestamp;

  public FCMData(String title, String content, Map<String, String> dataMaps) {
    this.title = title;
    this.content = content;
    this.dataMaps = dataMaps;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Map<String, String> getDataMaps() {
    return dataMaps;
  }

  public void setDataMaps(Map<String, String> dataMaps) {
    this.dataMaps = dataMaps;
  }

  public long getReceivedTimestamp() {
    return receivedTimestamp;
  }

  public void setReceivedTimestamp(long receivedTimestamp) {
    this.receivedTimestamp = receivedTimestamp;
  }

  @Override
  public String toString() {
    return "FCMData{" +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", dataMaps=" + dataMaps +
        ", receivedTimestamp=" + receivedTimestamp +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FCMData data = (FCMData) o;

    if (title != null ? !title.equals(data.title) : data.title != null) return false;
    if (content != null ? !content.equals(data.content) : data.content != null) return false;
    return dataMaps != null ? dataMaps.equals(data.dataMaps) : data.dataMaps == null;
  }
}
